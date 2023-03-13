package com.zhn.teamsharebackend.controller;

import cn.hutool.core.util.StrUtil;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.dto.LoginForm;
import com.zhn.teamsharebackend.exception.BusinessException;
import com.zhn.teamsharebackend.exception.ErrorCode;
import com.zhn.teamsharebackend.service.LoginService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zhn
 * @version 1.0
 */
@RestController
public class LoginController {
    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginForm loginForm) {
        if(loginForm == null) {
            throw new BusinessException(ErrorCode.NULL_PARAMS_ERROR,"登录信息为空");
        }
        return loginService.login(loginForm);
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
