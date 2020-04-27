package com.example.carpark.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        System.out.println(">>>>>>>>>>>>>>>进入拦截器");
        //获得请求路径的ur1
        String uri = request.getRequestURI();
        System.out.println("请求uri：" + uri);

//        //判断是否为请求css、js文件
//        if (uri.endsWith(".js") || uri.endsWith(".css")) {
//            return true;
//        }

        //判断路径是登出还是登录验证。是这两者之一的话执行controller中定义的方法
        if (uri.endsWith("/indexLogin") ||uri.endsWith("/adminLogin") || uri.endsWith("/exit") || uri.endsWith("/chargeLogin") || uri.endsWith("/carpark") || uri.endsWith("/machine") || uri.endsWith("/machine2") || uri.endsWith("/adminFaceLogin") || uri.endsWith("/chargeFaceLogin")) {
            System.out.println(">>>>>>>>>>不需要验证>>>>>>>>>可跳转");
            return true;
        }

        if(uri.contains("/gate") || uri.contains("/alipay")||uri.contains("/esmap")){
            System.out.println(">>>>>>>>>>不需要验证>>>>>>>>>可跳转");
            return true;
        }
        if (uri.contains("/msg")){
            if (request.getSession().getAttribute("tbAdmin") != null || request.getSession().getAttribute("tbCashier") != null) {
                System.out.println(">>>>>>>认证成功>>>>>>允许访问");
                return true;
            }
            System.out.println(">>>>>>>>>未登录>>>>>>>无法访问");
            response.sendRedirect(request.getContextPath() + "/url/admin/adminLogin");
        }
        if(uri.contains("/url/admin/mobile")){
            System.out.println(">>>>>>>>>进入管理移动版认证是否登陆");
            if (request.getSession().getAttribute("tbAdmin") != null) {
                System.out.println(">>>>>>>认证成功>>>>>>允许访问");
                return true;
            }
            System.out.println(">>>>>>>>>未登录>>>>>>>无法访问");
            response.sendRedirect(request.getContextPath() + "/url/admin/indexLogin");
            return false;
        }
        if(uri.contains("/admin") || uri.contains("/white") || uri.contains("/month")){
            System.out.println(">>>>>>>>>进入管理员认证是否登陆");
            if (request.getSession().getAttribute("tbAdmin") != null) {
                System.out.println(">>>>>>>认证成功>>>>>>允许访问");
                return true;
            }
            System.out.println(">>>>>>>>>未登录>>>>>>>无法访问");
            response.sendRedirect(request.getContextPath() + "/url/admin/adminLogin");
        }else if(uri.contains("/charge")){
            System.out.println(">>>>>>>>>进入收费员认证是否登陆");
            if (request.getSession().getAttribute("tbCashier") != null) {
                System.out.println(">>>>>>>认证成功>>>>>>允许访问");
                return true;
            }
            System.out.println(">>>>>>>>>未登录>>>>>>>无法访问");
            response.sendRedirect(request.getContextPath() + "/charge/path/chargeLogin");
        }
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
