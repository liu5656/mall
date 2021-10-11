package com.macro.mall.admin.dto;

import com.macro.mall.admin.validator.FlagValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 添加更新商品分类的参数
 * @version 1.0
 * @Author lj
 * @date 2021/10/11 11:11 上午
 * @desc
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PmsProductCategoryParam {

    @ApiModelProperty("父分类的Id")
    private Long parentId;

    @NotEmpty
    @ApiModelProperty(value = "商品分类名称", required = true)
    private String name;

    @ApiModelProperty("分类单位")
    private String productUnit;

    @ApiModelProperty("是否在导航栏中显示")
    @FlagValidator(value = {"0","1"}, message = "状态只能为0或1")
    private Integer navStatus;

    @ApiModelProperty("是否显示")
    @FlagValidator(value = {"0","1"}, message = "状态只能为0或1")
    private Integer showStatus;

    @Min(value = 0)
    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("关键字")
    private String keywords;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("产品相关筛选属性集合")
    private List<Long> productAttributeIdList;
}
