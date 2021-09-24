package com.macro.mall.common.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * swagger自定义配置
 * @version 1.0
 * @Author lj
 * @date 2021/9/24 11:05 上午
 * @desc
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class SwaggerProperties {
    /**
     * api文档基础路径
     */
    private String apiBasePackage;

    /**
     * 是否要启用登录认证
     */
    private boolean enableSecurity;

    /**
     * 文档标题
     */
    private String title;

    /**
     * 文档描述
     */
    private String description;

    /**
     * 文档版本
     */
    private String version;

    /**
     * 文档负责人
     */
    private String contactName;

    /**
     * 文档负责人邮箱
     */
    private String contactEmail;

    /**
     * 文档负责人网页
     */
    private String contactUrl;
}
