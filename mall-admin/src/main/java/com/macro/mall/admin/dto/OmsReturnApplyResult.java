package com.macro.mall.admin.dto;

import com.macro.mall.model.OmsCompanyAddress;
import com.macro.mall.model.OmsOrderReturnApply;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/19 10:35 上午
 * @desc
 */
public class OmsReturnApplyResult extends OmsOrderReturnApply {

    @Setter
    @Getter
    @ApiModelProperty("公司收货地址")
    private OmsCompanyAddress companyAddress;

}
