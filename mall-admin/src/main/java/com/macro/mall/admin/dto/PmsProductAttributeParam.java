package com.macro.mall.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/9 11:31 上午
 * @desc
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PmsProductAttributeParam {

    @NotEmpty
    @ApiModelProperty("属性分类id")
    private Long productAttributeCategoryId;

    @NotEmpty
    @ApiModelProperty("属性名称")
    private String name;



}
