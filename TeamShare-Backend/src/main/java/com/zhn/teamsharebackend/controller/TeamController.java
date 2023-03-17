package com.zhn.teamsharebackend.controller;

import cn.hutool.core.util.StrUtil;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.Team;
import com.zhn.teamsharebackend.exception.BusinessException;
import com.zhn.teamsharebackend.exception.ErrorCode;
import com.zhn.teamsharebackend.service.TeamService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zhn
 * @version 1.0
 */
@RestController
@RequestMapping("/team")
public class TeamController {
    @Resource
    private TeamService teamService;

    @PostMapping("/create")
    public Result<Boolean> createTeam(@RequestBody Team team) {
        if(team == null) {
            throw new BusinessException(ErrorCode.NULL_PARAMS_ERROR,"创建队伍信息为空");
        }
        return teamService.createTeam(team);
    }

    @GetMapping("/checkTeamName")
    public Result<Boolean> checkTeamName(String teamName) {
        if (StrUtil.isBlank(teamName)) {
            throw new BusinessException(ErrorCode.NULL_PARAMS_ERROR,"队伍名不能为空");
        }
        return teamService.checkTeamName(teamName);
    }
}
