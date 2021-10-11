package com.macro.mall.admin.dto;

import com.macro.mall.model.PmsProductCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/11 11:39 上午
 * @desc
 */
@Data
public class PmsProductCategoryWithChildrenItem extends PmsProductCategory {
    @ApiModelProperty("子级分类")
    private List<PmsProductCategory> children;
}
