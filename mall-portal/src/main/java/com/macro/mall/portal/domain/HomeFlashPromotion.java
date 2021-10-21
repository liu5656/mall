package com.macro.mall.portal.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 首页秒杀信息封装
 * @version 1.0
 * @Author lj
 * @date 2021/10/21 11:07 上午
 * @desc
 */
@Setter
@Getter
public class HomeFlashPromotion {

    private Date startTime;                             // 开始时间
    private Date endTime;                               // 结束时间
    private Date nextStartTime;                         // 下场开始时间
    private Date nextEndTime;                           // 下场结束时间
    private List<FlashPromotionProduct> productList;    // 当前秒杀活动商品

}
