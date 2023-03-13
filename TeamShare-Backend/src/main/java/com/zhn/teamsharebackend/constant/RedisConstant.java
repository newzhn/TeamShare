package com.zhn.teamsharebackend.constant;

/**
 * Redis相关常量
 *
 * @author zhn
 * @version 1.0
 */
public interface RedisConstant {
    String REGISTER_CODE_KEY = "TeamShare:register:code:";
    Long REGISTER_CODE_TTL = 60L;

    String LOGIN_USER_KEY = "TeamShare:login:user:";
    Long LOGIN_USER_TTL = 60L;
}
