package com.macro.mall.admin.config;

import com.macro.mall.common.config.BaseRedisConfig;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/9/24 10:16 上午
 * @desc
 */
@Configuration
public class AdminRedisConfig extends BaseRedisConfig {
    //TODO 暂时未搞懂，为什么通过继承才能引入其他模块的Bean
}
