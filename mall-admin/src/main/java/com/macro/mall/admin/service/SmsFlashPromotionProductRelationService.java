package com.macro.mall.admin.service;

import com.macro.mall.admin.dto.SmsFlashPromotionProduct;
import com.macro.mall.model.SmsFlashPromotionProductRelation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 限时购和商品管理管理service
 * @version 1.0
 * @Author lj
 * @date 2021/10/13 4:50 下午
 */
public interface SmsFlashPromotionProductRelationService {

    /**
     * 批量添加关联
     */
    @Transactional
    int insertList(List<SmsFlashPromotionProductRelation> relations);

    /**
     * 删除关联
     */
    int delete(Long id);

    /**
     * 获取关联详情
     */
    SmsFlashPromotionProductRelation getItem(Long id);
    /**
     * 分页获取关联详情
     */
    List<SmsFlashPromotionProduct> getList(Long promotionId, Long sessionId, Integer pageNum, Integer pageSize);
    /**
     * 根据活动和id获取商品关联数量
     */
    long getCount(Long flashPromotionId, Long flashPromotionSessionId);

    /**
     * 修改关联信息
     */
    int update(Long id, SmsFlashPromotionProductRelation relation);


}
