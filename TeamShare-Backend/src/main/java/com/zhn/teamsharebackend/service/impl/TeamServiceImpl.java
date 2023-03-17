package com.zhn.teamsharebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.Team;
import com.zhn.teamsharebackend.exception.ErrorCode;
import com.zhn.teamsharebackend.mapper.TeamMapper;
import com.zhn.teamsharebackend.service.TeamService;
import com.zhn.teamsharebackend.utils.UserHolder;
import com.zhn.teamsharebackend.utils.ValidateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.ArrayList;

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

    @Override
    public Result<Boolean> createTeam(Team team) {
        //队伍数据非空校验
        ValidateUtil.teamValidate(team);
        //队伍名去重
        if (checkTeamName(team.getTeamName()).getData()) {
            return Result.fail(ErrorCode.PARAMS_ERROR,"队伍名不能重复");
        }
        //根据队伍状态进行密码处理
        if (team.getTeamStatus() == 2) {
            team.setTeamPassword(DigestUtils.md5DigestAsHex(team.getTeamPassword().getBytes()));
        }
        //获取创建者和队员id信息
        Long userId = UserHolder.getUser().getUserId();
        team.setCaptainId(userId);
        Gson gson = new Gson();
        ArrayList<Long> ids = new ArrayList<>();
        ids.add(userId);
        team.setMemberIds(gson.toJson(ids));
        //存储数据库
        int i = teamMapper.insert(team);
        //返回创建结果
        return Result.ok(i > 0);
    }

    @Override
    public Result<Boolean> checkTeamName(String teamName) {
        QueryWrapper<Team> wrapper = new QueryWrapper<>();
        wrapper.eq("team_name",teamName);
        boolean exists = teamMapper.exists(wrapper);
        return Result.ok(exists);
    }
}




