package com.zhn.teamsharebackend.domain.request;

import lombok.Data;

/**
 * 存储用户注册表单信息
 *
 * @author zhn
 * @version 1.0
 */
@Data
public class RegisterRequest {
    private String userName;
    private String email;
    private String qq;
    private String userPassword;
    private String verificationCode;
}
