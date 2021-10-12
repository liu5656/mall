package com.macro.mall.admin.dao;

import com.macro.mall.model.PmsProductFullReduction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品满减自定义Dao
 * @version 1.0
 * @Author lj
 * @date 2021/10/12 9:43 上午
 */
public interface PmsProductFullReductionDao {

    /**
     * 批量创建
     */
    int insertList(@Param("list") List<PmsProductFullReduction> reductions);
}
