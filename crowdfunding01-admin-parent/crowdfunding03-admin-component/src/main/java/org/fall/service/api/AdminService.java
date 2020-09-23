package org.fall.service.api;

import com.github.pagehelper.PageInfo;
import crowd.entity.Admin;


import java.util.List;

public interface AdminService {

    void saveAdmin(Admin admin);

    Admin queryAdmin(Integer id);

    List<Admin> getAll();

    Admin getAdminByUsername(String adminLoginAcct, String adminPassword);

    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    void removeById(Integer adminId);

    void updateAdmin(Admin admin);

    void saveAdminRoleRelationship(Integer adminId, List<Integer> roleList);

    Admin getAdminByLoginAcct(String loginAcct);
}
