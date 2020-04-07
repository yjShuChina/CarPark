package com.example.carpark.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        System.out.println("进入拦截器");
        //获得请求路径的ur1
        String uri = request.getRequestURI();
        System.out.println("打印uri：" + uri);

        //判断是否为请求css、js文件
        if (uri.endsWith(".js") || uri.endsWith(".css")) {
            return true;
        }

        //判断路径是登出还是登录验证。是这两者之一的话执行controller中定义的方法
        if (uri.endsWith("/admin/login") || uri.endsWith("/admin/out") || uri.endsWith("/admin/index")) {
            return true;
        }

        //其他情况判断sossion中是否有key, 有的话维续用户的操作
        if (request.getSession() != null && request.getSession().getAttribute("tbadmin") != null) {
            return true;
        }

        //最后的情况就是进入登录页面
        response.sendRedirect(request.getContextPath() + "/admin/index");

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object
            o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse
            httpServletResponse, Object o, Exception e) throws Exception {

    }
}
