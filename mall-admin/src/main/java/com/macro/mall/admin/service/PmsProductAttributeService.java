package com.macro.mall.admin.service;

import com.macro.mall.model.PmsProductAttribute;

import java.util.List;

/**
 * 商品属性管理service
 * @version 1.0
 * @Author lj
 * @date 2021/10/9 11:26 上午
 */
public interface PmsProductAttributeService {
    /**
     * 根据分类分页获取商品属性
     * @param cid   分类id
     * @param type  0-规格，1-参数
     * @return
     */
    List<PmsProductAttribute> getList(Long cid, Integer type, Integer pageSize, Integer pageNum);

    /**
     * 添加商品属性
     */
    int create(PmsProductAttributeParam param);
}
