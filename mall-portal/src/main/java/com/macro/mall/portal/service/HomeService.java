package com.macro.mall.portal.service;

import com.macro.mall.model.CmsSubject;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.model.PmsProductCategory;
import com.macro.mall.portal.domain.HomeContentResult;

import java.util.List;

/**
 * 首页内容管理Service
 * @version 1.0
 * @Author lj
 * @date 2021/10/21 11:01 上午
 */
public interface HomeService {

    /**
     * 获取首页内容
     */
    HomeContentResult content();

    /**
     * 首页商品推荐
     */
    List<PmsProduct> recommendProductList(Integer pageNum, Integer pageSize);

    /**
     * 获取商品分类
     * @param parentId  0获取一级分类，其他获取二级分类
     * @return
     */
    List<PmsProductCategory> getProductCategoryList(Long parentId);

    /**
     * 根据专题分类id分页获取专题列表
     * @param cateId 专题分类id
     * @return
     */
    List<CmsSubject> getSubjectList(Long cateId, Integer pageNum, Integer pageSize);

    /**
     * 获取热卖商品
     */
    List<PmsProduct> hotProductList(Integer pageNum, Integer pageSize);

    /**
     * 首页新品推荐
     */
    List<PmsProduct> newProductList(Integer pageNum, Integer pageSize);

}
