package com.zhn.teamsharebackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhn.teamsharebackend.constant.CacheConstant;
import com.zhn.teamsharebackend.constant.SystemConstant;
import com.zhn.teamsharebackend.domain.Result;
import com.zhn.teamsharebackend.domain.User;
import com.zhn.teamsharebackend.domain.request.RegisterRequest;
import com.zhn.teamsharebackend.constant.ErrorCode;
import com.zhn.teamsharebackend.service.EmailService;
import com.zhn.teamsharebackend.service.RegisterService;
import com.zhn.teamsharebackend.service.UserService;
import com.zhn.teamsharebackend.utils.CacheUtil;
import com.zhn.teamsharebackend.utils.RegexUtil;
import com.zhn.teamsharebackend.utils.ValidateUtil;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.Collections;

/**
 * 注册相关服务
 *
 * @author zhn
 * @version 1.0
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    @Resource
    private UserService userService;
    @Resource
    private EmailService emailService;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private CacheUtil cacheUtil;

    @Override
    public Result<Boolean> checkUserName(String userName) {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",userName);
        long count = userService.count(wrapper);
        return Result.ok(count > 0);
    }

    @Override
    public Result<Boolean> checkEmail(String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        long count = userService.count(wrapper);
        return Result.ok(count > 0);
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
        cacheUtil.set(CacheConstant.REGISTER_CODE,email,code);
        //发送验证码
        emailService.sendVerificationCode(email,code);
        //返回结果
        return Result.ok(true);
    }

    @Override
    public Result<Boolean> register(RegisterRequest registerRequest) {
        User user = BeanUtil.copyProperties(registerRequest, User.class);
        String userName = user.getUserName();
        String email = user.getEmail();
        String qq = user.getQq();
        String userPassword = user.getUserPassword();
        String code = registerRequest.getVerificationCode();
        //对注册信息做校验
        Result<Boolean> validateResult = ValidateUtil.registerValidate(userName, email, qq, userPassword, code);
        if (validateResult.getData()) {
            return validateResult;
        }
        if(checkEmail(email).getData() || checkUserName(userName).getData()) {
            return Result.fail(ErrorCode.REGISTERED);
        }
        //判断验证码是否正确
        String cacheCode = cacheUtil.get(CacheConstant.REGISTER_CODE.getKeyPrefix() + email);
        if (cacheCode == null || !cacheCode.equals(code)) {
            return Result.fail(ErrorCode.PARAMS_ERROR,false,"验证码错误");
        }
        //如果正确，则根据QQ号获取昵称和头像,若昵称和头像获取失败，则使用默认配置，后续可以用消息队列优化 TODO
        setAvatarUrlAndNickname(user,restTemplate);
        //对密码加密
        userPassword = DigestUtils.md5DigestAsHex((SystemConstant.SALT + userPassword).getBytes());
        user.setUserPassword(userPassword);
        //保存信息到数据库
        boolean save = userService.save(user);
        //信息存入缓存中
        if (save) {
            userService.getById(user.getUserId());
        }
        return Result.ok(save);
    }

    private void setAvatarUrlAndNickname(User user,RestTemplate restTemplate) {
        String qq = user.getQq();
        user.setAvatarUrl(SystemConstant.QQ_AVATAR_API + qq + "&s=100");
        String nickname = "";
        try {
            restTemplate.setMessageConverters(Collections.singletonList(new StringHttpMessageConverter(Charset.forName("GBK"))));
            String url = SystemConstant.QQ_NICKNAME_API + qq;
            String response = restTemplate.getForObject(url, String.class);
            nickname = response.split(",")[6];
            nickname = nickname.substring(1, nickname.length() - 1);
        } catch (Exception e) {
            nickname = qq;
        }
        user.setNickname(nickname);
    }
}
