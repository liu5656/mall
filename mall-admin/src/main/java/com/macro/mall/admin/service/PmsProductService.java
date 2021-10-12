package com.macro.mall.admin.service;

import com.macro.mall.admin.dto.PmsProductParam;
import com.macro.mall.admin.dto.PmsProductQueryParam;
import com.macro.mall.admin.dto.PmsProductResult;
import com.macro.mall.model.PmsProduct;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品管理Service
 * @version 1.0
 * @Author lj
 * @date 2021/10/11 4:15 下午
 */
public interface PmsProductService {
    /**
     * 创建商品
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    int create(PmsProductParam param);

    /**
     * 分页查询商品
     */
    List<PmsProduct> list(PmsProductQueryParam param, Integer pageNum, Integer pageSize);
    /**
     * 根据商品名称或货号模糊查询
     */
    List<PmsProduct> list(String keyword);

    /**
     * 根据商品编号获取更新信息
     */
    PmsProductResult getUpdateInfo(Long id);
    /**
     * 更新商品
     */
    @Transactional
    int update(Long id, PmsProductParam param);
    /**
     * 批量修改商品审核状态
     * @param ids
     * @param status 审核状态
     * @param detail 审核详情
     * @return
     */
    @Transactional
    int updateVerifyStatus(List<Long> ids, Integer status, String detail);
    /**
     * 批量修改商品上架状态
     */
    int updatePublishStatus(List<Long> ids, Integer status);
    /**
     * 批量修改商品推荐状态
     */
    int updateRecommendStatus(List<Long> ids, Integer status);
    /**
     * 批量修改新品状态
     */
    int updateNewStatus(List<Long> ids, Integer status);
    /**
     * 批量更新商品删除标志
     */
    int updateDelete(List<Long> ids, Integer status);

}
