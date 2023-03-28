package com.zhn.teamsharebackend.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhn.teamsharebackend.constant.RedisConstant;
import com.zhn.teamsharebackend.constant.SystemConstant;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.User;
import com.zhn.teamsharebackend.domain.dto.RegisterForm;
import com.zhn.teamsharebackend.exception.ErrorCode;
import com.zhn.teamsharebackend.mapper.UserMapper;
import com.zhn.teamsharebackend.service.EmailService;
import com.zhn.teamsharebackend.service.RegisterService;
import com.zhn.teamsharebackend.utils.CacheUtil;
import com.zhn.teamsharebackend.utils.RegexUtil;
import com.zhn.teamsharebackend.utils.ValidateUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 注册相关服务
 *
 * @author zhn
 * @version 1.0
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private EmailService emailService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private CacheUtil cacheUtil;

    @Override
    public Result<Boolean> checkUserName(String userName) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",userName);
        boolean exists = userMapper.exists(wrapper);
        return Result.ok(exists);
    }

    @Override
    public Result<Boolean> checkEmail(String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        boolean exists = userMapper.exists(wrapper);
        return Result.ok(exists);
    }

    @Override
    public Result<Boolean> sendCode(String email) {
        //校验邮箱
        if(RegexUtil.isEmailInvalid(email)) {
            return Result.fail(ErrorCode.PARAMS_ERROR,"邮箱格式不正确");
        }
        //若成功，则创建验证码
        String code = RandomUtil.randomNumbers(6);
        //保存验证码到Redis，设置有效期，用邮箱做key
        String key = RedisConstant.REGISTER_CODE_KEY + email;
        stringRedisTemplate.opsForValue().set(key,code,RedisConstant.REGISTER_CODE_TTL, TimeUnit.SECONDS);
        //发送验证码
        emailService.sendVerificationCode(email,code);
        //返回结果
        return Result.ok(true);
    }

    @Override
    public Result<Boolean> register(RegisterForm registerForm) {
        String userName = registerForm.registerName;
        String email = registerForm.registerEmail;
        String qq = registerForm.registerQQ;
        String userPassword = registerForm.registerPass;
        String code = registerForm.verificationCode;
        //对注册信息做校验
        Result<Boolean> validateResult = ValidateUtil.registerValidate(userName, email, qq, userPassword, code);
        if (validateResult.getData()) {
            return validateResult;
        }
        if(checkEmail(email).getData() || checkUserName(userName).getData()) {
            return Result.fail(ErrorCode.REGISTERED);
        }
        //判断验证码是否正确
        String key = RedisConstant.REGISTER_CODE_KEY + email;
        String cacheCode = stringRedisTemplate.opsForValue().get(key);
        if (cacheCode == null || !cacheCode.equals(code)) {
            return Result.fail(ErrorCode.PARAMS_ERROR,false,"验证码错误");
        }
        //如果正确，则根据QQ号获取昵称和头像,若昵称和头像获取失败，则使用默认配置，后续可以用消息队列优化 TODO
        String avatarUrl = getQQAvatarUrl(qq);
        String nickname = getQQNickname(qq, restTemplate);
        //对密码加密
        userPassword = DigestUtils.md5DigestAsHex((SystemConstant.SALT + userPassword).getBytes());
        //保存信息到数据库
        User user = new User(nickname, userName, userPassword, email, qq, avatarUrl);
        int i = userMapper.insert(user);
        //信息存入缓存中
        if (i > 0) {
            cacheUtil.set(RedisConstant.USER_KEY + user.getUserId(),user,RedisConstant.USER_TTL,TimeUnit.HOURS);
        }
        return Result.ok(i > 0);
    }

    public static String getQQAvatarUrl(String qq) {
        return SystemConstant.QQ_AVATAR_API + qq + "&s=100";
    }

    public static String getQQNickname(String qq,RestTemplate restTemplate) {
        String nickname = "";
        try {
            restTemplate.setMessageConverters(Collections.singletonList(new StringHttpMessageConverter(Charset.forName("GBK"))));
            String url = SystemConstant.QQ_NICKNAME_API + qq;
            String response = restTemplate.getForObject(url, String.class);
            nickname = response.split(",")[6];
        } catch (Exception e) {
            nickname = qq;
        }
        return nickname.substring(1, nickname.length() - 1);
    }
}
