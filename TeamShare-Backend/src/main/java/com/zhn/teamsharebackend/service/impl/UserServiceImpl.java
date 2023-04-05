package com.zhn.teamsharebackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.zhn.teamsharebackend.constant.CacheConstant;
import com.zhn.teamsharebackend.converter.UserConverter;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.User;
import com.zhn.teamsharebackend.domain.dto.UserDTO;
import com.zhn.teamsharebackend.domain.vo.UserVo;
import com.zhn.teamsharebackend.mapper.UserMapper;
import com.zhn.teamsharebackend.service.UserService;
import com.zhn.teamsharebackend.utils.CacheUtil;
import com.zhn.teamsharebackend.utils.ValidateUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
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
    @Resource
    private UserConverter userConverter;
    @Resource
    private Gson gson;

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
    public Result<UserVo> get(long userId) {
        User user = this.getById(userId);
        return Result.ok(user == null ? null : this.convert(user));
    }

    @Override
    public UserVo convert(User user) {
        //暂时只是属性复制，，后续增加属性处理相关操作 TODO
        UserDTO userDTO = userConverter.doToDto(user);
        return userConverter.dtoToVo(userDTO);
    }

    /**
     * 获取用户基本信息，重写父类方法增加查询缓存操作
     * @param id 用户ID
     * @return 返回user对象
     */
    @Override
    public User getById(Serializable id) {
        return cacheUtil.queryWithPassThrough(CacheConstant.USER, id, User.class, super::getById);
    }

    @Override
    public Result<UserVo> getLoginUser(String token) {
        String key = CacheConstant.LOGIN_USER.getKeyPrefix() + token;
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(key);
        if (userMap.isEmpty()) {
            return Result.ok();
        }
        UserDTO userDTO = BeanUtil.fillBeanWithMap(userMap, new UserDTO(), false);
        return Result.ok(userConverter.dtoToVo(userDTO));
    }

    @Override
    public Result<Boolean> updateUser(UserVo userVo,String token) {
        UserDTO userDTO = userConverter.voToDto(userVo);
        //校验
        Result<Boolean> validate = ValidateUtil.userValidate(userDTO);
        if (validate.getData()) {
            return validate;
        }
        //修改数据
        this.updateCacheUser(userDTO,token);
        User user = userConverter.dtoToDo(userDTO);
        cacheUtil.delete(CacheConstant.USER.getKeyPrefix() + user.getUserId());
        this.updateById(user);
        return Result.ok(true);
    }

    private void updateCacheUser(UserDTO userDTO,String token) {
        HashMap<String, String> map = new HashMap<>();
        map.put("nickname",gson.toJson(userDTO.getNickname()));
        map.put("signature",gson.toJson(userDTO.getSignature()));
        map.put("gender",gson.toJson(userDTO.getGender()));
        map.put("email",gson.toJson(userDTO.getEmail()));
        map.put("qq",gson.toJson(userDTO.getQq()));
        map.put("tagNames",gson.toJson(userDTO.getTagNames()));
        stringRedisTemplate.opsForHash().putAll(CacheConstant.LOGIN_USER.getKeyPrefix() + token,map);
    }
}




