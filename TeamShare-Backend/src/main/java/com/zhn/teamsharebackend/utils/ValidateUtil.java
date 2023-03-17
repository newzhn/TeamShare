package com.zhn.teamsharebackend.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.Team;
import com.zhn.teamsharebackend.exception.BusinessException;
import com.zhn.teamsharebackend.exception.ErrorCode;

import java.time.LocalDateTime;
import java.util.Date;

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

    public static void teamValidate(Team team) {
        if (StrUtil.isBlank(team.getTeamName())) {
            throw new BusinessException(ErrorCode.NULL_PARAMS_ERROR,"队伍名不能为空");
        }
        if (StrUtil.isBlank(team.getTeamDescribe())) {
            throw new BusinessException(ErrorCode.NULL_PARAMS_ERROR,"队伍描述不能为空");
        }
        if (team.getTeamSize() < 2 || team.getTeamSize() > 10) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"队伍大小不符合规定");
        }
        if (DateUtil.betweenMs(new Date(),team.getDeadline()) < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"队伍截止时间设置错误");
        }
        if (team.getTeamStatus() != 1 && team.getTeamStatus() != 2) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"队伍状态设置错误");
        }
        if (team.getTeamStatus() == 2 && StrUtil.isBlank(team.getTeamPassword())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"队伍密码不能为空");
        }
    }
}
