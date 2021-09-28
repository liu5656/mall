package com.macro.mall.security.config;

import com.macro.mall.security.component.*;
import com.macro.mall.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * 对SpringSecurity的配置扩展，支持自定义白名单资源路径和查询用户逻辑
 * @version 1.0
 * @Author lj
 * @date 2021/9/18 10:36 上午
 * @desc
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired(required = false) private DynamicSecurityService dynamicSecurityService;
    @Autowired private IgnoreUrlsConfig ignoreUrlsConfig;
    @Autowired private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();

//        registry.antMatchers("/**")//测试时全部运行访问
//                .permitAll();

        System.out.println("------------开始过滤路径");
        // 不需要保护的资源路径允许访问
        for (String url : ignoreUrlsConfig.getUrls()) {
            System.out.println("------------开始过滤路径");
            registry.antMatchers(url).permitAll();
        }
        // 允许跨域请求的options
        registry.antMatchers(HttpMethod.OPTIONS).permitAll();

        // 其他请求需要身份认证
        registry.and().authorizeRequests().anyRequest().authenticated();
        // 关闭跨站请求防护，不实用session
        registry.and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 自定义权限拒绝处理类
        registry.and().exceptionHandling().accessDeniedHandler(restfulAccessDeniedHandler).authenticationEntryPoint(restAuthenticationEntryPoint);
        // 自定义权限拦截器JWT过滤器
        registry.and().addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 有动态权限配置时添加动态权限校验过滤器
        if (dynamicSecurityService != null) {
            registry.and().addFilterBefore(dynamicSecurityFilter(), FilterSecurityInterceptor.class);
        }
    }

    @Bean
    @ConditionalOnBean(name = "dynamicSecurityService")
    public DynamicSecurityFilter dynamicSecurityFilter() {
        return new DynamicSecurityFilter();
    }

    @Bean
    @ConditionalOnBean(name = "dynamicSecurityService")
    public DynamicAccessDecisionManager dynamicAccessDecisionManager() {
        return new DynamicAccessDecisionManager();
    }

    @Bean
    @ConditionalOnBean(name = "dynamicSecurityService")
    public DynamicSecurityMetadataSource dynamicSecurityMetadataSource() {
        return new DynamicSecurityMetadataSource();
    }

//    @Bean
//    public IgnoreUrlsConfig ignoreUrlsConfig() {
//        return new IgnoreUrlsConfig();
//    }
//    @Bean
//    public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
//        return new RestfulAccessDeniedHandler();
//    }
//    @Bean
//    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
//        return new RestAuthenticationEntryPoint();
//    }
//    @Bean
//    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
//        return new JwtAuthenticationTokenFilter();
//    }
//    @Bean
//    public JwtTokenUtil jwtTokenUtil() {
//        return new JwtTokenUtil();
//    }
    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

}
