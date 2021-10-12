package com.macro.mall.admin.dao;

import com.macro.mall.model.PmsProductAttributeValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品参数/规格属性自定义Dao
 * @version 1.0
 * @Author lj
 * @date 2021/10/12 9:52 上午
 */
public interface PmsProductAttributeValueDao {
    /**
     * 批量添加
     */
    int insertList(@Param("list") List<PmsProductAttributeValue> values);
}
