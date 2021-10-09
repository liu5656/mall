package com.macro.mall.admin.service;

import com.macro.mall.admin.dto.PmsProductAttrInfo;
import com.macro.mall.admin.dto.PmsProductAttributeParam;
import com.macro.mall.model.PmsProductAttribute;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    int create(PmsProductAttributeParam param);

    /**
     * 修改商品属性
     * @param id 属性id
     * @param param
     * @return
     */
    int update(Long id, PmsProductAttributeParam param);

    /**
     * 获取单个商品属性信息
     */
    PmsProductAttribute getItem(Long id);

    /**
     * 批量删除商品属性
     */
    @Transactional
    int delete(List<Long> ids);

    /**
     * 获取商品分类对应的属性列表
     */
    List<PmsProductAttrInfo> getProductAttributeInfo(Long categoryId);


}
