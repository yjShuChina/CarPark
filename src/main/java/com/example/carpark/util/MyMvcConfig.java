package com.example.carpark.util;

import com.example.carpark.Interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    /*资源处理器*/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/layui/**").addResourceLocations("/WEB-INF/" + "/layui/");
        registry.addResourceHandler("/layuiadmin/**").addResourceLocations("/WEB-INF/" + "/layuiadmin/");
        registry.addResourceHandler("/administration/**").addResourceLocations("/WEB-INF/" + "/administration/");
        registry.addResourceHandler("/charge/**").addResourceLocations("/WEB-INF/" + "/charge/");
        registry.addResourceHandler("/gate/**").addResourceLocations("/WEB-INF/" + "/gate/");
        registry.addResourceHandler("/control/**").addResourceLocations("/WEB-INF/" + "/control/");
        registry.addResourceHandler("/driver/**").addResourceLocations("/WEB-INF/" + "/driver/");
        registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/" + "/js/");
        registry.addResourceHandler("/esmap/**").addResourceLocations("/WEB-INF/" + "/esmap/");
        registry.addResourceHandler("/alipay/**").addResourceLocations("/WEB-INF/" + "/alipay/");
        registry.addResourceHandler("/assembly/**").addResourceLocations("/WEB-INF/" + "/assembly/");
        registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/" + "/css/");
        registry.addResourceHandler("/dist/**").addResourceLocations("/WEB-INF/" + "/dist/");
        registry.addResourceHandler("/src/**").addResourceLocations("/WEB-INF/" + "/src/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor());
        registration.addPathPatterns("/**");                      //所有路径都被拦截
        registration.excludePathPatterns(  // 添加不拦截路径
                "/admin/CheckCodeServlet",              // 验证码
                "/**/*.html",            //html静态资源
                "/**/*.js",              //js静态资源
                "/**/*.css",             //css静态资源
                "/**/*.woff",
                "/**/*.ttf",
                "/**/*.woff2",
                "/**/*.jpg",
                "/**/*.gif",
                "/**/*.png",
                "/**/*.mp3"

        );
    }

}

