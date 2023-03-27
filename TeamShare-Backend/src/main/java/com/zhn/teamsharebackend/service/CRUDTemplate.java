package com.zhn.teamsharebackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhn.teamsharebackend.domain.Result;

/**
 * 封装IService接口，在其基础上增加缓存相关功能，定义基本增删改查接口模板，供service类实现
 *
 * @param <T> the type parameter 基本Domain
 * @param <R> the type parameter Domain类的DTO类
 * @author zhn
 * @version 1.0
 */
public interface CRUDTemplate<T,R> extends IService<T> {
    /**
     * Create result.
     *
     * @param t the t
     * @return the result
     */
    Result<Boolean> create(T t);

    /**
     * Delete result.
     *
     * @param id the id
     * @return the result
     */
    Result<Boolean> delete(long id);

    /**
     * Update result.
     *
     * @param t the t
     * @return the result
     */
    Result<Boolean> update(T t);

    /**
     * Get result.
     *
     * @param id the id
     * @return the result
     */
    Result<R> get(long id);

    /**
     * Convert r.
     *
     * @param t the t
     * @return the r
     */
    R convert(T t);
}
