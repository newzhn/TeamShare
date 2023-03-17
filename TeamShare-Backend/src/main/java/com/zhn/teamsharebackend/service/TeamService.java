package com.zhn.teamsharebackend.service;

import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.Team;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zhanh
* @description 针对表【team(队伍表)】的数据库操作Service
* @createDate 2023-03-04 15:58:14
*/
public interface TeamService extends IService<Team> {

    Result<Boolean> createTeam(Team team);

    Result<Boolean> checkTeamName(String teamName);
}
