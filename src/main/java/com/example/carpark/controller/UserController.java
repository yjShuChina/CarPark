package com.example.carpark.controller;


import com.example.carpark.javabean.ResultDate;
import com.example.carpark.javabean.TbUser;
import com.example.carpark.service.UserService;
import com.example.carpark.util.ApplicationContextHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping("/userLogin")
    public String userLogin(String userTel, String userPwd, HttpSession session){
        System.out.println("=============用户请求登陆==============");
        TbUser tbUser = ApplicationContextHelper.getBean(TbUser.class);
        tbUser.setUserTel(userTel);
        tbUser.setUserPwd(userPwd);
        TbUser tbUser2 = userService.userLogin(tbUser);
        if(tbUser2 != null){
            session.setAttribute("tbUser",tbUser2);
            return "success";
        }else {
            return "error";
        }
    }

}
