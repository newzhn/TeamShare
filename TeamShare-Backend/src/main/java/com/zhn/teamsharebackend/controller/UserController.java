package com.zhn.teamsharebackend.controller;

import cn.hutool.core.util.StrUtil;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.User;
import com.zhn.teamsharebackend.domain.dto.UserDTO;
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

    @GetMapping("/currentUser")
    public Result<UserDTO> getCurrentUser(HttpServletRequest request) {
        //获取token
        String token = request.getHeader("authorization");
        if(StrUtil.isBlank(token)) {
            return Result.ok();
        }
        return userService.getCurrentUser(token);
    }

    @GetMapping("/recommend")
    public Result<List<User>> getRecommendUsers() {
        List<User> users = userService.query().list();
        return Result.ok(users);
    }
}
