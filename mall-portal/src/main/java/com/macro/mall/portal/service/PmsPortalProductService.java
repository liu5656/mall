package com.macro.mall.portal.service;

import com.macro.mall.model.PmsProduct;
import com.macro.mall.portal.domain.PmsPortalProductDetail;
import com.macro.mall.portal.domain.PmsProductCategoryNode;

import java.util.List;

/**
 * 前端商品管理Service
 * @version 1.0
 * @Author lj
 * @date 2021/10/21 4:34 下午
 */
public interface PmsPortalProductService {

    /**
     * 综合搜索商品
     */
    List<PmsProduct> search(String keyword, Long brandId, Long productCategoryId, Integer pageNum, Integer pageSize, Integer sort);

    /**
     * 树形结构展示所有商品分类
     */
    List<PmsProductCategoryNode> categoryTreeList();

    /**
     * 获取前端商品详情
     */
    PmsPortalProductDetail detail(Long id);
}
