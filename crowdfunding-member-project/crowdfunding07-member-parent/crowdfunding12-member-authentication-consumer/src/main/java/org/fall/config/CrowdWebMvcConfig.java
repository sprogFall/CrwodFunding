package org.fall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 前端请求的url地址
        String urlPath = "/auth/to/member/reg/page.html";

        // 实际后端跳转页面（会自动拼上前后缀）
        String viewName = "member-reg";

        // 前往用户注册页面
        registry.addViewController(urlPath).setViewName(viewName);

        // 前往登录页面
        registry.addViewController("/auth/to/member/login/page.html").setViewName("member-login");

        // 前往登录完成后的用户主页面
        registry.addViewController("/auth/to/member/center/page.html").setViewName("member-center");
        // 前往“我的众筹”页面
        registry.addViewController("/auth/to/member/crowd/page.html").setViewName("member-crowd");
    }
}
