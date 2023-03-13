package com.zhn.teamsharebackend.service;

import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.dto.RegisterForm;

/**
 * The interface Register service.
 *
 * @author zhn
 * @version 1.0
 */
public interface RegisterService {
    /**
     * Check user name result.
     * 用户名校验
     *
     * @param userName the user name
     * @return the result
     */
    Result<Boolean> checkUserName(String userName);

    /**
     * Check email result.
     * 邮箱校验
     *
     * @param email the email
     * @return the result
     */
    Result<Boolean> checkEmail(String email);

    /**
     * Send code result.
     * 发送注册验证码
     *
     * @param email the email
     * @return the result
     */
    Result<Boolean> sendCode(String email);

    /**
     * Register result.
     * 用户注册
     *
     * @param registerForm the register form
     * @return the result
     */
    Result<Boolean> register(RegisterForm registerForm);
}
