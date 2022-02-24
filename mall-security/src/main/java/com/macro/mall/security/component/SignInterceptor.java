package com.macro.mall.security.component;

import com.macro.mall.common.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @version 1.0
 * @Author lj
 * @date 2022/2/22 下午4:11
 * @desc
 */

//@Component
//public class SignInterceptor implements HandlerInterceptor {
//
//    @Autowired private RedisService redisService;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        return false;
//    }
//}
