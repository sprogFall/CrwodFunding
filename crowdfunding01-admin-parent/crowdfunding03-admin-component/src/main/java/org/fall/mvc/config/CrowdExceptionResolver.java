package org.fall.mvc.config;

import com.google.gson.Gson;
import org.fall.constant.CrowdConstant;
import org.fall.exception.LoginAcctAlreadyInUseException;
import org.fall.exception.LoginAcctAlreadyInUseForUpdateException;
import org.fall.exception.LoginFailedException;
import org.fall.util.CrowdUtil;
import org.fall.util.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//注解标明该类是基于注解的异常处理器类（实测，注解版异常处理比xml版本优先生效）
@ControllerAdvice
public class CrowdExceptionResolver {

    // 处理其他异常
    @ExceptionHandler(value = {Exception.class})
    public ModelAndView resolveException(Exception exception,
            HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        return commonCode(exception,request,response,"system-error");
    }

    // 处理数学异常,这里如果内部操作相同，跳转页面也相同，其实可以放在上面一个方法中，此处只是为了演示
    @ExceptionHandler(value = {ArithmeticException.class})
    public ModelAndView resolveArithmeticException(ArithmeticException exception,
            HttpServletRequest request,HttpServletResponse response) throws IOException {
        return commonCode(exception,request,response,"system-error");

    }

    // 触发登录失败异常，则继续返回登陆页面
    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView resolverLoginFailedException(
            LoginFailedException exception, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String viewName = "admin-login";
        return commonCode(exception,request,response,viewName);
    }

    // 触发禁止访问受保护资源异常，返回登陆页面
    /*      这里使用@ExceptionHandler注解捕捉异常在访问/admin/main/page.html时不会进入该方法
            原因是：上面的视图是在mvc的配置文件中用mvc:view-controller修饰的，这种页面，会使用默认的异常处理器
            而不是使用自定义处理器，
            因此这里必须通过mvc配置文件来配置异常映射，或不使用view-controller，而是把页面跳转放在Controller中。
    @ExceptionHandler(value = AccessForbiddenException.class)
    public ModelAndView resolverAccessForbiddenException(
            AccessForbiddenException exception, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String viewName = "admin-login";
        return commonCode(exception,request,response,viewName);
    }

     */

    // 新增管理员时，login_acct已存在，则返回admin-add.jsp页面
    @ExceptionHandler(value = LoginAcctAlreadyInUseException.class)
    public ModelAndView resolverLoginAcctAlreadyInUseException(
            LoginAcctAlreadyInUseException exception, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String viewName = "admin-add";
        return commonCode(exception,request,response,viewName);
    }

    // 更新时，不应将账号改为与其他账号同名
    @ExceptionHandler(value = LoginAcctAlreadyInUseForUpdateException.class)
    public ModelAndView resolverLoginAcctAlreadyInUseForUpdateException(
            LoginAcctAlreadyInUseForUpdateException exception, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonCode(exception,request,response,viewName);
    }

    // 整理出的不同异常的可重用代码
    private ModelAndView commonCode(
            //触发的异常，此处借助多态
            Exception exception,
            //客户器端的请求
            HttpServletRequest request,
            //服务端的响应
            HttpServletResponse response,
            //指定普通页面请求时，去的错误页面
            String viewName
    ) throws IOException {
        boolean judgeRequestType = CrowdUtil.judgeRequestType(request);
        if (judgeRequestType){
            //if判断-是json请求
            ResultEntity<Object> failed = ResultEntity.failed(exception.getMessage());
            //创建Gson对象
            Gson gson = new Gson();
            //将ResultEntity对象转换成json格式
            String json = gson.toJson(failed);
            //通过原生servlet的response传回异常内容
            response.getWriter().write(json);
            //此时只需要返回null（因为是通过json格式返回数据）
            return null;
        } else {
            //if判断-是普通页面请求
            //创建ModelAndView对象
            ModelAndView modelAndView = new ModelAndView();
            //设置触发异常跳转的页面（会自动被视图解析器加上前后缀）
            modelAndView.setViewName(viewName);
            //将异常信息加入
            modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION, exception);
            //返回设置完成的ModelAndView
            return modelAndView;
        }
    }


}
