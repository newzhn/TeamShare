package com.zhn.teamsharebackend.config;

import com.zhn.teamsharebackend.interceptor.CorsInterceptor;
import com.zhn.teamsharebackend.interceptor.LoginInterceptor;
import com.zhn.teamsharebackend.interceptor.RefreshTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author zhn
 * @version 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).order(3);
        registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate)).order(1);
        registry.addInterceptor(new CorsInterceptor()).order(0);
    }
}
