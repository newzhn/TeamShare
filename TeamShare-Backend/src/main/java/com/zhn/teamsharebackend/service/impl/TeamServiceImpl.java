package com.zhn.teamsharebackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhn.teamsharebackend.constant.RedisConstant;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.Team;
import com.zhn.teamsharebackend.domain.User;
import com.zhn.teamsharebackend.domain.dto.TeamDTO;
import com.zhn.teamsharebackend.domain.dto.UserDTO;
import com.zhn.teamsharebackend.exception.BusinessException;
import com.zhn.teamsharebackend.constant.ErrorCode;
import com.zhn.teamsharebackend.mapper.TeamMapper;
import com.zhn.teamsharebackend.service.TeamService;
import com.zhn.teamsharebackend.service.UserService;
import com.zhn.teamsharebackend.utils.CacheUtil;
import com.zhn.teamsharebackend.utils.UserHolder;
import com.zhn.teamsharebackend.utils.ValidateUtil;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;
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
    private Gson gson;
    @Resource
    private CacheUtil cacheUtil;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

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
        Long captainId = UserHolder.getUser().getUserId();
        team.setCaptainId(captainId);
        List<Long> memberIds = Collections.singletonList(captainId);
        team.setMemberIds(gson.toJson(memberIds));
        //存储数据库
        int i = teamMapper.insert(team);
        //存入Redis，需要进行非空判断
        if (i > 0) {
            long teamId = team.getTeamId();
            cacheUtil.set(RedisConstant.TEAM_KEY + teamId,this.getById(teamId), RedisConstant.TEAM_TTL,TimeUnit.HOURS);
            stringRedisTemplate.opsForList().leftPush(RedisConstant.TEAM_LIST_KEY + team.getTeamStatus(),String.valueOf(team.getTeamId()));
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
    public Result<TeamDTO> get(long id) {
        Team team = this.getById(id);
        return Result.ok(team == null ? null : this.convert(team));
    }

    @Override
    public TeamDTO convert(Team team) {
        //对team进行处理，获取队员和队长信息
        TeamDTO teamDTO = BeanUtil.copyProperties(team, TeamDTO.class);
        List<UserDTO> members = getTeamMembers(team.getMemberIds());
        //处理信息，默认队长信息是队员信息第一个
        teamDTO.setCaptain(members.get(0));
        teamDTO.setMembers(members);
        return teamDTO;
    }

    @Override
    public Team getById(Serializable id) {
        return cacheUtil.queryWithPassThrough(RedisConstant.TEAM_KEY, id, Team.class, super::getById,
                RedisConstant.TEAM_TTL, TimeUnit.HOURS, RedisConstant.NULL_DATA_TTL);
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
            Team team = this.getById(teamId);
            String memberIdsJson = team.getMemberIds();
            List<Long> memberIds = this.getIdList(memberIdsJson);
            //进行密码校验
            password = DigestUtils.md5DigestAsHex(password.getBytes());
            if (team.getTeamStatus() == PRIVATE_TEAM_STATUS && !team.getTeamPassword().equals(password)) {
                return Result.fail(ErrorCode.PASSWORD_ERROR,"加入失败，队伍密码错误");
            }
            //获取当前登录用户Id和已加入队伍Id列表
            UserDTO loginUser = UserHolder.getUser();
            Long userId = loginUser.getUserId();
            String teamIdsJson = loginUser.getTeamIds();
            List<Long> teamIds = this.getIdList(teamIdsJson);
            //真正加入队伍前进行校验，保证没有重复加入队伍
            if (memberIds.contains(userId) || teamIds.contains(teamId)) {
                return Result.fail(ErrorCode.PARAMS_ERROR,"不能重复加入队伍");
            }
            //开始加入操作
            //更新UserHolder、Redis缓存、数据库中的用户数据
            teamIds.add(teamId);
            teamIdsJson = gson.toJson(teamIds);
            UserHolder.removeUser();
            stringRedisTemplate.opsForHash().put(RedisConstant.LOGIN_USER_KEY + token, "teamIds", teamIdsJson);
            userService.updateById(new User(userId,teamIdsJson));
            stringRedisTemplate.delete(RedisConstant.USER_KEY + userId);
            //更新对应队伍的队员列表信息
            memberIds.add(userId);
            this.updateById(new Team(teamId,gson.toJson(memberIds)));
            stringRedisTemplate.delete(RedisConstant.TEAM_KEY + teamId);
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
    public Result<List<TeamDTO>> getListForStatus(int teamStatus) {
        List<TeamDTO> teamList = new ArrayList();
        //获取已加入队伍Id列表
        UserDTO user = UserHolder.getUser();
        List<Long> teamIds = getIdList(user != null ? user.getTeamIds() : null);
        //查询缓存中维持的队伍列表信息
        String key = RedisConstant.TEAM_LIST_KEY + teamStatus;
        ListOperations<String, String> listOperations = stringRedisTemplate.opsForList();
        //存在则直接返回
        if (stringRedisTemplate.hasKey(key) && listOperations.size(key) > 0) {
            List<Long> list = listOperations.range(key, 0, listOperations.size(key) - 1)
                    .stream().map(Long::parseLong).collect(Collectors.toList());
            for (long teamId : list) {
                teamList.add(this.get(teamId).getData());
            }
            return Result.ok(teamList.stream().filter(team -> !teamIds.contains(team.getTeamId())).collect(Collectors.toList()));
        }
        //不存在则查询数据库
        //查询数据库，要求状态一致，截止时间未到，按照时间顺序降序输出
        QueryWrapper<Team> teamQueryWrapper = new QueryWrapper<>();
        teamQueryWrapper.eq("team_status", teamStatus).ge("deadline", new Date()).orderByDesc("create_time");
        //对查询队伍进行处理。获取队长和队员的相关信息,并转换成TeamDTO返回，并写入缓存
        teamList = teamMapper.selectList(teamQueryWrapper).stream().map(team -> {
            cacheUtil.set(RedisConstant.TEAM_KEY + team.getTeamId(),team,RedisConstant.TEAM_TTL,TimeUnit.HOURS);
            listOperations.rightPush(key,String.valueOf(team.getTeamId()));
            return this.convert(team);
        }).collect(Collectors.toList());
        //设置缓存过期时间
        stringRedisTemplate.expire(key,RedisConstant.TEAM_LIST_TTL,TimeUnit.HOURS);
        //进行过滤返回结果
        teamList = teamList.stream().filter(team -> !teamIds.contains(team.getTeamId())).collect(Collectors.toList());
        return Result.ok(teamList);
    }

    /**
     * 获取当前登录用户加入的队伍列表
     *
     * @param teamIdsStr json格式的队伍id列表，一般存储在用户信息里
     * @return 返回队伍列表
     */
    @Override
    public Result<List<TeamDTO>> getJoinedList(String teamIdsStr) {
        if (StrUtil.isBlank(teamIdsStr)) {
            return Result.ok();
        }
        long[] teamIds = gson.fromJson(teamIdsStr, long[].class);
        ArrayList<TeamDTO> joinedTeamList = new ArrayList<>();
        for (long teamId : teamIds) {
            //查询缓存中的队伍信息
            TeamDTO teamDTO = this.get(teamId).getData();
            if (teamDTO != null) {
                joinedTeamList.add(teamDTO);
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
    public Result<List<TeamDTO>> search(String searchText, int teamStatus) {
        //查询数据库进行文本匹配
        QueryWrapper<Team> wrapper = new QueryWrapper<>();
        wrapper.like("team_name",searchText)
                .eq("team_status", teamStatus)
                .ge("deadline", new Date())
                .orderByDesc("create_time");
        List<Team> list = teamMapper.selectList(wrapper);
        //过滤掉不符合加入条件队伍的队伍
        UserDTO user = UserHolder.getUser();
        List<Long> teamIds = getIdList(user != null ? user.getTeamIds() : null);
        list = list.stream().filter(team -> !teamIds.contains(team.getTeamId())).collect(Collectors.toList());
        //加工数据
        List<TeamDTO> teamList = list.stream().map(this::convert).collect(Collectors.toList());
        //返回结果
        return Result.ok(teamList);
    }

    /**
     * 对json格式的队员列表进行处理，获取List<UserDTO>集合
     *
     * @param memberIdsStr json格式的队员列表
     * @return 队员集合
     */
    public List<UserDTO> getTeamMembers(String memberIdsStr) {
        //获取队员id列表
        long[] memberIds = gson.fromJson(memberIdsStr, long[].class);
        //遍历队员id列表，获取队员信息
        ArrayList<UserDTO> members = new ArrayList<>(memberIds.length);
        for (long userId : memberIds) {
            //读取缓存中用户数据
            UserDTO userDTO = userService.get(userId).getData();
            if (userDTO != null) {
                members.add(userDTO);
            }
        }
        return members;
    }

    private List<Long> getIdList(String idStr) {
        List<Long> ids = new ArrayList();
        if (StrUtil.isNotBlank(idStr)) {
            ids.addAll(Objects.requireNonNull(gson.fromJson(idStr, new TypeToken<List<Long>>() {
            }.getType())));
        }
        return ids;
    }
}




