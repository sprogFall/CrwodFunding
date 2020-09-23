package org.fall.mvc.handler;

import com.github.pagehelper.PageInfo;
import crowd.entity.Admin;
import org.fall.constant.CrowdConstant;
import org.fall.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class AdminHandler {

    @Autowired
    AdminService adminService;

    //登录操作的handler
    @RequestMapping("/admin/login/doLogin.html")
    public String doLogin(
            @RequestParam("login-user") String username,
            @RequestParam("login-pwd") String password,
            HttpSession session) {
        //通过service层方法得到Admin对象
        Admin admin = adminService.getAdminByUsername(username,password);

        //将Admin对象放入Session域
        session.setAttribute(CrowdConstant.LOGIN_ADMIN_NAME, admin);

        //重定向到登录完成后的主页面（重定向防止重复提交表单，增加不必要的数据库访问）
        return "redirect:/admin/main/page.html";
    }

    //退出登录的handler
    @RequestMapping("/admin/login/logout.html")
    public String doLogout(HttpSession session){
        //传入session对象,通过invalidate将当前session设置为失效（相当于清除登录信息）
        session.invalidate();
        return "redirect:/admin/login/page.html";
    }

    //显示admin的数据
    @RequestMapping("/admin/page/page.html")
    public String getAdminPage(
            // 传入的关键字，若未传入，默认值为一个空字符串（不是null）
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            // 传入的页码，默认值为1
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            // 传入的页面大小，默认值为5
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            ModelMap modelMap) {
        //从AdminService中得到对应传参的列表
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
        //将得到的PageInfo存入modelMap，传给前端
        modelMap.addAttribute(CrowdConstant.NAME_PAGE_INFO,pageInfo);
        //进入对应的显示管理员信息的页面（/WEB-INF/admin-page.jsp）
        return "admin-page";
    }


    @RequestMapping("/admin/page/remove/{adminId}/{pageNum}/{keyword}.html")
    public String removeAdmin(
            // 从前端获取的管理员id
            @PathVariable("adminId") Integer adminId,
            // 从前端获取的当前页码与关键字（为了删除后跳转的页面仍然是刚才的页面，优化体验）
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("keyword") String keyword){

        // 调用service层方法，从数据库根据id删除管理员
        adminService.removeById(adminId);

        //重定向（减少数据库操作）返回信息页
        return "redirect:/admin/page/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    @PreAuthorize("hasAuthority('user:save')")
    @RequestMapping("/admin/page/doSave.html")
    public String addAdmin(Admin admin){
        // 调用service层存储admin对象的方法
        adminService.saveAdmin(admin);

        // 重定向会原本的页面，且为了能在添加管理员后看到管理员，设置pageNum为整型的最大值（通过修正到最后一页）
        return "redirect:/admin/page/page.html?pageNum="+Integer.MAX_VALUE;
    }


    @RequestMapping("/admin/page/update/{adminId}/{pageNum}/{keyword}.html")
    public String toUpdatePage(
            @PathVariable("adminId") Integer adminId,
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("keyword") String keyword,
            ModelMap modelMap){
        // 调用Service方法，通过id查询admin对象
        Admin admin = adminService.queryAdmin(adminId);

        // 将admin对象、页码、关键字存入modelMap，传到前端页面
        modelMap.addAttribute("admin", admin);
        modelMap.addAttribute("pageNum", pageNum);
        modelMap.addAttribute("keyword", keyword);

        // 跳转到更新页面WEB-INF/admin-update.jsp
        return "admin-update";
    }

    @RequestMapping("/admin/page/doUpdate.html")
    public String updateAdmin(Admin admin,@RequestParam("pageNum") Integer pageNum,@RequestParam("keyword") String keyword){
        adminService.updateAdmin(admin);
        return "redirect:/admin/page/page.html?pageNum="+pageNum + "&keyword="+keyword;
    }

}
