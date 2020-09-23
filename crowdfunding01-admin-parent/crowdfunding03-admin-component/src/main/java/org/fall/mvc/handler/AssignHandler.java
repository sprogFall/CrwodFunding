package org.fall.mvc.handler;

import crowd.entity.Auth;
import crowd.entity.Role;
import org.fall.service.api.AdminService;
import org.fall.service.api.AuthService;
import org.fall.service.api.RoleService;
import org.fall.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class AssignHandler {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;


    @RequestMapping("/assign/to/page.html")
    public String toAssignPage(@RequestParam("adminId") Integer adminId, ModelMap modelMap){

        // 得到对应当前adminId未被分配的角色（Role）List
        List<Role> UnAssignedRoleList = roleService.queryUnAssignedRoleList(adminId);

        // 得到对应当前adminId已被分配的角色（Role）List
        List<Role> AssignedRoleList = roleService.queryAssignedRoleList(adminId);

        // 将已选择的、未选择的放入modelMap
        modelMap.addAttribute("UnAssignedRoleList",UnAssignedRoleList);
        modelMap.addAttribute("AssignedRoleList",AssignedRoleList);

        // 请求转发到assign-role.jsp
        return "assign-role";
    }

    @RequestMapping("/assign/do/assign.html")
    public String saveAdminRoleRelationship(
            @RequestParam("adminId") Integer adminId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList
    ){

        adminService.saveAdminRoleRelationship(adminId, roleIdList);

        //重定向（减少数据库操作）返回信息页
        return "redirect:/admin/page/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    @ResponseBody
    @RequestMapping("/assign/get/tree.json")
    public ResultEntity<List<Auth>> getAuthTree(){
        List<Auth> authList = authService.queryAuthList();

        return ResultEntity.successWithData(authList);
    }


    // 获得被勾选的auth信息
    @ResponseBody
    @RequestMapping("/assign/get/checked/auth/id.json")
    public ResultEntity<List<Integer>> getAuthByRoleId(Integer roleId){
        List<Integer> authIdList = authService.getAuthByRoleId(roleId);
        return ResultEntity.successWithData(authIdList);
    }

    @ResponseBody
    @RequestMapping("/assign/do/save/role/auth/relationship.json")
    public ResultEntity<String> saveRoleAuthRelationship(
            @RequestBody Map<String,List<Integer>> map ) {

        authService.saveRoleAuthRelationship(map);
        return ResultEntity.successWithoutData();
    }

}
