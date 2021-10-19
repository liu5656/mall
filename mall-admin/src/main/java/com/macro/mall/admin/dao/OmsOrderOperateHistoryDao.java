package com.macro.mall.admin.dao;

import com.macro.mall.model.OmsOrderOperateHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单操作记录自定义Dao
 * @version 1.0
 * @Author lj
 * @date 2021/10/18 10:37 上午
 */
public interface OmsOrderOperateHistoryDao {
    /**
     * 批量创建
     */
    int insertList(@Param("List")List<OmsOrderOperateHistory> operateHistories);
}
