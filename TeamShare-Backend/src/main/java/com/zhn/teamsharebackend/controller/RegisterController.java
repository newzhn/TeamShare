package com.zhn.teamsharebackend.controller;

import cn.hutool.core.util.StrUtil;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.request.RegisterRequest;
import com.zhn.teamsharebackend.exception.BusinessException;
import com.zhn.teamsharebackend.constant.ErrorCode;
import com.zhn.teamsharebackend.service.RegisterService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zhn
 * @version 1.0
 */
@RestController
public class RegisterController {
    @Resource
    private RegisterService registerService;

    @PostMapping("/register")
    public Result<Boolean> register(@RequestBody RegisterRequest registerRequest) {
        if(registerRequest == null) {
            throw new BusinessException(ErrorCode.NULL_PARAMS_ERROR,"注册信息为空");
        }
        return registerService.register(registerRequest);
    }

    @GetMapping("/register/sendCode")
    public Result<Boolean> senCode(String email) {
        if(StrUtil.isBlank(email)) {
            throw new BusinessException(ErrorCode.NULL_PARAMS_ERROR,"邮箱参数为空");
        }
        return registerService.sendCode(email);
    }

    @GetMapping("/register/checkUserName")
    public Result<Boolean> checkUserName(String userName) {
        if(StrUtil.isBlank(userName)) {
            throw new BusinessException(ErrorCode.NULL_PARAMS_ERROR,"用户名为空");
        }
        return registerService.checkUserName(userName);
    }

    @GetMapping("/register/checkEmail")
    public Result<Boolean> checkEmail(String email) {
        if(StrUtil.isBlank(email)) {
            throw new BusinessException(ErrorCode.NULL_PARAMS_ERROR,"邮箱参数为空");
        }
        return registerService.checkEmail(email);
    }
}
