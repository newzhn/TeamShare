package com.zhn.teamsharebackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhn.teamsharebackend.constant.RedisConstant;
import com.zhn.teamsharebackend.constant.SystemConstant;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.User;
import com.zhn.teamsharebackend.domain.dto.LoginForm;
import com.zhn.teamsharebackend.domain.dto.UserDTO;
import com.zhn.teamsharebackend.exception.BusinessException;
import com.zhn.teamsharebackend.exception.ErrorCode;
import com.zhn.teamsharebackend.mapper.UserMapper;
import com.zhn.teamsharebackend.service.LoginService;
import com.zhn.teamsharebackend.utils.ValidateUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 登录相关服务
 *
 * @author zhn
 * @version 1.0
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result<String> login(LoginForm loginForm) {
        //校验用户名和密码
        String userName = loginForm.getLoginUsername();
        String password = loginForm.getLoginPassword();
        ValidateUtil.loginValidate(userName,password);
        //根据用户名查询用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",userName);
        User user = userMapper.selectOne(wrapper);
        if(ObjectUtil.isEmpty(user)) {
            throw new BusinessException(ErrorCode.NOT_USER);
        }
        //密码比较
        password = DigestUtils.md5DigestAsHex((SystemConstant.SALT + password).getBytes());
        if(!password.equals(user.getUserPassword())) {
            throw new BusinessException(ErrorCode.PASSWORD_ERROR);
        }
        //随机生成Token，作为登录令牌
        String token = UUID.randomUUID(false).toString();
        //用户信息存入Redis，使用Hash结构数据类型
        String key = RedisConstant.LOGIN_USER_KEY + token;
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(), CopyOptions.create()
                .setIgnoreNullValue(false)
                .setFieldValueEditor((fieldName, fieldValue) -> fieldValue == null ? null : fieldValue.toString()));
        stringRedisTemplate.opsForHash().putAll(key,userMap);
        //设置用户信息有效期
        stringRedisTemplate.expire(key,RedisConstant.LOGIN_USER_TTL, TimeUnit.MINUTES);
        //将token返回给前端
        return Result.ok(token);
    }

    @Override
    public Result<Boolean> logout(String token) {
        //删除Redis中的用户数据
        String key = RedisConstant.LOGIN_USER_KEY + token;
        Boolean isLogout = stringRedisTemplate.delete(key);
        return Result.ok(isLogout);
    }
}
