package com.macro.mall.admin.dao;

import com.macro.mall.admin.dto.PmsProductResult;
import org.apache.ibatis.annotations.Param;

/**
 * 商品管理自定义Dao
 * @version 1.0
 * @Author lj
 * @date 2021/10/12 10:18 上午
 */
public interface PmsProductDao {
    /**
     * 获取商品编辑信息
     */
    PmsProductResult getUpdateInfo(@Param("id") Long id);
}
