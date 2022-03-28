package com.macro.mall.admin.config;


import com.macro.mall.admin.service.UmsAdminService;
import com.macro.mall.admin.service.UmsResourceService;
import com.macro.mall.model.UmsResource;
import com.macro.mall.security.component.DynamicSecurityService;
import com.macro.mall.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * mall-security模块相关配置
 * @version 1.0
 * @Author lj
 * @date 2021/9/18 10:44 上午
 *
 * @desc
 */
@Configuration
@EnableWebSecurity
// 开启Spring方法级安全，
//（1）prePostEnabled :决定Spring Security的前注解是否可用[@PreAuthorize,@PostAuthorize,…]，如方法上面的@PreAuthorize("hasAnyRole('admin','normal')")才会生效。
//（2）secureEnabled : 决定是否Spring Security的保障注解 [@Secured] 是否可用。
//（3）jsr250Enabled ：决定 JSR-250 annotations 注解[@RolesAllowed…] 是否可用。
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MallSecurityConfig extends SecurityConfig {
    @Autowired private UmsAdminService adminService;
    @Autowired private UmsResourceService resourceService;

    /**
     * 获取登录用户信息
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> adminService.loadUserByUsername(username);
    }

    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
                List<UmsResource> resourceList = resourceService.listAll();
                for (UmsResource resource : resourceList) {
                    map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
                }
                return map;
            }
        };
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
    }
}
