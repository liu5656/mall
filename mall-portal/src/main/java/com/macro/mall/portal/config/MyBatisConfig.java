package com.macro.mall.portal.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/9/28 9:55 上午
 * @desc
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.macro.mall.mapper", "com.macro.mall.admin.dao"})
public class MyBatisConfig {
}
