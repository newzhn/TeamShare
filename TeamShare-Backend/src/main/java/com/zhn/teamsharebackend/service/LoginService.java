package com.zhn.teamsharebackend.service;

import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.request.LoginRequest;

import java.util.List;

/**
 * @author zhn
 * @version 1.0
 */
public interface LoginService {

    Result<String> login(LoginRequest loginRequest);

    Result<Boolean> logout(String token);

    void changeTeamIds(List<Long> teamIds,Long userId, String token);
}
