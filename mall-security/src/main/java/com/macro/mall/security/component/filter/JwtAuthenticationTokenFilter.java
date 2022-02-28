package com.macro.mall.security.component.filter;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.macro.mall.common.exception.SignException;
import com.macro.mall.common.service.RedisService;
import com.macro.mall.security.component.CustomHttpServletRequestWrapper;
import com.macro.mall.security.util.JwtTokenUtil;
import com.macro.mall.security.util.SignatureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * JWT登录授权过滤器
 * @version 1.0
 * @Author lj
 * @date 2021/9/23 2:12 下午
 * @desc
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static Logger log = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Autowired private UserDetailsService userDetailsService;
    @Autowired private JwtTokenUtil tokenUtil;
    @Autowired private RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        if (request.getMethod().toString().equals(HttpMethod.OPTIONS.toString())) {
            chain.doFilter(request, response);
            return;
        }

        CustomHttpServletRequestWrapper requestWrapper = new CustomHttpServletRequestWrapper(request);
         Map<String, Object> map = (Map<String, Object>) JSONUtil.parse(requestWrapper.body);
         log.info("路径" + requestWrapper.getPathInfo() + " 参数： " + map.toString());
         try {
             SignatureUtil.verifySignature(map);
             timestampValid(map.get("timestamp").toString());
             String nonce = map.get("nonce").toString();
             nonceValid(nonce);
             redisService.set(nonce, 0,60);
         }catch (Exception e) {
             e.printStackTrace();
         }
        // 校验JWT
        String header = request.getHeader(tokenHeader);

        if (header != null && header.startsWith(tokenHead)) {
            String authToken = header.substring(tokenHead.length());
            String username = tokenUtil.retrieveUsername(authToken);
            log.info("checking username:{}", username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (tokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    log.info("authenticated user:{}", username);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(requestWrapper, response);
    }

    private boolean timestampValid(String timestamp) throws IOException {
        Date begin = DateUtil.date(Long.parseLong(timestamp));
        Date end = DateUtil.date();
        long delta = DateUtil.between(begin, end, DateUnit.SECOND);
        if (delta > 60) {   // timeout
            throw new IOException("时间过期");
        }
        return true;
    }

    private  boolean nonceValid(String nonce) throws IOException {
        if (redisService.hasKey(nonce)) {
            throw new IOException("重复请求");
        }
        return true;
//        boolean res = redisService.hasKey(nonce);
//        if (res == false) {
//            redisService.set(nonce, 0,60);
//        }
//        return res == false;
    }
}
