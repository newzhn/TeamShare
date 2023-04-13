package com.zhn.teamsharebackend.constant;

import com.zhn.teamsharebackend.domain.Article;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

/**
 * The enum Cache constant.
 *
 * @author zhn
 * @version 1.0
 */
public enum CacheConstant {
    //缓存信息
    NULL_DATA("",60L,SECONDS),
    REGISTER_CODE("TeamShare:register:code:",60L,SECONDS),
    LOGIN_USER("TeamShare:login:",60L,MINUTES),
    USER("TeamShare:user:",60L,MINUTES),
    TEAM("TeamShare:team:",60L,MINUTES),
    CATEGORY("TeamShare:category:",60L,MINUTES),
    CATEGORY_LIST("TeamShare:category:",60L,MINUTES),
    TAG("TeamShare:tag:",60L,MINUTES),
    TAG_LIST("TeamShare:tag:",60L,MINUTES),
    ARTICLE("TeamShare:article:",60L,MINUTES),
    COMMENT("TeamShare:article:",60L,MINUTES);

    /**
     * key前缀
     */
    private final String keyPrefix;

    /**
     * 过期时间
     */
    private final Long ttl;

    /**
     * 时间单位
     */
    private final TimeUnit unit;

    CacheConstant(String keyPrefix, Long ttl, TimeUnit unit) {
        this.keyPrefix = keyPrefix;
        this.ttl = ttl;
        this.unit = unit;
    }

    /**
     * Gets key prefix.
     *
     * @return the key prefix
     */
    public String getKeyPrefix() {
        return keyPrefix;
    }

    /**
     * Gets ttl.
     *
     * @return the ttl
     */
    public Long getTtl() {
        return ttl;
    }

    /**
     * Gets unit.
     *
     * @return the unit
     */
    public TimeUnit getUnit() {
        return unit;
    }
}
