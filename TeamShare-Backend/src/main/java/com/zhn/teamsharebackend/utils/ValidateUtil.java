package com.zhn.teamsharebackend.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.Team;
import com.zhn.teamsharebackend.constant.ErrorCode;
import com.zhn.teamsharebackend.domain.User;
import com.zhn.teamsharebackend.domain.dto.UserDTO;

import java.util.Date;
import java.util.List;

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

    public static Result<Boolean> userValidate(UserDTO userDTO) {
        String nickname = userDTO.getNickname();
        String signature = userDTO.getSignature();
        int gender = userDTO.getGender();
        String email = userDTO.getEmail();
        String qq = userDTO.getQq();
        List<String> tagNames = userDTO.getTagNames();
        if (StrUtil.isAllBlank(nickname,email,qq) || tagNames == null) {
            return Result.fail(ErrorCode.NULL_PARAMS_ERROR, true, "表单数据不能出现空数据");
        }
        if(nickname.length() > 40) {
            return Result.fail(ErrorCode.NULL_PARAMS_ERROR, true, "昵称长度不符合要求");
        }
        if(signature != null && signature.length() > 40) {
            return Result.fail(ErrorCode.NULL_PARAMS_ERROR, true, "个性签名输入过长");
        }
        if(gender != 0 && gender != 1) {
            return Result.fail(ErrorCode.PARAMS_ERROR, true, "性别选择错误");
        }
        if(RegexUtil.isEmailInvalid(email)) {
            return Result.fail(ErrorCode.PARAMS_ERROR, true, "邮箱格式不正确");
        }
        if(RegexUtil.isQQInvalid(qq)) {
            return Result.fail(ErrorCode.PARAMS_ERROR, true, "QQ号格式不正确");
        }
        if(tagNames.size() > 5) {
            return Result.fail(ErrorCode.PARAMS_ERROR, true, "用户标签数量不能大于5个");
        }
        return Result.ok(false);
    }
}
