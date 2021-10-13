package com.macro.mall.admin.service;

import com.macro.mall.model.SmsFlashPromotion;

import java.util.List;

/**
 * 限时购活动管理service
 * @version 1.0
 * @Author lj
 * @date 2021/10/13 3:31 下午
 */
public interface SmsFlashPromotionService {

    /**
     * 添加活动
     */
    int create(SmsFlashPromotion promotion);

    /**
     * 删除活动
     */
    int delete(Long id);

    /**
     * 获取详细信息
     */
    SmsFlashPromotion getItem(Long id);
    /**
     * 分页查询活动列表
     */
    List<SmsFlashPromotion> list(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 修改指定活动
     */
    int update(Long id, SmsFlashPromotion promotion);
    /**
     * 修改活动上下线标志
     */
    int updateStatus(Long id, Integer status);
}
