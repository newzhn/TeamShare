package com.zhn.teamsharebackend.domain.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zhn
 * @version 1.0
 */
@Data
public class TeamVo {
    /**
     * 队伍ID
     */
    private Long teamId;

    /**
     * 队伍名
     */
    private String teamName;

    /**
     * 队伍描述
     */
    private String teamDescribe;

    /**
     * 队伍最大人数
     */
    private Integer teamSize;

    /**
     * 队伍状态。。。
     */
    private Integer teamStatus;

    /**
     * 队伍截止时间
     */
    private Date deadline;

    /**
     * 注册时间
     */
    private Date createTime;

    /**
     * 队长信息
     */
    private UserVo captain;

    /**
     * 队员信息
     */
    private List<UserVo> members;
}
