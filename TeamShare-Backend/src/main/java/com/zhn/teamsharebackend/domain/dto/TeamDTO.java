package com.zhn.teamsharebackend.domain.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zhn
 * @version 1.0
 */
@Data
public class TeamDTO {
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
     * 队伍密码，只有在队伍加密状态下有值
     */
    private String teamPassword;

    /**
     * 队伍截止时间
     */
    private Date deadline;

    /**
     * 注册时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 队长信息
     */
    private UserDTO captain;

    /**
     * 队员信息
     */
    private List<UserDTO> members;
}
