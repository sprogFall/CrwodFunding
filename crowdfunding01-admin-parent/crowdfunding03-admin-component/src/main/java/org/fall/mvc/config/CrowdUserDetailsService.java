package org.fall.mvc.config;

import crowd.entity.Admin;
import crowd.entity.Role;
import org.fall.service.api.AdminService;
import org.fall.service.api.AuthService;
import org.fall.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CrowdUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 通过用户名得到Admin对象
        Admin admin = adminService.getAdminByLoginAcct(username);

        // 通过AdminId得到角色List
        List<Role> roles = roleService.queryAssignedRoleList(admin.getId());

        // 通过AdminId得到权限name地List
        List<String> authNameList = authService.getAuthNameByAdminId(admin.getId());

        // 创建List用来存放GrantedAuthority（权限信息）
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 向List存放角色信息，注意角色必须要手动加上 “ROLE_” 前缀
        for (Role role : roles){
            String roleName = "ROLE_" + role.getName();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
            authorities.add(simpleGrantedAuthority);
        }

        // 向List存放权限信息
        for (String authName : authNameList){
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authName);
            authorities.add(simpleGrantedAuthority);
        }

        // 将Admin对象、权限信息封装入SecurityAdmin对象（User的子类）
        SecurityAdmin securityAdmin = new SecurityAdmin(admin,authorities);

        // 返回SecurityAdmin对象
        return securityAdmin;
    }
}
