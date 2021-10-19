package com.macro.mall.admin.dao;

import com.macro.mall.admin.dto.OmsReturnApplyQueryParam;
import com.macro.mall.admin.dto.OmsReturnApplyResult;
import com.macro.mall.model.OmsOrderReturnApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单退货申请管理自定义Dao
 * @version 1.0
 * @Author lj
 * @date 2021/10/19 10:04 上午
 */
public interface OmsOrderReturnApplyDao {
    /**
     * 查询申请列表
     */
    List<OmsOrderReturnApply> getList(@Param("param") OmsReturnApplyQueryParam param);

    /**
     * 获取申请详情
     */
    OmsReturnApplyResult getDetail(@Param("id") Long id);
}
