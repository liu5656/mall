package com.macro.mall.admin.dto;

import com.macro.mall.model.PmsProduct;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 查询单个商品进行修改时返回的结果
 * @version 1.0
 * @Author lj
 * @date 2021/10/11 4:42 下午
 * @desc
 */
public class PmsProductResult extends PmsProductParam {
    @Setter
    @Getter
    @ApiModelProperty("商品所选分类的父id")
    private Long cateParentId;
}
