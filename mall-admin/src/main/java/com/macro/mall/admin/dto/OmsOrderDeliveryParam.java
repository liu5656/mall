package com.macro.mall.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单发货参数
 * @version 1.0
 * @Author lj
 * @date 2021/10/14 5:13 下午
 * @desc
 */
@Data
public class OmsOrderDeliveryParam {

    @ApiModelProperty("订单ID")
    private Long orderId;

    @ApiModelProperty("物流公司")
    private String deliveryCompany;

    @ApiModelProperty("物流单号")
    private String deliverySn;

}
