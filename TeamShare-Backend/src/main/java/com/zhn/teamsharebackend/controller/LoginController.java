package com.zhn.teamsharebackend.controller;

import cn.hutool.core.util.StrUtil;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.request.LoginRequest;
import com.zhn.teamsharebackend.exception.BusinessException;
import com.zhn.teamsharebackend.constant.ErrorCode;
import com.zhn.teamsharebackend.service.LoginService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author zhn
 * @version 1.0
 */
@RestController
public class LoginController {
    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginRequest loginRequest) {
        if(loginRequest == null) {
            throw new BusinessException(ErrorCode.NULL_PARAMS_ERROR,"登录信息为空");
        }
        return loginService.login(loginRequest);
    }

    @GetMapping("/logout")
    public Result<Boolean> logout(HttpServletRequest request) {
        //获取token
        String token = request.getHeader("authorization");
        if(StrUtil.isBlank(token)) {
            return Result.ok(true);
        }
        return loginService.logout(token);
    }
}
