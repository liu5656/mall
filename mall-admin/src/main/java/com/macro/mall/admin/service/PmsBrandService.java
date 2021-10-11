package com.macro.mall.admin.service;

import com.macro.mall.admin.dto.PmsBrandParam;
import com.macro.mall.model.PmsBrand;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品品牌管理Service
 * @version 1.0
 * @Author lj
 * @date 2021/10/11 4:53 下午
 */
public interface PmsBrandService {

    /**
     * 创建品牌
     */
    int create(PmsBrandParam param);

    /**
     * 删除品牌
     */
    int delete(Long id);
    /**
     * 批量删除品牌
     */
    int deleteList(List<Long> ids);

    /**
     * 获取品牌详情
     */
    PmsBrand getItem(Long id);
    /**
     * 获取所有品牌
     */
    List<PmsBrand> getAll();
    /**
     * 分页查询品牌
     */
    List<PmsBrand> getList(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 修改品牌
     */
    @Transactional
    int update(Long id, PmsBrandParam param);
    /**
     * 修改显示状态
     */
    int updateShowStatus(List<Long> ids, Integer status);
    /**
     * 修改工厂状态
     */
    int updateFactoryStatus(List<Long> ids, Integer status);





}
