package com.macro.mall.admin.config;

import com.macro.mall.common.config.BaseSwaggerConfig;
import com.macro.mall.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/9/24 11:41 上午
 * @desc
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {
    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.macro.mall.admin.controller")
                .title("mall后台系统")
                .description("mall后台相关接口文档")
                .contactName("lj")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
