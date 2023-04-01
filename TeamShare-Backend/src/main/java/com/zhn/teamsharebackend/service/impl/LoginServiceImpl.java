package com.zhn.teamsharebackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.zhn.teamsharebackend.constant.CacheConstant;
import com.zhn.teamsharebackend.constant.SystemConstant;
import com.zhn.teamsharebackend.converter.UserConverter;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.User;
import com.zhn.teamsharebackend.domain.request.LoginRequest;
import com.zhn.teamsharebackend.domain.dto.UserDTO;
import com.zhn.teamsharebackend.exception.BusinessException;
import com.zhn.teamsharebackend.constant.ErrorCode;
import com.zhn.teamsharebackend.mapper.UserMapper;
import com.zhn.teamsharebackend.service.LoginService;
import com.zhn.teamsharebackend.utils.UserHolder;
import com.zhn.teamsharebackend.utils.ValidateUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zhn.teamsharebackend.constant.CacheConstant.LOGIN_USER;

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
    @Resource
    private UserConverter userConverter;
    @Resource
    private Gson gson;

    @Override
    public Result<String> login(LoginRequest loginRequest) {
        //校验用户名和密码
        String userName = loginRequest.getLoginUsername();
        String password = loginRequest.getLoginPassword();
        Result<Boolean> validateResult = ValidateUtil.loginValidate(userName, password);
        if (validateResult.getData()) {
            return Result.fail(validateResult.getCode(),validateResult.getMessage(),null);
        }
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
        String key = LOGIN_USER.getKeyPrefix() + token;
        UserDTO userDTO = userConverter.doToDto(user);
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(), CopyOptions.create()
                .setIgnoreNullValue(true)
                .setFieldValueEditor((fieldName, fieldValue) -> fieldValue == null ? null : fieldValue.toString()));
        userMap.put("token",token);
        stringRedisTemplate.opsForHash().putAll(key,userMap);
        //设置用户信息有效期
        stringRedisTemplate.expire(key, LOGIN_USER.getTtl(), LOGIN_USER.getUnit());
        //将token返回给前端
        return Result.ok(token);
    }

    @Override
    public Result<Boolean> logout(String token) {
        //删除Redis中的用户数据
        String key = CacheConstant.LOGIN_USER.getKeyPrefix() + token;
        Boolean isLogout = stringRedisTemplate.delete(key);
        return Result.ok(isLogout);
    }

    @Override
    public void changeTeamIds(List<Long> teamIds,Long userId, String token) {
        String teamIdsJson = gson.toJson(teamIds);
        UserHolder.removeUser();
        stringRedisTemplate.opsForHash().put(CacheConstant.LOGIN_USER.getKeyPrefix() + token, "teamIds", teamIdsJson);
        userMapper.updateById(new User(userId,teamIdsJson));
        stringRedisTemplate.delete(CacheConstant.USER.getKeyPrefix() + userId);
    }
}
