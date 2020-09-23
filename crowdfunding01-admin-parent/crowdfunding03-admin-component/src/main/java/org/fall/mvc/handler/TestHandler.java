package org.fall.mvc.handler;



import crowd.entity.Admin;
import crowd.entity.Student;
import org.fall.service.api.AdminService;
import org.fall.util.CrowdUtil;
import org.fall.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TestHandler {

    @Autowired
    AdminService adminService;

    private Logger logger = LoggerFactory.getLogger(TestHandler.class);

    @RequestMapping("/test/ssm.html")
    public String testSSM(Model model, HttpServletRequest request){
        //Admin admin = adminService.queryAdmin(1);
        List<Admin> admins = adminService.getAll();
        model.addAttribute("admins", admins);

        //System.out.println(1/0);
        logger.info(String.valueOf(CrowdUtil.judgeRequestType(request)));

        //测试空指针异常
        //String e = null;
        //int length = e.length();

        //测试数学异常
        System.out.println(1/0);


        return "target";
    }


    //通过@RequestParam接收数组
    @ResponseBody
    @RequestMapping("/send/array/one.html")
    public String testAjax01(@RequestParam("array") Integer[] array,HttpServletRequest request){
        for(Integer num : array){
            System.out.println("num:"+num);
        }
        logger.info(String.valueOf(CrowdUtil.judgeRequestType(request)));
        //System.out.println(1/0);
        return "success";
    }

    //通过@RequestBody接收数组
    @ResponseBody
    @RequestMapping("/send/array/two.html")
    public String testAjax02(@RequestBody Integer[] array){
        for(Integer num : array){
            System.out.println("num:"+num);
        }
        return "success";
    }

    //通过@RequestBody接收复杂对象
    @ResponseBody
    @RequestMapping("/send/compose/object.html")
    public String testSendComposeObject(@RequestBody Student student){
        System.out.println(student);
        return "success";
    }

    //通过一个工具类（ResultEntity<T>）统一返回数据的格式
    @ResponseBody
    @RequestMapping("/send/compose/object.json")
    public ResultEntity<Student> testResultEntity(@RequestBody Student student){
        //测试空指针异常
        //String e = null;
        //int length = e.length();

        //测试数学异常
        System.out.println(1/0);

        return ResultEntity.successWithData(student);
    }

    //@RequestMapping("/admin/login/page.html")
    public String loginPage(){
        System.out.println("进入了。。");
        return "admin-login";
    }



}
