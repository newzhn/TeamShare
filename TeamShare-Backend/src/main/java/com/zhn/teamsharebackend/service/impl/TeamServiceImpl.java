package com.zhn.teamsharebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.zhn.teamsharebackend.constant.CacheConstant;
import com.zhn.teamsharebackend.converter.TeamConverter;
import com.zhn.teamsharebackend.converter.UserConverter;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.Team;
import com.zhn.teamsharebackend.domain.dto.TeamDTO;
import com.zhn.teamsharebackend.domain.dto.UserDTO;
import com.zhn.teamsharebackend.domain.vo.TeamVo;
import com.zhn.teamsharebackend.exception.BusinessException;
import com.zhn.teamsharebackend.constant.ErrorCode;
import com.zhn.teamsharebackend.mapper.TeamMapper;
import com.zhn.teamsharebackend.service.LoginService;
import com.zhn.teamsharebackend.service.TeamService;
import com.zhn.teamsharebackend.service.UserService;
import com.zhn.teamsharebackend.utils.CacheUtil;
import com.zhn.teamsharebackend.utils.UserHolder;
import com.zhn.teamsharebackend.utils.ValidateUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhanh
 * @description 针对表【team(队伍表)】的数据库操作Service实现
 * @createDate 2023-03-04 15:58:14
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team>
        implements TeamService {
    @Resource
    private TeamMapper teamMapper;
    @Resource
    private UserService userService;
    @Resource
    private LoginService loginService;
    @Resource
    private Gson gson;
    @Resource
    private CacheUtil cacheUtil;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private TeamConverter teamConverter;
    @Resource
    private UserConverter userConverter;

    private static final int PRIVATE_TEAM_STATUS = 2;

    @Override
    public Result<Boolean> create(Team team) {
        //队伍数据非空校验
        Result<Boolean> result = ValidateUtil.teamValidate(team);
        if (result.getData()) {
            return result;
        }
        //队伍名去重
        if (this.checkTeamName(team.getTeamName()).getData()) {
            return Result.fail(ErrorCode.PARAMS_ERROR, "队伍名不能重复");
        }
        //根据队伍状态进行密码加密处理
        if (team.getTeamStatus() == PRIVATE_TEAM_STATUS) {
            team.setTeamPassword(DigestUtils.md5DigestAsHex(team.getTeamPassword().getBytes()));
        }
        //获取创建者和队员id信息
        UserDTO user = UserHolder.getUser();
        Long captainId = user.getUserId();
        team.setCaptainId(captainId);
        List<Long> memberIds = Collections.singletonList(captainId);
        team.setMemberIds(gson.toJson(memberIds));
        //存储数据库
        int i = teamMapper.insert(team);
        //存入Redis，需要进行非空判断
        if (i > 0) {
            this.getById(team.getTeamId());
            //改变创建者加入队伍列表
            List<Long> teamIds = user.getTeamIds();
            teamIds.add(team.getTeamId());
            loginService.changeTeamIds(teamIds,captainId,user.getToken());
        }
        //返回创建结果
        return Result.ok(i > 0);
    }

    @Override
    public Result<Boolean> delete(long id) {

        return null;
    }

    @Override
    public Result<Boolean> update(Team team) {
        return null;
    }

    @Override
    public Result<TeamVo> get(long id) {
        Team team = this.getById(id);
        return Result.ok(team == null ? null : this.convert(team));
    }

    @Override
    public TeamVo convert(Team team) {
        //对team进行处理，获取队员和队长信息
        TeamDTO teamDto = teamConverter.doToDto(team);
        List<UserDTO> members = getTeamMembers(teamDto.getMemberIds());
        //处理信息，默认队长信息是队员信息第一个
        teamDto.setCaptain(members.get(0));
        teamDto.setMembers(members);
        return teamConverter.dtoToVo(teamDto);
    }

    @Override
    public Team getById(Serializable id) {
        return cacheUtil.queryWithPassThrough(CacheConstant.TEAM, id, Team.class, super::getById);
    }

    /**
     * 加入队伍
     *
     * @param teamId 队伍Id
     * @return 返回加入结果布尔值
     */
    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Result<Boolean> joinIn(Long teamId, String token, String password) {
        try {
            //获取要加入队伍数据和成员id列表
            TeamDTO teamDto = teamConverter.doToDto(this.getById(teamId));
            List<Long> memberIds = teamDto.getMemberIds();
            //进行密码校验
            password = DigestUtils.md5DigestAsHex(password.getBytes());
            if (teamDto.getTeamStatus() == PRIVATE_TEAM_STATUS && !teamDto.getTeamPassword().equals(password)) {
                return Result.fail(ErrorCode.PASSWORD_ERROR,"加入失败，队伍密码错误");
            }
            //获取当前登录用户Id和已加入队伍Id列表
            UserDTO loginUser = UserHolder.getUser();
            Long userId = loginUser.getUserId();
            List<Long> teamIds = loginUser.getTeamIds() != null ? loginUser.getTeamIds() : Collections.emptyList();
            //真正加入队伍前进行校验，保证没有重复加入队伍
            if (memberIds.contains(userId) || teamIds.contains(teamId)) {
                return Result.fail(ErrorCode.PARAMS_ERROR,"不能重复加入队伍");
            }
            //开始加入操作
            //更新UserHolder、Redis缓存、数据库中的用户数据
            teamIds.add(teamId);
            loginService.changeTeamIds(teamIds,userId,token);
            //更新对应队伍的队员列表信息
            memberIds.add(userId);
            this.changeMemberIds(memberIds,teamId);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.HANDLE_ERROR, "加入队伍失败，原因已报告给管理员");
        }
        return Result.ok(true);
    }

    /**
     * 根据队伍状态获取最新队伍列表信息
     *
     * @param teamStatus 前端传入队伍状态参数
     * @return 返回队伍列表
     */
    @Override
    public Result<List<TeamVo>> getListForStatus(int teamStatus) {
        //获取已加入队伍Id列表
        UserDTO user = UserHolder.getUser();
        List<Long> teamIds = user != null && user.getTeamIds() != null ? user.getTeamIds() : Collections.emptyList();
        //查询数据库，要求状态一致，截止时间未到，按照时间顺序降序输出
        QueryWrapper<Team> wrapper = new QueryWrapper<>();
        wrapper.eq("team_status", teamStatus).ge("deadline", new Date()).orderByDesc("create_time");
        //对查询队伍进行过滤处理。获取队长和队员的相关信息,并转换成TeamDTO返回，并写入缓存
        List<Team> teams = teamMapper.selectList(wrapper).stream()
                .filter(team -> !teamIds.contains(team.getTeamId())).collect(Collectors.toList());
        List<TeamVo> teamList = teams.stream().map(team -> {
            cacheUtil.set(CacheConstant.TEAM, team.getTeamId(), team);
            return this.convert(team);
        }).collect(Collectors.toList());
        return Result.ok(teamList);
    }

    /**
     * 获取当前登录用户加入的队伍列表
     *
     * @param teamIds 队伍id列表，一般存储在用户信息里
     * @return 返回队伍列表
     */
    @Override
    public Result<List<TeamVo>> getJoinedList(List<Long> teamIds) {
        if (teamIds == null || teamIds.size() == 0) {
            return Result.ok();
        }
        List<TeamVo> joinedTeamList = new ArrayList<>();
        for (long teamId : teamIds) {
            //查询缓存中的队伍信息
            TeamVo teamVo = this.get(teamId).getData();
            if (teamVo != null) {
                joinedTeamList.add(teamVo);
            }
        }
        return Result.ok(joinedTeamList);
    }

    /**
     * 队伍名去重校验
     *
     * @param teamName 前端传入队伍名参数
     * @return 返回校验结果布尔值
     */
    @Override
    public Result<Boolean> checkTeamName(String teamName) {
        QueryWrapper<Team> wrapper = new QueryWrapper<>();
        wrapper.eq("team_name", teamName);
        boolean exists = teamMapper.exists(wrapper);
        return Result.ok(exists);
    }

    @Override
    public Result<List<TeamVo>> search(String searchText, int teamStatus) {
        //查询数据库进行文本匹配
        QueryWrapper<Team> wrapper = new QueryWrapper<>();
        wrapper.like("team_name",searchText)
                .eq("team_status", teamStatus)
                .ge("deadline", new Date())
                .orderByDesc("create_time");
        List<Team> list = teamMapper.selectList(wrapper);
        //过滤掉不符合加入条件队伍的队伍
        UserDTO user = UserHolder.getUser();
        List<Long> teamIds = user != null ? user.getTeamIds() : Collections.emptyList();
        list = list.stream().filter(team -> !teamIds.contains(team.getTeamId())).collect(Collectors.toList());
        //加工数据
        List<TeamVo> teamList = list.stream()
                .map(teamConverter::doToDto)
                .map(teamConverter::dtoToVo).collect(Collectors.toList());
        //返回结果
        return Result.ok(teamList);
    }

    @Override
    public void changeMemberIds(List<Long> memberIds, Long teamId) {
        this.updateById(new Team(teamId,gson.toJson(memberIds)));
        stringRedisTemplate.delete(CacheConstant.TEAM.getKeyPrefix() + teamId);
    }

    /**
     * 对队员列表进行处理，获取List<UserDTO>集合
     *
     * @param memberIds 队员列表
     * @return 队员集合
     */
    public List<UserDTO> getTeamMembers(List<Long> memberIds) {
        //遍历队员id列表，获取队员信息
        ArrayList<UserDTO> members = new ArrayList<>(memberIds.size());
        for (long userId : memberIds) {
            //读取缓存中用户数据
            UserDTO userDTO = userConverter.doToDto(userService.getById(userId));
            if (userDTO != null) {
                members.add(userDTO);
            }
        }
        return members;
    }
}




