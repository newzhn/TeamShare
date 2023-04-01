package com.zhn.teamsharebackend.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.Team;
import com.zhn.teamsharebackend.constant.ErrorCode;

import java.util.Date;

/**
 * 校验工具类
 * @author zhn
 * @version 1.0
 */
public class ValidateUtil {
    public static Result<Boolean> registerValidate(String userName,String email,String qq,String password,String code) {
        if(StrUtil.isAllBlank(userName,email,qq,password,code)) {
            return Result.fail(ErrorCode.NULL_PARAMS_ERROR, true, "注册信息存在空数据");
        }
        if(RegexUtil.isUserNameInvalid(userName)) {
            return Result.fail(ErrorCode.PARAMS_ERROR, true, "用户名格式不正确");
        }
        if(RegexUtil.isEmailInvalid(email)) {
            return Result.fail(ErrorCode.PARAMS_ERROR, true, "邮箱格式不正确");
        }
        if(RegexUtil.isQQInvalid(qq)) {
            return Result.fail(ErrorCode.PARAMS_ERROR, true, "QQ号格式不正确");
        }
        if(RegexUtil.isPasswordInvalid(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR, true, "密码格式不正确");
        }
        if(RegexUtil.isCodeInvalid(code)) {
            return Result.fail(ErrorCode.PARAMS_ERROR, true, "验证码格式不正确");
        }
        return Result.ok(false);
    }

    public static Result<Boolean> loginValidate(String userName,String password) {
        if(StrUtil.isAllBlank(userName,password)) {
            return Result.fail(ErrorCode.NULL_PARAMS_ERROR, true, "注册信息存在空数据");
        }
        if(RegexUtil.isUserNameInvalid(userName)) {
            return Result.fail(ErrorCode.PARAMS_ERROR, true, "用户名格式不正确");
        }
        if(RegexUtil.isPasswordInvalid(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR, true, "密码格式不正确");
        }
        return Result.ok(false);
    }

    public static Result<Boolean> teamValidate(Team team) {
        if (StrUtil.isBlank(team.getTeamName())) {
            return Result.fail(ErrorCode.NULL_PARAMS_ERROR, true, "队伍名不能为空");
        }
        if (StrUtil.isBlank(team.getTeamDescribe())) {
            return Result.fail(ErrorCode.NULL_PARAMS_ERROR, true, "队伍描述不能为空");
        }
        if (team.getTeamSize() < 2 || team.getTeamSize() > 10) {
            return Result.fail(ErrorCode.PARAMS_ERROR, true, "队伍大小不符合规定");
        }
        if (DateUtil.betweenMs(new Date(),team.getDeadline()) < 0) {
            return Result.fail(ErrorCode.PARAMS_ERROR, true, "队伍截止时间设置错误");
        }
        if (team.getTeamStatus() != 1 && team.getTeamStatus() != 2) {
            return Result.fail(ErrorCode.PARAMS_ERROR, true, "队伍状态设置错误");
        }
        if (team.getTeamStatus() == 2 && StrUtil.isBlank(team.getTeamPassword())) {
            return Result.fail(ErrorCode.PARAMS_ERROR, true, "队伍密码不能为空");
        }
        return Result.ok(false);
    }
}
