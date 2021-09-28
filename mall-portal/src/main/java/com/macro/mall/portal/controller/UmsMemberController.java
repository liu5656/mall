package com.macro.mall.portal.controller;

import com.macro.mall.portal.service.UmsMemberService;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.UmsMember;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/9/27 5:59 下午
 * @desc
 */
@Api(tags = "UmsMemberController", description = "会员注册登录管理")
@RequestMapping("/member")
@RestController
public class UmsMemberController {
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Autowired private UmsMemberService memberService;

    @ApiOperation("会员注册")
    @PostMapping("/register")
    public CommonResult register(@RequestParam String username,
                                 @RequestParam String password,
                                 @RequestParam String mobile,
                                 @RequestParam String authCode) {
        memberService.register(username, password, mobile, authCode);
        return CommonResult.success("注册成功");
    }

    @ApiOperation("会员登录")
    @PostMapping("/login")
    public CommonResult login(@RequestParam String username,
                              @RequestParam String password) {
        String token = memberService.login(username, password);
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码失败");
        }
        Map<String, String> map = new HashMap<>();
        map.put("tokenHead", tokenHead);
        map.put("token", token);
        return CommonResult.success(map);
    }

    @ApiOperation("获取会员信息")
    @GetMapping("/info")
    public CommonResult info(Principal principal) {
        if (principal == null) {
            return CommonResult.unauthorized(null);
        }
        UmsMember member = memberService.getCurrentMember();
        return CommonResult.success(member);
    }

    @ApiOperation("获取验证码")
    @GetMapping("/getAuthCode")
    public CommonResult getAuthCode(@RequestParam String mobile) {
        String code = memberService.generateAuthCode(mobile);
        return CommonResult.success("获取验证码成功");
    }

    @ApiOperation("修改密码")
    @PostMapping("/updatePassword")
    public CommonResult updatePassword(@RequestParam String mobile,
                                       @RequestParam String password,
                                       @RequestParam String authCode) {
        memberService.updatePassword(mobile, password, authCode);
        return CommonResult.success("修改密码成功");
    }

    @ApiOperation("刷新token")
    @PostMapping("/refreshToken")
    public CommonResult refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshed = memberService.refreshToken(token);
        return CommonResult.success(refreshed);
    }
}
