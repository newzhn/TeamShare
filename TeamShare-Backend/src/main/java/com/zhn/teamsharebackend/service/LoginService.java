package com.zhn.teamsharebackend.service;

import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.dto.LoginForm;

import java.util.Map;

/**
 * @author zhn
 * @version 1.0
 */
public interface LoginService {

    Result<String> login(LoginForm loginForm);

    Result<Boolean> logout(String token);
}
