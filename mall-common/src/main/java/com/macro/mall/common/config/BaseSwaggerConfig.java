package com.macro.mall.common.config;

import com.macro.mall.common.domain.SwaggerProperties;
//import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/9/24 11:03 上午
 * @desc
 */

public abstract class BaseSwaggerConfig {

    @Bean
    public Docket createRestApi() {
        SwaggerProperties properties = swaggerProperties();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(properties))
                .select()
                .apis(RequestHandlerSelectors.basePackage(properties.getApiBasePackage()))
                .paths(PathSelectors.any())
                .build();
        if (properties.isEnableSecurity()) {
            docket.securitySchemes(securitySchemes())
                    .securityContexts(securityContexts());
        }
        return docket;
    }

    private ApiInfo apiInfo(SwaggerProperties properties) {
        return new ApiInfoBuilder()
                .title(properties.getTitle())
                .description(properties.getDescription())
                .version(properties.getVersion())
                .contact(new Contact(properties.getContactName(), properties.getContactUrl(), properties.getContactEmail()))
                .build();
    }

    /**
     * 设置请求头信息
     */
    private List<ApiKey> securitySchemes() {
        List<ApiKey> keys = new ArrayList<>();
        ApiKey key = new ApiKey("Authorization", "Authorization", "header");
        keys.add(key);
        return keys;
    }

    /**
     * 设置需要登录的路径
     */
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> contexts = new ArrayList<>();
        contexts.add(getContextByPath("/*/.*"));
        return contexts;
    }
    private SecurityContext getContextByPath(String path) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(path))
                .build();
    }
    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> result = new ArrayList<>();
        AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] scopes = {scope};
        result.add(new SecurityReference("Authorization", scopes));
        return result;
    }



    /**
     * 自定义swagger配置类
     */
    public abstract SwaggerProperties swaggerProperties();
}
