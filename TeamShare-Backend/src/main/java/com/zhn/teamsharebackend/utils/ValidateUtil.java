package com.zhn.teamsharebackend.utils;

import cn.hutool.core.util.StrUtil;
import com.zhn.teamsharebackend.exception.BusinessException;
import com.zhn.teamsharebackend.exception.ErrorCode;

/**
 * 校验工具类
 * @author zhn
 * @version 1.0
 */
public class ValidateUtil {
    public static void registerValidate(String userName,String email,String qq,String password,String code) {
        if(StrUtil.isAllBlank(userName,email,qq,password,code)) {
            throw new BusinessException(ErrorCode.NULL_PARAMS_ERROR,"注册信息存在空数据");
        }
        if(RegexUtil.isUserNameInvalid(userName)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名格式不正确");
        }
        if(RegexUtil.isEmailInvalid(email)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"邮箱格式不正确");
        }
        if(RegexUtil.isQQInvalid(qq)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"QQ号格式不正确");
        }
        if(RegexUtil.isPasswordInvalid(password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码格式不正确");
        }
        if(RegexUtil.isCodeInvalid(code)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"验证码格式不正确");
        }
    }

    public static void loginValidate(String userName,String password) {
        if(StrUtil.isAllBlank(userName,password)) {
            throw new BusinessException(ErrorCode.NULL_PARAMS_ERROR,"注册信息存在空数据");
        }
        if(RegexUtil.isUserNameInvalid(userName)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名格式不正确");
        }
        if(RegexUtil.isPasswordInvalid(password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码格式不正确");
        }
    }
}
