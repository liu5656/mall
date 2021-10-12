package com.macro.mall.admin.dao;

import com.macro.mall.model.PmsProductVertifyRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品审核日志管理自定义Dao
 * @version 1.0
 * @Author lj
 * @date 2021/10/12 11:30 上午
 */
public interface PmsProductVerifyRecordDao {

    /**
     * 批量添加
     */
    int insertList(@Param("list")List<PmsProductVertifyRecord> records);
}
