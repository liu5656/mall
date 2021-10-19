package com.macro.mall.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 修改订单收货人信息
 * @version 1.0
 * @Author lj
 * @date 2021/10/14 5:00 下午
 * @desc
 */
@Data
public class OmsReceiverInfoParam {

    @ApiModelProperty("订单ID")
    private Long orderId;

    @ApiModelProperty("收货人姓名")
    private String receiverName;

    @ApiModelProperty("收货人电话")
    private String receiverPhone;

    @ApiModelProperty("收货人邮编")
    private String receiverPostCode;

    @ApiModelProperty("详细地址")
    private String receiverDetailAddress;

    @ApiModelProperty("省份/直辖市")
    private String receiverProvince;

    @ApiModelProperty("城市")
    private String receiverCity;

    @ApiModelProperty("区")
    private String receiverRegion;

    @ApiModelProperty("订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单")
    private Integer status;


}
