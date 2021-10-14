package com.macro.mall.admin.service;

import com.macro.mall.model.SmsHomeRecommendProduct;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 首页推荐商品管理Service
 * @version 1.0
 * @Author lj
 * @date 2021/10/14 2:40 下午
 */
public interface SmsHomeRecommendProductService {

    /**
     * 增加首页推荐
     */
    @Transactional
    int create(List<SmsHomeRecommendProduct> products);

    /**
     * 批量删除首页推荐
     */
    int delete(List<Long> ids);

    /**
     * 分页获取
     */
    List<SmsHomeRecommendProduct> list(String productName, Integer recommendStatus, Integer pageNum, Integer pageSize);

    /**
     * 编辑推荐排序
     */
    int updateSort(Long id, Integer sort);
    /**
     * 编辑推荐标志
     */
    int updateRecommendstatus(List<Long> ids, Integer recommendStatus);



}
