package com.example.carpark.util;

import com.example.carpark.Interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    /*资源处理器*/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/layui/**").addResourceLocations("/WEB-INF/" + "/layui/");
        registry.addResourceHandler("/administration/**").addResourceLocations("/WEB-INF/" + "/administration/");
        registry.addResourceHandler("/charge/**").addResourceLocations("/WEB-INF/" + "/charge/");
        registry.addResourceHandler("/control/**").addResourceLocations("/WEB-INF/" + "/control/");
        registry.addResourceHandler("/driver/**").addResourceLocations("/WEB-INF/" + "/driver/");
        registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/" + "/js/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor());
        registration.addPathPatterns("/**");                      //所有路径都被拦截
        registration.excludePathPatterns(  // 添加不拦截路径
                "/SYJadmin/CheckCodeServlet",              // 验证码
                "/**/*.html",            //html静态资源
                "/**/*.js",              //js静态资源
                "/**/*.css",             //css静态资源
                "/**/*.woff",
                "/**/*.ttf"
        );
    }
}

