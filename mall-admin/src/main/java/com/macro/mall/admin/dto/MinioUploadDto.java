package com.macro.mall.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件上传返回结果
 * @version 1.0
 * @Author lj
 * @date 2022/3/3 下午2:54
 * @desc
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class MinioUploadDto {
    @ApiModelProperty("文件访问url")
    private String url;
    @ApiModelProperty("文件名称")
    private String name;
}
