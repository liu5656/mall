package com.macro.mall.admin.dto;

import com.macro.mall.admin.validator.FlagValidator;
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

    @FlagValidator({"0","1","2"})
    @ApiModelProperty("属性选择类型：0->唯一；1->单选；2->多选")
    private Integer selectType;

    @FlagValidator({"0", "1"})
    @ApiModelProperty("属性录入方式：0-手工，1-从列表中选取")
    private Integer inputType;

    @ApiModelProperty("可选值列表，以逗号隔开")
    private String inputList;

    private Integer sort;

    @FlagValidator({"0","1"})
    @ApiModelProperty("分类筛选样式：0-普通，1-颜色")
    private Integer filterType;

    @FlagValidator({"0","1","2"})
    @ApiModelProperty("检索类型：0-不需要进行检索，1-关键词，2-范围")
    private Integer searchType;

    @ApiModelProperty("相同属性产品是否关联：0-否，1-是")
    @FlagValidator({"0","1"})
    private Integer relatedStatus;

    @ApiModelProperty("是否支持手动新增：0-否，1-是")
    @FlagValidator({"0","1"})
    private Integer handAddStatus;

    @ApiModelProperty("属性类型：0-规格，1-参数")
    @FlagValidator({"0", "1"})
    private Integer type;

}
