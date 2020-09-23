package org.fall.service.api;

import com.github.pagehelper.PageInfo;
import crowd.entity.Role;

import java.util.List;


public interface RoleService {

    PageInfo<Role> getPageInfo(int pageNum, int pageSize, String keyword);


    void saveRole(Role role);

    void updateRole(Role role);

    void removeById(List<Integer> roleIdList);

    // 根据adminId查询已分配的角色
    List<Role> queryUnAssignedRoleList(Integer adminId);

    List<Role> queryAssignedRoleList(Integer adminId);
}
