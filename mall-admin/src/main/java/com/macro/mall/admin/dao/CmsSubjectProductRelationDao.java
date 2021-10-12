package com.macro.mall.admin.dao;

import com.macro.mall.model.CmsSubjectProductRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品和专题关系自定义Dao
 * @version 1.0
 * @Author lj
 * @date 2021/10/12 10:05 上午
 */
public interface CmsSubjectProductRelationDao {
    /**
     * 批量添加
     */
    int insertList(@Param("list")List<CmsSubjectProductRelation> relations);
}
