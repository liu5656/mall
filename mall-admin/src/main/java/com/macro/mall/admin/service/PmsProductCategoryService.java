package com.macro.mall.admin.service;

import com.macro.mall.admin.dto.PmsProductCategoryParam;
import com.macro.mall.admin.dto.PmsProductCategoryWithChildrenItem;
import com.macro.mall.model.PmsProductCategory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品分类管理service
 * @version 1.0
 * @Author lj
 * @date 2021/10/11 11:33 上午
 */
public interface PmsProductCategoryService {
    /**
     * 创建商品分类
     */
    @Transactional
    int create(PmsProductCategoryParam param);

    /**
     * 删除商品分类
     */
    int delete(Long id);

    /**
     * 根据商品id获取分类
     */
    PmsProductCategory getItem(Long id);

    /**
     * 分页获取商品分类
     */
    List<PmsProductCategory> getList(Long parentId, Integer pageNum, Integer pageSize);
    /**
     * 以层级形式获取商品分类
     */
    List<PmsProductCategoryWithChildrenItem> listWithChildren();

    /**
     * 修改商品分类
     */
    int update(Long id, PmsProductCategoryParam param);
    /**
     * 批量修稿导航状态
     */
    int updateNavStatus(List<Long> ids, Integer status);
    /**
     * 批量修改显示状态
     */
    int updateShowStatus(List<Long> ids, Integer status);

}
