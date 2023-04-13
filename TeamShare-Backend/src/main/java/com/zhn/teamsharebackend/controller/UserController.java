package com.zhn.teamsharebackend.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.zhn.teamsharebackend.constant.ErrorCode;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.User;
import com.zhn.teamsharebackend.domain.dto.UserDTO;
import com.zhn.teamsharebackend.domain.vo.UserVo;
import com.zhn.teamsharebackend.service.UserService;
import com.zhn.teamsharebackend.utils.UserHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zhn
 * @version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PutMapping("/")
    public Result<Boolean> update(@RequestBody UserVo userVo) {
        //校验
        if (ObjectUtil.isEmpty(userVo)) {
            return Result.fail(ErrorCode.NULL_PARAMS_ERROR,"表单内容不允许为空");
        }
        return userService.updateUser(userVo,UserHolder.getUser().getToken());
    }

    @GetMapping("/getLoginUser")
    public Result<UserVo> getLoginUser(HttpServletRequest request) {
        //获取token
        String token = request.getHeader("authorization");
        if(StrUtil.isBlank(token)) {
            return Result.ok();
        }
        return userService.getLoginUser(token);
    }

    @GetMapping("/recommendList")
    public Result<List<UserVo>> getRecommendUsers() {
        return userService.getRecommendUserList();
    }
}
