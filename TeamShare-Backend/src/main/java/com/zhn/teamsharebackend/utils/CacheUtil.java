package com.zhn.teamsharebackend.utils;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 对缓存相关操作进行封装
 *
 * @author zhn
 * @version 1.0
 */
public class CacheUtil {
    private StringRedisTemplate stringRedisTemplate;
    private Gson gson;
    private static final ExecutorService CACHE_REBUILD_EXECUTOR = Executors.newFixedThreadPool(10);

    public CacheUtil(StringRedisTemplate stringRedisTemplate,Gson gson) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.gson = gson;
    }

    /**
     * 以json字符串形式向Redis存储数据
     * @param key key值
     * @param value value值
     * @param time 过期时间
     * @param unit 时间单位
     */
    public void set(String key, Object value, Long time, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, gson.toJson(value),time,unit);
    }

    /**
     * 读取Redis数据
     * @param key key值
     * @return 返回值
     */
    public Object get(String key, Type type) {
        String json = stringRedisTemplate.opsForValue().get(key);
        if (StrUtil.isBlank(json)) {
            return null;
        }
        return gson.fromJson(json, type);
    }

    /**
     * 删除键值对
     * @param key key值
     * @return 返回删除结果布尔值
     */
    public boolean delete(String key) {
        return BooleanUtil.isTrue(stringRedisTemplate.delete(key));
    }

    /**
     * 以字符串形式向Redis存储数据，不再由Redis控制过期时间，而是通过设置逻辑时间进行管理
     * @param key key值
     * @param value value值
     * @param time 过期时间
     * @param unit 时间单位
     */
    public void setWithLogicalExpire(String key, Object value, Long time, TimeUnit unit) {
        //设置逻辑过期时间
        RedisData redisData = new RedisData();
        redisData.setData(value);
        redisData.setExpireTime(LocalDateTime.now().plusSeconds(unit.toSeconds(time)));
        //写入缓存
        stringRedisTemplate.opsForValue().set(key, gson.toJson(redisData));
    }

    /**
     * 获取锁，底层通过setx的互斥性实现
     * @param key 锁名
     * @param time 锁超时时间
     * @return 返回获取锁是否成功
     */
    private boolean tryLock(String key,Long time) {
        Boolean isLock = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", time,TimeUnit.SECONDS);
        return BooleanUtil.isTrue(isLock);
    }

    /**
     * 释放锁
     * @param key 锁名
     */
    private void reliefLock(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 解决缓存穿透方案的默认实现
     * @param keyPrefix key前缀
     * @param id key后缀
     * @param type 存入Redis的数据结构类型
     * @param dbFallBack 传入一段函数，可以执行
     * @param time 过期时间
     * @param unit 时间单位
     * @param <R> 返回数据类型
     * @param <ID> key后缀类型
     * @return 返回处理结果
     */
    public <R,ID> R queryWithPassThrough(String keyPrefix, ID id, Class<R> type, Function<ID,R> dbFallBack,Long time, TimeUnit unit){
        return queryWithPassThrough(keyPrefix,id,type,dbFallBack,time,unit,2L);
    }

    /**
     * 解决缓存穿透方案的抽取实现
     * @param keyPrefix key前缀
     * @param id key后缀
     * @param type 存入Redis的数据结构类型
     * @param dbFallBack 传入一段函数，可以执行
     * @param time 过期时间
     * @param unit 时间单位
     * @param nullTimeMinutes 空数据的过期时间
     * @param <R> 返回数据类型
     * @param <ID> key后缀类型
     * @return 返回处理结果
     */
    public <R,ID> R queryWithPassThrough(
            String keyPrefix, ID id, Class<R> type, Function<ID,R> dbFallBack,Long time, TimeUnit unit, Long nullTimeMinutes) {
        String key = keyPrefix + id;
        //1. 从Redis缓存中获取信息
        String json = stringRedisTemplate.opsForValue().get(key);
        //2. 若命中数据，则直接返回结果
        if (StrUtil.isNotBlank(json)) {
            R r = gson.fromJson(json, type);
            return r;
        }
        //3. 若命中的是空字符串，则返回空
        if (json != null) {
            return null;
        }
        //4. 从数据库中查询信息
        R r = dbFallBack.apply(id);
        //5. 若没查到，则返回错误信息,将空值也存入缓存
        if (r == null) {
            stringRedisTemplate.opsForValue().set(key,"",nullTimeMinutes, TimeUnit.MINUTES);
            return null;
        }
        //6. 将信息写入Redis缓存,并设置超时时间
        this.set(key,r,time,unit);
        //7. 返回最终结果
        return r;
    }

    /**
     * 使用互斥锁解决缓存击穿方案的默认实现
     * @param keyPrefix key前缀
     * @param id key后缀
     * @param type 存入Redis的数据结构类型
     * @param dbFallBack 传入一段函数，可以执行
     * @param time 过期时间
     * @param unit 时间单位
     * @param <R> 返回数据类型
     * @param <ID> key后缀类型
     * @return 返回处理结果
     */
    public <R,ID> R queryWithMutex(
            String keyPrefix,String lockPrefix,ID id,Class<R> type,Function<ID,R> dbFallBack,Long time,TimeUnit unit) {
        return queryWithMutex(keyPrefix,lockPrefix,id,type,dbFallBack,time,unit,10L,2L);
    }

    /**
     * 使用互斥锁解决缓存击穿方案的抽取实现
     * @param keyPrefix key前缀
     * @param id key后缀
     * @param type 存入Redis的数据结构类型
     * @param dbFallBack 传入一段函数，可以执行
     * @param time 过期时间
     * @param unit 时间单位
     * @param lockTimeSeconds 锁的超时时间
     * @param nullTimeMinutes 锁的超时时间单位
     * @param <R> 返回数据类型
     * @param <ID> key后缀类型
     * @return 返回处理结果
     */
    public <R,ID> R queryWithMutex(
            String keyPrefix,String lockPrefix,ID id,Class<R> type,Function<ID,R> dbFallBack,Long time,TimeUnit unit,Long lockTimeSeconds,Long nullTimeMinutes) {
        R r = null;
        String key = keyPrefix + id;
        //1. 从缓存中查询数据
        String json = stringRedisTemplate.opsForValue().get(key);
        //2. 若命中数据，则直接返回数据
        if (StrUtil.isNotBlank(json)) {
            r = gson.fromJson(json, type);
            return r;
        }
        //3. 若命中空字符串，则返回null
        if (json != null) {
            return null;
        }
        //4. 未命中，则尝试获取锁,进行缓存重构
        String lockKey = lockPrefix + id;
        try {
            boolean isLock = tryLock(lockKey,lockTimeSeconds);
            //  4.1 获取失败，则短暂休眠后进行再次尝试
            if (!isLock) {
                Thread.sleep(50);
                return queryWithMutex(keyPrefix,lockPrefix,id,type,dbFallBack,time,unit,lockTimeSeconds,nullTimeMinutes);
            }
            //  4.2 获取成功，则从数据库中查询数据
            r = dbFallBack.apply(id);
            //  4.3 未查到数据，则返回空
            if (r == null) {
                stringRedisTemplate.opsForValue().set(key,"",nullTimeMinutes, TimeUnit.MINUTES);
                return null;
            }
            //  4.4 将查到数据写入缓存
            this.set(key,r,time,unit);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            //5. 释放锁
            reliefLock(lockKey);
        }
        return r;
    }

    /**
     * 使用逻辑过期解决缓存击穿的默认实现
     * @param keyPrefix key前缀
     * @param lockPrefix 锁的key前缀
     * @param id key后缀
     * @param type 存入Redis的数据结构类型
     * @param dbFallBack 传入一段函数，可以执行
     * @param time 过期时间
     * @param unit 过期时间单位
     * @param <R> 返回数据类型
     * @param <ID> key后缀类型
     * @return 返回处理结果
     */
    public  <R,ID> R queryWithLogicalExpire(
            String keyPrefix,String lockPrefix,ID id,Class<R> type,Function<ID,R> dbFallBack,Long time,TimeUnit unit) {
        return queryWithLogicalExpire(keyPrefix,lockPrefix,id,type,dbFallBack,time,unit,10L);
    }

    /**
     * 使用逻辑过期解决缓存击穿的抽取实现
     * @param keyPrefix key前缀
     * @param lockPrefix 锁的key前缀
     * @param id key后缀
     * @param type 存入Redis的数据结构类型
     * @param dbFallBack 传入一段函数，可以执行
     * @param time 过期时间
     * @param unit 过期时间单位
     * @param lockSeconds 锁过期时间
     * @param <R> 返回数据类型
     * @param <ID> key后缀类型
     * @return 返回处理结果
     */
    public  <R,ID> R queryWithLogicalExpire(
            String keyPrefix,String lockPrefix,ID id,Class<R> type,Function<ID,R> dbFallBack,Long time,TimeUnit unit,Long lockSeconds) {
        String key = keyPrefix + id;
        //1. 从缓存中查询数据
        String json = stringRedisTemplate.opsForValue().get(key);
        //2. 若未命中数据，直接返回null
        if (StrUtil.isBlank(json)) {
            return null;
        }
        //3. 命中数据，将数据反序列化为对象
        RedisData redisData = gson.fromJson(json, RedisData.class);
        R r = gson.fromJson( (JsonElement) redisData.getData(), type);
        LocalDateTime expireTime = redisData.getExpireTime();
        //4. 判断缓存是否过期
        if (expireTime.isAfter(LocalDateTime.now())) {
            //4.1 未过期，直接返回数据
            return r;
        }
        //5. 获取互斥锁，进行数据重载
        String lockKey = lockPrefix + id;
        boolean isLock = tryLock(lockKey,lockSeconds);
        //6. 判断锁是否获取成功
        if (isLock) {
            CACHE_REBUILD_EXECUTOR.submit(() -> {
                try {
                    //查询数据库
                    R r1 = dbFallBack.apply(id);
                    //重建缓存
                    this.setWithLogicalExpire(key,r1,time,unit);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    reliefLock(lockKey);
                }
            });
        }
        //7. 返回过期的信息
        return r;
    }
}
