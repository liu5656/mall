package com.macro.mall.portal.service;

import com.macro.mall.model.UmsMemberReceiveAddress;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户地址管理Service
 * @version 1.0
 * @Author lj
 * @date 2021/9/28 3:54 下午
 */
public interface UmsReceiveAddressService {
    /**
     * 添加收货地址
     */
    int add(UmsMemberReceiveAddress address);

    /**
     * 删除收货地址
     */
    int delete(Long id);

    /**
     * 修改收货地址
     */
    @Transactional
    int update(Long id, UmsMemberReceiveAddress address);

    /**
     * 获取用户的收货地址
     */
    List<UmsMemberReceiveAddress> list();

    /**
     * 获取地址详情
     */
    UmsMemberReceiveAddress getItem(Long id);
}
