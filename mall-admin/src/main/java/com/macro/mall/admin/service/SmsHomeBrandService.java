package com.macro.mall.admin.service;

import com.macro.mall.model.SmsHomeBrand;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 首页品牌管理service
 * @version 1.0
 * @Author lj
 * @date 2021/10/14 9:13 上午
 */
public interface SmsHomeBrandService {

    /**
     * 添加首页品牌
     */
    @Transactional
    int create(List<SmsHomeBrand> brands);

    /**
     * 删除首页品牌
     */
    int delete(List<Long> ids);

    /**
     * 修改品牌推荐排序
     */
    int update(Long id, Integer sort);

    /**
     * 批量更新推荐状态
     */
    int update(List<Long> ids, Integer status);

    /**
     * 分页查询品牌
     */
    List<SmsHomeBrand> list(String brandName, Integer recommendStatus, Integer pageNum, Integer pageSize);
}
