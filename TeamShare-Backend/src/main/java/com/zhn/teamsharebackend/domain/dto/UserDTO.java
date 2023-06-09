package com.zhn.teamsharebackend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author zhn
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 账户名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 头像url
     */
    private String avatarUrl;

    /**
     * 用户个性签名
     */
    private String signature;

    /**
     * 性别，0为女，1为男
     */
    private Integer gender;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户QQ
     */
    private String qq;

    /**
     * 保存的标签名列表
     */
    private List<String> tagNames;

    /**
     * json格式保存的队伍名列表
     */
    private List<Long> teamIds;

    /**
     * 用户状态，0是正常，1是被拉黑等
     */
    private Integer userStatus;

    /**
     * 用户角色，0是管理员，1是普通用户，2是队长，3是组员等
     */
    private Integer userRole;

    /**
     * 注册时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除，0是未删除，1是已删除
     */
    private Integer isDelete;

    /**
     * token
     */
    private String token;
}
