package com.example.carpark.controller;

import com.example.carpark.javabean.*;
import com.example.carpark.service.AdminService;
import com.example.carpark.service.ChargeService;
import com.example.carpark.service.MonthService;
import com.example.carpark.util.MD5;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 收费员控制类
 */
@Controller
@RequestMapping("/charge")
public class ChargeController {

    @Autowired
    private ChargeService chargeService;

    @Resource
    private MonthService monthService;
    Gson g = new Gson();

    /**
     * 管理员登陆验证
     *
     * @param param param说明：param里面有admin_account、admin_pwd、captcha
     */
    @RequestMapping("/chargeLogin")
    @ResponseBody
    public String adminLogin(@RequestParam Map<String, Object> param, HttpSession session, HttpServletRequest request) {
        System.out.println("===============================收费员登录=============================");
        String vcode = session.getAttribute("vcode").toString();
        if (vcode.equalsIgnoreCase(param.get("captcha").toString())) {

            System.out.println(param.get("cashier_account").toString());
            String pass = MD5.machining(param.get("cashier_pwd").toString());
            param.put("cashier_pwd", pass);
            System.out.println(param.get("cashier_pwd").toString());
            TbCashier tbCashier = chargeService.chargeLogin(param);

            if (tbCashier != null) {
                request.getSession().setAttribute("tbCashier", tbCashier);
                return "验证成功";
            }
            return "账号或密码错误";
        }
        return "验证码错误";
    }

    /**
     * 路径跳转
     *
     * @param path
     * @return
     */
    @RequestMapping("/path/{uri}")
    public String redirect(@PathVariable(value = "uri") String path) {
        return "/charge/jsp/" + path;
    }

    //添加月缴信息
    @RequestMapping("/addMonthlyPayment")
    public void addMonthlyPayment(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {

        String jsonstr = request.getParameter("tbUser");
        TbUser tbUser = g.fromJson(jsonstr, TbUser.class);
        String mcpId = request.getParameter("mcpId");
//        System.out.println("新增月缴用户的tbUser= " + tbUser.toString());
//        System.out.println("新增月缴用户的mcpId= " + mcpId);
        int month = monthService.findMonthById(Integer.parseInt(mcpId));//用户办理VIP月份
//        System.out.println("用户办理VIP月份=" + month);
        String monthVipDeadline = timeFactory(tbUser.getMonthVipBegin().toString(), month);//VIP到期时间
//        System.out.println("VIP到期时间=" + monthVipDeadline);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        Timestamp newMonthVipDeadline = new Timestamp(format.parse(monthVipDeadline).getTime());//到期时间转换格式
//        System.out.println(newMonthVipDeadline.toString());
        tbUser.setMonthVipDeadline(newMonthVipDeadline);
        monthService.addUser(tbUser);

        if (tbUser != null) {
            int userId = monthService.findIdByCarNumber(tbUser.getCarNumber());//新增用户的id
//            System.out.println("新增用户的id=" + userId);
            TbMonthVip tbMonthVip = new TbMonthVip();
            tbMonthVip.setUserId(userId);
            tbMonthVip.setOriginDeadline(newMonthVipDeadline);
            tbMonthVip.setCurrentDeadline(newMonthVipDeadline);
            tbMonthVip.setMcpId(Integer.parseInt(mcpId));
//            System.out.println("tbMonthVip=" + tbMonthVip.toString());
            monthService.addMonthlyPayment(tbMonthVip);
            response.getWriter().print("success");
            System.out.println("月缴新增成功");
        } else {
            response.getWriter().print("error");
            System.out.println("月缴新增失败");
        }
    }

    //查询月缴产品表
    @RequestMapping("/findMonthCharge")
    public String findMonthCharge(HttpServletRequest request, HttpServletResponse response) {
        List<TbMonthChargeParameter> monthChargeParameterList = monthService.findMonthCharge();
        request.setAttribute("monthChargeParameterList", monthChargeParameterList);
        return "/charge/jsp/addPaymentPopup";
    }

    //封装用户VIP到期时间
    public static String timeFactory(String nowTime, int addMonth) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = sdf.parse(nowTime);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.MONTH, addMonth);//充值月份
        rightNow.add(Calendar.DAY_OF_YEAR, -1);//日期减1天
        Date dt1 = rightNow.getTime();
        String newTime = sdf.format(dt1);
//        System.out.println(newTime);
        return newTime;
    }
}
