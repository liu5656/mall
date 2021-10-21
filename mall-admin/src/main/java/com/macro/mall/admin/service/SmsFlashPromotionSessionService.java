package com.macro.mall.admin.service;

import com.macro.mall.admin.dto.SmsFlashPromotionSessionDetail;
import com.macro.mall.model.SmsFlashPromotionSession;

import java.util.List;

/**
 * 限时购场次管理Service
 * @version 1.0
 * @Author lj
 * @date 2021/10/19 4:57 下午
 */
public interface SmsFlashPromotionSessionService {

    /**
     * 添加场次
     */
    int create(SmsFlashPromotionSession session);

    /**
     * 删除场次
     */
    int delete(Long id);

    /**
     * 修改场次启用状态
     */
    int updateStatus(Long id, Integer status);
    /**
     * 修改场次
     */
    int update(Long id, SmsFlashPromotionSession session);

    /**
     * 获取详情
     */
    SmsFlashPromotionSession getItem(Long id);
    /**
     * 根据限时购id获取全部场次及其数量
     */
    List<SmsFlashPromotionSessionDetail> list(Long flashPromotionId);
    /**
     * 获取全部场次
     */
    List<SmsFlashPromotionSession> listall();


}
