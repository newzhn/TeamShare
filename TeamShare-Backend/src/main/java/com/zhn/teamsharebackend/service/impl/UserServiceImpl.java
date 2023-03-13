package com.zhn.teamsharebackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhn.teamsharebackend.constant.RedisConstant;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.User;
import com.zhn.teamsharebackend.domain.dto.UserDTO;
import com.zhn.teamsharebackend.exception.BusinessException;
import com.zhn.teamsharebackend.exception.ErrorCode;
import com.zhn.teamsharebackend.mapper.UserMapper;
import com.zhn.teamsharebackend.service.EmailService;
import com.zhn.teamsharebackend.service.UserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
* @author zhanh
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2023-03-04 15:58:14
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result<UserDTO> getCurrentUser(String token) {
        String key = RedisConstant.LOGIN_USER_KEY + token;
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(key);
        if (userMap.isEmpty()) {
            return Result.ok();
        }
        UserDTO userDTO = BeanUtil.fillBeanWithMap(userMap, new UserDTO(), false);
        return Result.ok(userDTO);
    }
}




