package com.macro.mall.admin.service;

import com.macro.mall.model.SmsHomeNewProduct;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 首页新品推荐管理Service
 * @version 1.0
 * @Author lj
 * @date 2021/10/14 1:47 下午
 */
public interface SmsHomeNewProductService {

    /**
     * 增加首页新品推荐
     */
    @Transactional
    int create(List<SmsHomeNewProduct> products);

    /**
     * 批量删除
     */
    int delete(List<Long> ids);

    /**
     * 分页查询
     */
    List<SmsHomeNewProduct> list(String name, Integer recommendStatus, Integer pageNum, Integer pageSize);

    /**
     * 编辑排序
     */
    int updateSort(Long id, Integer sort);
    /**
     * 批量更新推荐标志
     */
    int updateRecommendStatus(List<Long> ids, Integer status);

}
