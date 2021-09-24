package com.macro.mall.admin.service;

import com.macro.mall.model.UmsResource;

import java.util.List;

/**
 * 后台资源管理Service
 * @version 1.0
 * @Author lj
 * @date 2021/9/22 11:11 上午
 */
public interface UmsResourceService {

    /**
     * 添加资源
     */
    int create(UmsResource umsResource);

    /**
     * 修改资源
     */
    int update(Long id, UmsResource umsResource);

    /**
     * 获取资源详情
     */
    UmsResource getItem(Long id);

    /**
     * 删除资源
     */
    int delete(Long id);

    /**
     * 分页查询资源
     */
    List<UmsResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum);

    /**
     * 获取全部资源
     */
    List<UmsResource> listAll();

}
