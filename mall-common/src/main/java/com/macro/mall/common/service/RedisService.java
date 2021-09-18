package com.macro.mall.common.service;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/9/6 4:08 下午
 * Redis操作Service
 * 对象和数组都以json的形式存储
 */
public interface RedisService {
    /**
     * 设置数据
     * @param key
     * @param value
     */
    void set(String key, String value);

    /**
     * 获取数据
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 设置过期时间
     * @param key
     * @param expire
     * @return
     */
    boolean expire(String key, long expire);

    /**
     * 删除数据
     * @param key
     */
    void remove(String key);

    /**
     * 自增
     * @param key
     * @param delta 步长
     * @return
     */
    Long increment(String key, long delta);
}
