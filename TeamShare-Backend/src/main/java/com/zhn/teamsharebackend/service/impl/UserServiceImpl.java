package com.zhn.teamsharebackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhn.teamsharebackend.constant.RedisConstant;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.User;
import com.zhn.teamsharebackend.domain.dto.UserDTO;
import com.zhn.teamsharebackend.mapper.UserMapper;
import com.zhn.teamsharebackend.service.UserService;
import com.zhn.teamsharebackend.utils.CacheUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    private CacheUtil cacheUtil;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result<Boolean> create(User user) {
        return null;
    }

    @Override
    public Result<Boolean> delete(long id) {
        return null;
    }

    @Override
    public Result<Boolean> update(User user) {
        return null;
    }

    @Override
    public Result<UserDTO> get(long userId) {
        User user = this.getById(userId);
        return Result.ok(user == null ? null : this.convert(user));
    }

    @Override
    public UserDTO convert(User user) {
        //暂时只是属性复制，，后续增加属性处理相关操作 TODO
        return BeanUtil.copyProperties(user,UserDTO.class);
    }

    /**
     * 获取用户基本信息，重写父类方法增加查询缓存操作
     * @param id 用户ID
     * @return 返回user对象
     */
    @Override
    public User getById(Serializable id) {
        return cacheUtil.queryWithPassThrough(RedisConstant.USER_KEY, id, User.class, super::getById,
                RedisConstant.USER_TTL, TimeUnit.HOURS, RedisConstant.NULL_DATA_TTL);
    }

    @Override
    public Result<UserDTO> getLoginUser(String token) {
        String key = RedisConstant.LOGIN_USER_KEY + token;
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(key);
        if (userMap.isEmpty()) {
            return Result.ok();
        }
        UserDTO userDTO = BeanUtil.fillBeanWithMap(userMap, new UserDTO(), false);
        return Result.ok(userDTO);
    }
}




