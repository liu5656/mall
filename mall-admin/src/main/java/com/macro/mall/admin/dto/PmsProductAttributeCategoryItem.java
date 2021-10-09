package com.macro.mall.admin.dto;

import com.macro.mall.model.PmsProductAttribute;
import com.macro.mall.model.PmsProductAttributeCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/9 4:19 下午
 * @desc
 */
@Data
public class PmsProductAttributeCategoryItem extends PmsProductAttributeCategory {
    @ApiModelProperty("商品属性列表")
    private List<PmsProductAttribute> attributes;
}
