package org.fall.filter;

import com.netflix.zuul.ZuulFilter;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import org.fall.constant.CrowdConstant;
import org.fall.util.AccessPassResources;
import org.springframework.stereotype.Component;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Component
public class CrowdAccessFilter extends ZuulFilter {

    // return "pre" 表示在请求发生其前进行过滤
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     *
     * @return true:表示被拦截; false: 表示放行
     */
    @Override
    public boolean shouldFilter() {

        // 通过getCurrentContext得到当前的RequestContext
        RequestContext requestContext = RequestContext.getCurrentContext();

        // 通过RequestContext得到HttpServletRequest
        HttpServletRequest request = requestContext.getRequest();

        // 获得当前请求的路径
        String servletPath = request.getServletPath();

        // 判断当前请求路径是否包含在不被过滤的请求的set集合中
        boolean isPassContains = AccessPassResources.PASS_RES_SET.contains(servletPath);

        // 如果包含在set中，返回false，表示不被过滤
        if (isPassContains){
            return false;
        }

        // 判断是否是静态资源
        boolean isStaticResource = AccessPassResources.judgeIsStaticResource(servletPath);

        // 是静态资源则工具方法返回true，因为应该放行，所以取反，返回false
        return !isStaticResource;
    }

    @Override
    public Object run() throws ZuulException {

        // 通过getCurrentContext得到当前的RequestContext
        RequestContext requestContext = RequestContext.getCurrentContext();

        // 通过RequestContext得到HttpServletRequest
        HttpServletRequest request = requestContext.getRequest();

        // 得到session
        HttpSession session = request.getSession();

        // 从session中得到“loginMember”
        Object loginMember = session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);

        // 判断得到的loginMember是否为空
        if (loginMember == null){
            // 为空 取得response，为了后面进行重定向
            HttpServletResponse response = requestContext.getResponse();

            // 向session域中存放"message":"还未登录，禁止访问受保护资源！",为了在重定向后能够在前台显示错误信息
            session.setAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_ACCESS_FORBIDDEN);

            try {
                // 重定向到登录页面
                response.sendRedirect("/auth/to/member/login/page.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 返回null就是不操作
        return null;
    }
}
