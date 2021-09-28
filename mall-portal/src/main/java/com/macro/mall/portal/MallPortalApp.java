package com.macro.mall.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/9/27 3:38 下午
 * @desc
 */
@SpringBootApplication(scanBasePackages = "com.macro.mall")
public class MallPortalApp {
    public static void main(String[] args) {
        SpringApplication.run(MallPortalApp.class, args);
    }
}
