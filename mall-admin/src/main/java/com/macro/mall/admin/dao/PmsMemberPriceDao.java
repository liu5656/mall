package com.macro.mall.admin.dao;

import com.macro.mall.model.PmsMemberPrice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会员价格管理自定义Dao
 * @version 1.0
 * @Author lj
 * @date 2021/10/12 9:25 上午
 */
public interface PmsMemberPriceDao {
    /**
     * 批量创建
     */
    int insertList(@Param("list")List<PmsMemberPrice> prices);
}
