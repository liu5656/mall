package com.macro.mall.portal.controller;

import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.OmsCartItem;
import com.macro.mall.model.SmsCoupon;
import com.macro.mall.model.SmsCouponHistory;
import com.macro.mall.model.UmsMember;
import com.macro.mall.portal.domain.CartPromotionItem;
import com.macro.mall.portal.domain.SmsCouponHistoryDetail;
import com.macro.mall.portal.service.OmsCartItemService;
import com.macro.mall.portal.service.UmsMemberCouponService;
import com.macro.mall.portal.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * 用户优惠券管理Controller
 * @version 1.0
 * @Author lj
 * @date 2021/9/30 3:56 下午
 * @desc
 */
@Api(tags = "UmsMemberCouponController", description = "优惠券管理控制器")
@RequestMapping("/member/coupon")
@RestController
public class UmsMemberCouponController {
    @Autowired private UmsMemberCouponService memberCouponService;
    @Autowired private UmsMemberService memberService;
    @Autowired private OmsCartItemService cartItemService;

    @ApiOperation("领取指定优惠券")
    @PostMapping("/add/{couponId}")
    public CommonResult add(@PathVariable Long couponId) {
        memberCouponService.add(couponId);
        return CommonResult.success("领取成功");
    }

    @ApiOperation("获取用户优惠券历史列表")
    @ApiImplicitParam(name = "useStatus",
            value = "优惠券筛选类型:0->未使用；1->已使用；2->已过期",
            allowableValues = "0,1,2",
            paramType = "query",
            dataType = "integer")
    @GetMapping("/listHistory")
    public CommonResult<List<SmsCouponHistory>> listHistory(@RequestParam(value = "useStatus", required = true) Integer useStatus) {
        List<SmsCouponHistory> couponHistories = memberCouponService.listHistory(useStatus);
        return CommonResult.success(couponHistories);
    }

    @ApiOperation("获取用户优惠券列表")
    @ApiImplicitParam(name = "useStatus",
            value = "优惠券筛选类型:0->未使用；1->已使用；2->已过期",
            allowableValues = "0,1,2",
            paramType = "query",
            dataType = "integer")
    @GetMapping("/list")
    public CommonResult<List<SmsCoupon>> list(@RequestParam(value = "useStatus", required = true) Integer useStatus) {
        List<SmsCoupon> coupons = memberCouponService.list(useStatus);
        return CommonResult.success(coupons);
    }

    @ApiOperation("当前会员购物车商品的优惠券")
//    @ApiImplicitParam(name = "xyz",
//            value = "使用可用:0->不可用；1->可用",
//            allowableValues = "0,1",
//            defaultValue = "1",
//            paramType = "query",
//            dataType = "Long")
    @GetMapping("/list/cart/{type}")
    public CommonResult<List<SmsCouponHistoryDetail>> listCart(@PathVariable("type") Integer type) {
        UmsMember member = memberService.getCurrentMember();
        List<CartPromotionItem> items = cartItemService.listPromotion(member.getId(), null);
        List<SmsCouponHistoryDetail> details = memberCouponService.listCart(items, type);
        return CommonResult.success(details);
    }

    @ApiOperation("获取指定商品优惠券")
    @GetMapping("/listByProduct/{productId}")
    public CommonResult<List<SmsCoupon>> listByProduct(@PathVariable("productId") Long id) {
        List<SmsCoupon> coupons = memberCouponService.listByProduct(id);
        return CommonResult.success(coupons);
    }

}
