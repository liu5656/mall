package com.macro.mall.admin.dao;

import com.macro.mall.model.PmsProductLadder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会员阶梯价格自定义Dao
 * @version 1.0
 * @Author lj
 * @date 2021/10/12 9:36 上午
 */
public interface PmsProductLadderDao {

    /**
     * 批量创建
     */
    int insertList(@Param("list") List<PmsProductLadder> ladders);

}
