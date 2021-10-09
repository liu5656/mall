package com.macro.mall.admin.controller;

import com.macro.mall.admin.dto.UmsAdminLoginParam;
import com.macro.mall.admin.dto.UmsAdminParam;
import com.macro.mall.admin.dto.UmsUpdateAdminPasswordParam;
import com.macro.mall.admin.service.UmsAdminService;
import com.macro.mall.admin.service.UmsRoleService;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.common.api.ResultCode;
import com.macro.mall.model.UmsAdmin;
import com.macro.mall.model.UmsRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/9/22 2:02 下午
 * @desc
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "UmsAdminController", description = "后台用户管理")
public class UmsAdminController {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired private UmsAdminService adminService;
    @Autowired private UmsRoleService roleService;

    @ApiOperation("后台管理用户注册")
    @PostMapping("/register")
    public CommonResult<UmsAdmin> register(@Validated @RequestBody UmsAdminParam umsAdminParam) {
        UmsAdmin admin = adminService.register(umsAdminParam);
        if (admin != null) {
            return CommonResult.success(admin);
        }
        return CommonResult.failed(ResultCode.FAILED);
    }

    @ApiOperation("登录获取token")
    @PostMapping("/login")
    public CommonResult login(@Validated @RequestBody UmsAdminLoginParam loginParam) {
        String token = adminService.login(loginParam.getUsername(), loginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("账号或密码错误");
        }
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("tokenHead", tokenHead);
        return CommonResult.success(map);
    }

    @ApiOperation("刷新token")
    @PostMapping("/refreshToken")
    public CommonResult refreshToken(HttpServletRequest req) {
        String old = req.getHeader(tokenHeader);
        String refreshed = adminService.refreshToken(old);
        if (refreshed == null) {
            return CommonResult.failed("token验证失败");
        }
        Map<String, String> map = new HashMap<>();
        map.put("token", refreshed);
        map.put("tokenHead", tokenHead);
        return CommonResult.success(map);
    }

    @ApiOperation("获取登录用户信息")
    @GetMapping("/info")
    public CommonResult getAdminInfo(Principal principal) {
        if (principal == null) {
            return CommonResult.unauthorized();
        }
        String username = principal.getName();
        UmsAdmin admin = adminService.getAdminByUserName(username);

        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("menus", roleService.getMenuList(admin.getId()));
        map.put("icon", admin.getIcon());
        List<UmsRole> list = adminService.getRoleList(admin.getId());
        if (list.size() > 0) {
            List<String> roles = list.stream().map(UmsRole::getName).collect(Collectors.toList());
            map.put("roles", roles);
        }
        return CommonResult.success(map);
    }

    @ApiOperation("退出功能")
    @PostMapping("/logout")
    public CommonResult logout() {
        return CommonResult.success(null);
    }

    @ApiOperation("根据用户名获取用户列表")
    @GetMapping("/list")
    public CommonResult<CommonPage<UmsAdmin>> list(@RequestParam(required = false, value = "keyword") String keyword,
                                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<UmsAdmin> admins = adminService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(admins));
    }

    @ApiOperation("获取指定用户信息")
    @GetMapping("/{id}")
    public CommonResult<UmsAdmin> getItem(@PathVariable("id") Long id) {
        UmsAdmin admin = adminService.getItem(id);
        return CommonResult.success(admin);
    }

    @ApiOperation("修改指定用户信息")
    @PutMapping("/update/{uid}")
    public CommonResult updateAdmin(@PathVariable Long id, @RequestBody UmsAdmin admin) {
        int count = adminService.update(id, admin);
        if (count > 0) {
            return CommonResult.success(null);
        }
        return CommonResult.failed("更新失败");
    }

    @ApiOperation("修改指定用户密码")
    @PutMapping("/updatePassword")
    public CommonResult updatePassword(@Validated @RequestBody UmsUpdateAdminPasswordParam param) {
        int result = adminService.updatePassword(param);
        if (result > 0) {
            return CommonResult.success(null);
        }else if (-1 == result) {
            return CommonResult.failed("参数不合法");
        }else if (-2 == result) {
            return CommonResult.failed("找不到该用户");
        }else if (-3 == result) {
            return CommonResult.failed("旧密码错误");
        }else {
            return CommonResult.failed(ResultCode.FAILED);
        }
    }

    @ApiOperation("删除指定用户")
    @DeleteMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        int count = adminService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改账户状态")
    @PostMapping("/updateStatus/{id}")
    public CommonResult updateStatus(@PathVariable Long id, @RequestParam("status") Integer status) {
        UmsAdmin admin = new UmsAdmin();
        admin.setStatus(status);
        int count = adminService.update(id, admin);
        if (count > 0 ) {
            return CommonResult.success(null);
        }
        return CommonResult.failed("更新失败");
    }

    @ApiOperation("给用户分配角色")
    @PostMapping("/role/update")
    public CommonResult updateRole(@RequestParam Long adminId, @RequestParam List<Long> roleIds) {
        int count = adminService.updateRole(adminId, roleIds);
        if (count > 0) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取指定用户角色")
    @GetMapping("/role/{adminId}")
    public CommonResult<List<UmsRole>> getRoleList(@PathVariable Long adminId) {
        List<UmsRole> roles = adminService.getRoleList(adminId);
        return CommonResult.success(roles);
    }




}
