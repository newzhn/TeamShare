package com.zhn.teamsharebackend.utils;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 实现逻辑过期解决缓存击穿的数据结构
 * @author:zhn
 * @version 1.0
 */
@Data
public class RedisData {
    private LocalDateTime expireTime;
    private Object data;
}
