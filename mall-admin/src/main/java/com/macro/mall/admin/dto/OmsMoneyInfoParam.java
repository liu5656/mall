package com.macro.mall.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 修改订单费用信息参数
 * @version 1.0
 * @Author lj
 * @date 2021/10/14 5:22 下午
 * @desc
 */
@Data
public class OmsMoneyInfoParam {

    @ApiModelProperty("订单ID")
    private Long orderId;

    @ApiModelProperty("运费金额")
    private BigDecimal freightAmount;

    @ApiModelProperty("管理员后台调整订单使用的折扣金额")
    private BigDecimal discountAmount;

    @ApiModelProperty("订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单")
    private Integer status;

}
