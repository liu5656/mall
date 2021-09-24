package com.macro.mall.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.admin.dao.UmsRoleDao;
import com.macro.mall.admin.service.UmsAdminCacheService;
import com.macro.mall.admin.service.UmsRoleService;
import com.macro.mall.mapper.UmsRoleMapper;
import com.macro.mall.mapper.UmsRoleMenuRelationMapper;
import com.macro.mall.mapper.UmsRoleResourceRelationMapper;
import com.macro.mall.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 后台角色管理实现类
 * @version 1.0
 * @Author lj
 * @date 2021/9/22 2:28 下午
 * @desc
 */
@Service
public class UmsRoleServiceImpl implements UmsRoleService {

    @Autowired private UmsRoleMapper roleMapper;
    @Autowired private UmsRoleMenuRelationMapper roleMenuRelationMapper;
    @Autowired private UmsRoleResourceRelationMapper roleResourceRelationMapper;
    @Autowired private UmsRoleDao roleDao;
    @Autowired private UmsAdminCacheService cacheService;


    @Override
    public int create(UmsRole role) {
        role.setCreateTime(new Date());
        role.setAdminCount(0);
        role.setSort(0);
        return roleMapper.insert(role);
    }

    @Override
    public int delete(List<Long> ids) {
        UmsRoleExample example = new UmsRoleExample();
        example.createCriteria().andIdIn(ids);
        int count = roleMapper.deleteByExample(example);
        cacheService.delResourceListByRoleIds(ids);
        return count;
    }

    @Override
    public List<UmsRole> list() {
        return roleMapper.selectByExample(new UmsRoleExample());
    }

    @Override
    public List<UmsRole> list(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        UmsRoleExample example = new UmsRoleExample();
        if (StringUtils.isEmpty(keyword) == false) {
            example.createCriteria().andNameLike('%' + keyword + '%');
        }
        return roleMapper.selectByExample(example);
    }

    @Override
    public int update(Long id, UmsRole role) {
        role.setId(id);
        return roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public List<UmsMenu> getMenuList(Long adminId) {
        return roleDao.getMenuList(adminId);
    }

    @Override
    public List<UmsMenu> listMenu(Long roleId) {
        return roleDao.getMenuListByRoleId(roleId);
    }

    @Override
    public int allocMenu(Long roleId, List<Long> menuIds) {
        UmsRoleMenuRelationExample example = new UmsRoleMenuRelationExample();
        example.createCriteria().andIdEqualTo(roleId);
        roleMenuRelationMapper.deleteByExample(example);
        for (Long id : menuIds) {
            UmsRoleMenuRelation relation = new UmsRoleMenuRelation();
            relation.setRoleId(roleId);
            relation.setMenuId(id);
            roleMenuRelationMapper.insert(relation);
        }
        return menuIds.size();
    }

    @Override
    public int allocResource(Long roleId, List<Long> resourceIds) {
        UmsRoleResourceRelationExample example = new UmsRoleResourceRelationExample();
        example.createCriteria().andIdEqualTo(roleId);
        roleResourceRelationMapper.deleteByExample(example);
        for (Long id : resourceIds) {
            UmsRoleResourceRelation relation = new UmsRoleResourceRelation();
            relation.setRoleId(roleId);
            relation.setResourceId(id);
            roleResourceRelationMapper.insert(relation);
        }
        return resourceIds.size();
    }
}
