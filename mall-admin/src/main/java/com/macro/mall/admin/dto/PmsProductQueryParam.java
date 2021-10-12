package com.macro.mall.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/11 4:47 下午
 * @desc
 */
@Data
public class PmsProductQueryParam {

    @ApiModelProperty("上架状态")
    private Integer publishStatus;

    @ApiModelProperty("审核状态")
    private Integer verifyStatus;

    @ApiModelProperty("商品名称模糊关键字")
    private String keyword;

    @ApiModelProperty("商品货号")
    private String productSn;

    @ApiModelProperty("商品分类id")
    private Long productCategoryId;

    @ApiModelProperty("商品品牌Id")
    private Long brandId;

}
