package com.macro.mall.portal.config;

import com.macro.mall.common.config.BaseSwaggerConfig;
import com.macro.mall.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2接口文档配置
 * @version 1.0
 * @Author lj
 * @date 2021/9/28 11:18 上午
 * @desc
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {
    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.macro.mall.portal.controller")
                .title("mall前台系统")
                .description("mall前台系统相关接口文档")
                .contactName("lj")
                .version("1.0")
                .enableSecurity(true)
                .build();

    }
}
