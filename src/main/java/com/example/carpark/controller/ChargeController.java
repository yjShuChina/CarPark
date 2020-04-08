package com.example.carpark.controller;

<<<<<<< HEAD

import com.example.carpark.javabean.TbCashier;
import com.example.carpark.service.AdminService;
import com.example.carpark.service.ChargeService;
import com.example.carpark.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 收费员控制类
 */
=======
import com.example.carpark.javabean.TbUser;
import com.example.carpark.service.MonthService;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

>>>>>>> 3889b8d... 潘奕安0408
@Controller
@RequestMapping("/charge")
public class ChargeController {

<<<<<<< HEAD
    @Autowired
    private ChargeService chargeService;

    /**
     * 管理员登陆验证
     * @param param
     * param说明：param里面有admin_account、admin_pwd、captcha
     */
    @RequestMapping("/chargeLogin")
    @ResponseBody
    public String adminLogin(@RequestParam Map<String,Object> param, HttpSession session, HttpServletRequest request){
        System.out.println("===============================收费员登录=============================");
        String vcode = session.getAttribute("vcode").toString();
        if(vcode.equalsIgnoreCase(param.get("captcha").toString())){

            System.out.println(param.get("cashier_account").toString());

            String pass = MD5.machining(param.get("cashier_pwd").toString());
            param.put("cashier_pwd",pass);
            System.out.println(param.get("cashier_pwd").toString());
            TbCashier tbCashier = chargeService.chargeLogin(param);

            if(tbCashier != null){
                request.getSession().setAttribute("tbCashier",tbCashier);
                return "验证成功";
            }
            return "账号或密码错误";
        }
        return "验证码错误";
    }

    /**
     * 路径跳转
     * @param path
     * @return
     */
    @RequestMapping("/path/{uri}")
    public String redirect(@PathVariable(value = "uri")String path){
        return "/charge/jsp/"+path;
    }
=======
    @Resource
    private MonthService monthService;
    Gson g = new Gson();

    /**
     * 收费员端页面跳转
     *
     * @param path
     * @return
     */
    @RequestMapping("/path/{url}")
    public String getUrl(@PathVariable(value = "url") String path) {
        return "/cashier/jsp/" + path;
    }

    //添加月缴信息
    @RequestMapping("/addMonthlyPayment")
    public void addMonthlyPayment(HttpServletRequest request, HttpServletResponse response) throws IOException {

//        String jsonstr = request.getParameter("tbUser");
//        TbUser tbUser = g.fromJson(jsonstr, TbUser.class);
//        System.out.println("新增月缴用户的tbUser= " + tbUser);
//        monthService.addMonthlyPayment(tbUser);
//        if (tbUser != null) {
//            response.getWriter().print("success");
//            System.out.println("月缴新增成功");
//
//        } else {
//            response.getWriter().print("error");
//            System.out.println("月缴新增失败");
//        }
    }

>>>>>>> 3889b8d... 潘奕安0408
}
