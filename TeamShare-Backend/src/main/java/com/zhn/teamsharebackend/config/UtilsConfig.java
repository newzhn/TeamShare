package com.zhn.teamsharebackend.config;

import com.google.gson.Gson;
import com.zhn.teamsharebackend.utils.CacheUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

/**
 * 工具类配置
 *
 * @author zhn
 * @version 1.0
 */
@Configuration
public class UtilsConfig {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public CacheUtil cacheUtil() {
        return new CacheUtil(stringRedisTemplate,new Gson());
    }
}
