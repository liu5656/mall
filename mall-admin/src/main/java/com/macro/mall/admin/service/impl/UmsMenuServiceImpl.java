package com.macro.mall.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.admin.dto.UmsMenuNode;
import com.macro.mall.admin.service.UmsMenuService;
import com.macro.mall.mapper.UmsMenuMapper;
import com.macro.mall.model.UmsMenu;
import com.macro.mall.model.UmsMenuExample;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台菜单管理Service实现类
 * @version 1.0
 * @Author lj
 * @date 2021/9/26 5:49 下午
 * @desc
 */
@Service
public class UmsMenuServiceImpl implements UmsMenuService {

    @Autowired private UmsMenuMapper menuMapper;

    @Override
    public int create(UmsMenu menu) {
        menu.setCreateTime(new Date());
        updateLevel(menu);
        return menuMapper.insert(menu);
    }

    @Override
    public int update(Long id, UmsMenu menu) {
        menu.setId(id);
        updateLevel(menu);
        return menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public UmsMenu getItem(Long id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(Long id) {
        return menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<UmsMenu> list(Long parentId, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        UmsMenuExample example = new UmsMenuExample();
        example.setOrderByClause("sort desc");
        example.createCriteria().andParentIdEqualTo(parentId);
        return menuMapper.selectByExample(example);
    }

    @Override
    public List<UmsMenuNode> treeList() {
        List<UmsMenu> all = menuMapper.selectByExample(new UmsMenuExample());

//        List<UmsMenu> temp = all.stream().filter(menu -> menu.getParentId().equals(0L)).collect(Collectors.toList());
//
//        return temp.stream().map( parent -> foundChildrens(parent, all)).collect(Collectors.toList());

        List<UmsMenuNode> nodes = all.stream().filter(menu -> menu.getParentId().equals(0L))
                .map( parent -> foundChildrens(parent, all))
                .collect(Collectors.toList());
        return nodes;
    }

    @Override
    public int updateHidden(Long id, Integer hidden) {
        UmsMenu menu = new UmsMenu();
        menu.setId(id);
        menu.setHidden(hidden);
        return menuMapper.updateByPrimaryKeySelective(menu);
    }

    /**
     * 修改菜单层级
     */
    private void updateLevel(UmsMenu menu) {
        if (0 == menu.getParentId()) {
            menu.setLevel(0);
        }else {
            UmsMenu parent = menuMapper.selectByPrimaryKey(menu.getParentId());
            if (parent != null) {
                menu.setLevel(parent.getLevel() + 1);
            }else{
                menu.setLevel(0);
            }
        }
    }

    /**
     * 递归查询菜单对应子级
     */
    private UmsMenuNode foundChildrens(UmsMenu parent, List<UmsMenu> crowd) {
        UmsMenuNode node = new UmsMenuNode();
        BeanUtils.copyProperties(parent, node);
        List<UmsMenuNode> childrens = crowd.stream()
                .filter( children -> children.getParentId().equals(node.getId()))
                .map( children -> foundChildrens(children, crowd))
                .collect(Collectors.toList());
        node.setChildren(childrens);
        return node;
    }
}
