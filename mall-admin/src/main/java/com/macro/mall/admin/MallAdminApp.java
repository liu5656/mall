package com.macro.mall.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/9/23 10:02 上午
 * @desc
 */
@SpringBootApplication(scanBasePackages = "com.macro.mall")
public class MallAdminApp {
    public static void main(String[] args) {
        SpringApplication.run(MallAdminApp.class, args);
    }
}
