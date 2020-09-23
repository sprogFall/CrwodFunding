package org.fall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/do/crowd/launch/page.html").setViewName("project-launch");
        registry.addViewController("/agree/protocol/page.html").setViewName("project-agree");
        registry.addViewController("/return/info/page.html").setViewName("project-return");
        registry.addViewController("/create/confirm/page.html").setViewName("project-confirm");
        registry.addViewController("/create/success.html").setViewName("project-success");
    }
}
