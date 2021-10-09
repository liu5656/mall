package com.macro.mall.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品分类对应属性信息
 * @version 1.0
 * @Author lj
 * @date 2021/10/9 2:05 下午
 * @desc
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PmsProductAttrInfo {

    @ApiModelProperty("商品属性id")
    private Long attributeId;

    @ApiModelProperty("商品属性分类id")
    private Long attributeCategoryId;

}
