package com.macro.mall.security.component;

import cn.hutool.core.util.URLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/9/23 3:10 下午
 * @desc
 */
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private static Map<String, ConfigAttribute> configAttributeMap = null;

    @Autowired DynamicSecurityService securityService;

    @PostConstruct
    public void loadDataSource() { configAttributeMap = securityService.loadDataSource(); }

    public void clearDataSource() {
        configAttributeMap.clear();
        configAttributeMap = null;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        if (configAttributeMap == null) {
            loadDataSource();
        }
        List<ConfigAttribute> attributes = new ArrayList<>();
        // 获取当前访问路径
        String url = ((FilterInvocation)o).getRequestUrl();
        String path = URLUtil.getPath(url);
        AntPathMatcher matcher = new AntPathMatcher();
        Iterator<String> iterator = configAttributeMap.keySet().iterator();
        while (iterator.hasNext()) {
            String pattern = iterator.next();
            if (matcher.match(pattern, path)) {
                attributes.add(configAttributeMap.get(pattern));
            }
        }
        return attributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
