package com.zhn.teamsharebackend.domain.dto;

import lombok.Data;

/**
 * 存储用户注册表单信息
 *
 * @author zhn
 * @version 1.0
 */
@Data
public class RegisterForm {
    public String registerName;
    public String registerEmail;
    public String registerQQ;
    public String registerPass;
    public String verificationCode;
}
