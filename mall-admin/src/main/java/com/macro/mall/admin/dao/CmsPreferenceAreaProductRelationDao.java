package com.macro.mall.admin.dao;

import com.macro.mall.model.CmsPrefrenceAreaProductRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 优选和商品关系自定义Dao
 * @version 1.0
 * @Author lj
 * @date 2021/10/12 10:13 上午
 */
public interface CmsPreferenceAreaProductRelationDao {
    /**
     * 批量添加
     */
    int insertList(@Param("list")List<CmsPrefrenceAreaProductRelation> relations);
}
