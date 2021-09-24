package com.macro.mall.security.component;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * 动态权限相关业务类
 * @version 1.0
 * @Author lj
 * @date 2021/9/22 11:53 上午
 */
public interface DynamicSecurityService {
    /**
     * 加载资源ANT通配符和资源对应的MAP
     */
    Map<String, ConfigAttribute> loadDataSource();
}
