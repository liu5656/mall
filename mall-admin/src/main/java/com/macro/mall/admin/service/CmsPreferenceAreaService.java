package com.macro.mall.admin.service;

import com.macro.mall.model.CmsPrefrenceArea;

import java.util.List;

/**
 * 商品优选管理Service
 * @version 1.0
 * @Author lj
 * @date 2021/10/13 9:54 上午
 */
public interface CmsPreferenceAreaService {
    /**
     * 获取所有优选专区
     */
    List<CmsPrefrenceArea> listAll();
}
