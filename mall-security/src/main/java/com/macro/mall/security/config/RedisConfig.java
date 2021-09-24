package com.macro.mall.security.config;

import com.macro.mall.common.config.BaseRedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/9/23 5:17 下午
 * @desc
 */
@Configuration
@EnableCaching
public class RedisConfig extends BaseRedisConfig {
}
