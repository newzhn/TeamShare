package com.zhn.teamsharebackend.service;

import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.Team;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhn.teamsharebackend.domain.dto.TeamDTO;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author zhanh
 * @description 针对表【team(队伍表)】的数据库操作Service
 * @createDate 2023-03-04 15:58:14
 */
public interface TeamService extends CRUDTemplate<Team,TeamDTO> {
    /**
     * 加入队伍
     *
     * @param teamId 队伍Id
     * @param token 用户授权Id
     * @param password 加密队伍密码，非必须
     * @return 返回加入结果布尔值
     */
    Result<Boolean> joinIn(Long teamId, String token, @Nullable String password);

    /**
     * 根据队伍状态获取最新队伍列表信息
     *
     * @param teamStatus 前端传入队伍状态参数
     * @return 返回队伍列表
     */
    Result<List<TeamDTO>> getListForStatus(int teamStatus);

    /**
     * 获取当前登录用户加入的队伍列表
     *
     * @param teamIdsStr json格式的队伍id列表，一般存储在用户信息里
     * @return 返回队伍列表
     */
    Result<List<TeamDTO>> getJoinedList(String teamIdsStr);

    /**
     * 队伍名去重校验
     *
     * @param teamName 前端传入队伍名参数
     * @return 返回校验结果布尔值
     */
    Result<Boolean> checkTeamName(String teamName);

    /**
     * 根据搜索文本进行模糊查询
     * @param searchText
     * @param teamStatus
     * @return
     */
    Result<List<TeamDTO>> search(String searchText, int teamStatus);
}
