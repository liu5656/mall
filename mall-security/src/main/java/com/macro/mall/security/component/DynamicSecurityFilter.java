package com.macro.mall.security.component;

import com.macro.mall.security.config.IgnoreUrlsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 动态权限过滤器，用于实现基于路径的动态权限过滤
 * @version 1.0
 * @Author lj
 * @date 2021/9/23 3:04 下午
 * @desc
 */
@Component
public class DynamicSecurityFilter extends AbstractSecurityInterceptor implements Filter {
    @Autowired private DynamicSecurityMetadataSource metadataSource;
    @Autowired private IgnoreUrlsConfig urlsConfig;

    @Autowired
    public void setMyAccessDecisionManager(DynamicAccessDecisionManager dynamicAccessDecisionManager) {
        super.setAccessDecisionManager(dynamicAccessDecisionManager);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        FilterInvocation invocation = new FilterInvocation(servletRequest, servletResponse, filterChain);
        // 允许options请求
        if (request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
            invocation.getChain().doFilter(invocation.getRequest(), invocation.getResponse());
            return;
        }
        // 允许白名单请求
        AntPathMatcher matcher = new AntPathMatcher();
        for (String url : urlsConfig.getUrls()) {
            if (matcher.match(url, request.getRequestURI())) {
                invocation.getChain().doFilter(invocation.getRequest(), invocation.getResponse());
                return;
            }
        }
        // 调用AccessDecisionManager中的decide方法进行鉴权
        InterceptorStatusToken token = super.beforeInvocation(invocation);
        try{
            invocation.getChain().doFilter(invocation.getRequest(), invocation.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return metadataSource;
    }
}
