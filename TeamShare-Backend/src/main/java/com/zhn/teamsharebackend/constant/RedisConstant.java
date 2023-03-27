package com.zhn.teamsharebackend.constant;

/**
 * Redis相关常量
 *
 * @author zhn
 * @version 1.0
 */
public interface RedisConstant {
    Long NULL_DATA_TTL = 10L;

    String REGISTER_CODE_KEY = "TeamShare:register:code:";
    Long REGISTER_CODE_TTL = 60L;

    String LOGIN_USER_KEY = "TeamShare:login:";
    Long LOGIN_USER_TTL = 60L;

    String USER_KEY = "TeamShare:user:";
    Long USER_TTL = 12L;

    String TEAM_KEY = "TeamShare:team:";
    Long TEAM_TTL = 12L;
    String TEAM_LIST_KEY = "TeamShare:team:list:";
    Long TEAM_LIST_TTL = 12L;
}
