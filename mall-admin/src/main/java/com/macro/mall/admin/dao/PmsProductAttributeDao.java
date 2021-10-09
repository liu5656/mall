package com.macro.mall.admin.dao;


import com.macro.mall.admin.dto.PmsProductAttrInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品属性管理自定义Dao
 * @version 1.0
 * @Author lj
 * @date 2021/10/9 2:01 下午
 */
public interface PmsProductAttributeDao {
    /**
     * 获取商品属性信息
     */
    List<PmsProductAttrInfo> getProductAttrInfo(@Param("id") Long productCategoryId);
}
