package com.macro.mall.admin.dto;

import com.macro.mall.admin.validator.FlagValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/11 4:55 下午
 * @desc
 */
@Data
public class PmsBrandParam {

    @NotEmpty
    @ApiModelProperty(value = "品牌名称", required = true)
    private String name;

    @ApiModelProperty(value = "品牌首字母")
    private String firstLetter;

    @Min(value = 0)
    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    @FlagValidator(value = {"0", "1"}, message = "厂家状态")
    @ApiModelProperty(value = "是否为厂家制造商")
    private Integer factoryStatus;

    @FlagValidator(value = {"0", "1"}, message = "显示状态不正确")
    @ApiModelProperty(value = "是否进行显示")
    private Integer showStatus;

    @NotEmpty
    @ApiModelProperty(value = "品牌logo", required = true)
    private String logo;

    @ApiModelProperty("品牌大图")
    private String bigPic;

    @ApiModelProperty("品牌故事")
    private String brandStory;

}
