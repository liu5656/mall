package com.macro.mall.portal.domain;

import com.macro.mall.model.CmsSubject;
import com.macro.mall.model.PmsBrand;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.model.SmsHomeAdvertise;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 首页内容返回封装
 * @version 1.0
 * @Author lj
 * @date 2021/10/21 11:03 上午
 * @desc
 */
@Setter
@Getter
public class HomeContentResult {
    private List<SmsHomeAdvertise> advertiseList;       // 轮播
    private List<PmsBrand> brandList;                   // 推荐品牌
    private HomeFlashPromotion homeFlashPromotion;      // 当前秒杀场次
    private List<PmsProduct> newProductList;            // 新品推荐
    private List<PmsProduct> hotProductList;            // 人气推荐
    private List<CmsSubject> subjectList;               // 推荐专题
}
