package com.zhn.teamsharebackend.domain.vo;

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
public class UserVo {
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
    private String gender;

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
     * 用户角色，0是管理员，1是普通用户，2是队长，3是组员等
     */
    private Integer userRole;

    /**
     * 注册时间
     */
    private Date createTime;
}
