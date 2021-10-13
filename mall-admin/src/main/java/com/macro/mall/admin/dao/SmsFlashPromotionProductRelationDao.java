package com.macro.mall.admin.dao;

import com.macro.mall.admin.dto.SmsFlashPromotionProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 限时购关联商品自定义Dao
 * @version 1.0
 * @Author lj
 * @date 2021/10/13 4:19 下午
 */
public interface SmsFlashPromotionProductRelationDao {
    /**
     * 获取限时购和关联商品的信息
     */
    List<SmsFlashPromotionProduct> getList(@Param("flashPromotionId") Long promotionId, @Param("flashPromotionSessionId") Long sessionId);
}
