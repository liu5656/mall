package com.macro.mall.admin.dto;

import com.macro.mall.model.UmsMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 后台菜单节点封装
 * @version 1.0
 * @Author lj
 * @date 2021/9/26 5:46 下午
 * @desc
 */

@Data
public class UmsMenuNode extends UmsMenu {
    @ApiModelProperty(value = "子级菜单")
    private List<UmsMenuNode> children;
}
