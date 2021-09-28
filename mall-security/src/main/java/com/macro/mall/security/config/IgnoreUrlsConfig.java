package com.macro.mall.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于配置白名单资源路径
 * @version 1.0`
 * @Author lj
 * @date 2021/9/23 10:33 上午
 * @desc
 */
@Data
@ConfigurationProperties(prefix = "secure.ignored")
@Component
public class IgnoreUrlsConfig {
    private List<String> urls = new ArrayList<>();
}
