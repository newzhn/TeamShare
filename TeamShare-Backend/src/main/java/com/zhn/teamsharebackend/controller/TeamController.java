package com.zhn.teamsharebackend.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.Team;
import com.zhn.teamsharebackend.domain.dto.TeamDTO;
import com.zhn.teamsharebackend.domain.dto.UserDTO;
import com.zhn.teamsharebackend.domain.vo.TeamVo;
import com.zhn.teamsharebackend.exception.BusinessException;
import com.zhn.teamsharebackend.constant.ErrorCode;
import com.zhn.teamsharebackend.service.TeamService;
import com.zhn.teamsharebackend.utils.UserHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zhn
 * @version 1.0
 */
@RestController
@RequestMapping("/team")
public class TeamController {
    @Resource
    private TeamService teamService;

    @PostMapping("/")
    public Result<Boolean> create(@RequestBody Team team) {
        if (team == null) {
            return Result.fail(ErrorCode.NULL_PARAMS_ERROR,"创建队伍信息为空");
        }
        return teamService.create(team);
    }

    @DeleteMapping("/{teamId}")
    public Result<Boolean> delete(@PathVariable("teamId") Long teamId) {
        if (teamId != null && teamId <= 0) {
            return Result.fail(ErrorCode.PARAMS_ERROR,"队伍Id参数错误");
        }
        return teamService.delete(teamId);
    }

    @PutMapping("/")
    public Result<Boolean> update(TeamDTO teamDTO) {
        return null;
    }

    @GetMapping("/{teamId}")
    public Result<TeamVo> get(@PathVariable("teamId") Long teamId) {
        if (teamId != null && teamId <= 0) {
            return Result.fail(ErrorCode.PARAMS_ERROR,"队伍Id参数错误");
        }
        return teamService.get(teamId);
    }

    @PostMapping("/joinIn/{teamId}")
    public Result<Boolean> joinIn(@PathVariable("teamId") Long teamId, HttpServletRequest request,
                                  @RequestParam(value = "password",required = false)String password) {
        if (teamId != null && teamId <= 0) {
            return Result.fail(ErrorCode.PARAMS_ERROR,"队伍Id参数错误");
        }
        return teamService.joinIn(teamId,request.getHeader("authorization"),password);
    }

    @GetMapping("/getList/{teamStatus}")
    public Result<List<TeamVo>> getList(@PathVariable("teamStatus") int teamStatus) {
        if(teamStatus != 1 && teamStatus != 2) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"队伍状态参数错误");
        }
        return teamService.getListForStatus(teamStatus);
    }

    @GetMapping("/getJoinedList")
    public Result<List<TeamVo>> getJoinedList() {
        //校验用户是否登录
        UserDTO user = UserHolder.getUser();
        if (ObjectUtil.isEmpty(user)) {
            return Result.fail(ErrorCode.NOT_LOGIN);
        }
        return teamService.getJoinedList(user.getTeamIds());
    }

    @GetMapping("/checkTeamName/{teamName}")
    public Result<Boolean> checkTeamName(@PathVariable("teamName") String teamName) {
        if (StrUtil.isBlank(teamName)) {
            throw new BusinessException(ErrorCode.NULL_PARAMS_ERROR,"队伍名不能为空");
        }
        return teamService.checkTeamName(teamName);
    }

    @GetMapping("/search")
    public Result<List<TeamVo>> search(String searchText,int teamStatus) {
        if (StrUtil.isBlank(searchText)) {
            return this.getList(teamStatus);
        }
        if(teamStatus != 1 && teamStatus != 2) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"队伍状态参数错误");
        }
        return teamService.search(searchText,teamStatus);
    }

    @GetMapping("/recommendList")
    public Result<List<TeamVo>> getRecommendList() {
        return teamService.getRecommendTeamList();
    }
}
