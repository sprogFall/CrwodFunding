package org.fall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import crowd.entity.Role;
import crowd.entity.RoleExample;
import org.fall.mapper.RoleMapper;
import org.fall.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    // 获取分页的用户列表
    @Override
    public PageInfo<Role> getPageInfo(int pageNum, int pageSize, String keyword) {
        // 开启分页
        PageHelper.startPage(pageNum,pageSize);

        // 从mapper方法得到Role的List
        List<Role> roles = roleMapper.selectRoleByKeyword(keyword);

        // 封装为PageInfo对象
        PageInfo<Role> pageInfo = new PageInfo<Role>(roles);

        // 返回pageInfo
        return pageInfo;
    }

    @Override
    public void saveRole(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void removeById(List<Integer> roleIdList) {
        // 创建RoleExample
        RoleExample roleExample = new RoleExample();

        // 获取Criteria对象
        RoleExample.Criteria criteria = roleExample.createCriteria();

        // 使用Criteria封装查询条件
        criteria.andIdIn(roleIdList);

        roleMapper.deleteByExample(roleExample);
    }

    @Override
    public List<Role> queryUnAssignedRoleList(Integer adminId) {
        return roleMapper.queryUnAssignedRoleList(adminId);
    }

    @Override
    public List<Role> queryAssignedRoleList(Integer adminId) {
        return roleMapper.queryAssignedRoleList(adminId);
    }
}
