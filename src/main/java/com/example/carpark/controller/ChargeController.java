package com.example.carpark.controller;

import com.example.carpark.javabean.*;
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
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    Date d = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String today = df.format(d);//今天时间

    @Resource
    private Refund refund;

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

    //收费规则数据查询接口
    @RequestMapping("/chargePrice")
    public void chargePrice(HttpServletResponse response) throws IOException {
        response.getWriter().print(chargeService.chargePrice());
    }

    //收费规则修改
    @RequestMapping("/modifyChargePrice")
    public void modifyChargePrice(TbChargerParameter tbChargerParameter, HttpServletResponse response) throws IOException {
        int i = chargeService.modifyChargePrice(tbChargerParameter);
        if (i == 1) {
            response.getWriter().print("succeed");
        }
    }

    //收费规则添加
    @RequestMapping("/addChargePrice")
    public void addChargePrice(TbChargerParameter tbChargerParameter, HttpServletResponse response) throws IOException {
        int i = chargeService.addChargePrice(tbChargerParameter);
        if (i == 1) {
            response.getWriter().print("succeed");
        }
    }

    //收费规则删除
    @RequestMapping("/delChargePrice")
    public void delChargePrice(String data, HttpServletResponse response) throws IOException {
        TbChargerParameter[] tbChargerParameter = new Gson().fromJson(data,TbChargerParameter[].class);
        System.out.println("后台接受数据==="+new Gson().toJson(tbChargerParameter));
        Integer i = chargeService.delChargePrice(tbChargerParameter);
        if (i == tbChargerParameter.length) {
            response.getWriter().print("succeed");
        }
    }


    //图片识别车牌号
    @RequestMapping("/uploadTrainPicture")
    @ResponseBody
    public void addTrainPicture(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        System.out.println(file.getOriginalFilename());
        String str = chargeService.findcarnumber(file);
        System.out.println("车牌号=" + str);
    }


    //添加月缴信息
    @RequestMapping("/addMonthlyPayment")
    public void addMonthlyPayment(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {

        String jsonstr = request.getParameter("tbUser");
        TbUser tbUser = g.fromJson(jsonstr, TbUser.class);
        String mcpId = request.getParameter("mcpId");
//        System.out.println("新增月缴用户的tbUser= " + tbUser.toString());
//        System.out.println("新增月缴用户的mcpId= " + mcpId);
        TbMonthChargeParameter tbmcp = monthService.findMonthById(Integer.parseInt(mcpId));//用户办理VIP月份
        int month = (int) tbmcp.getMonth();
//        System.out.println("用户办理VIP月份=" + month);
        String monthVipDeadline = timeFactory(tbUser.getMonthVipBegin().toString(), month);//VIP到期时间
//        System.out.println("VIP到期时间=" + monthVipDeadline);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        Timestamp newMonthVipDeadline = new Timestamp(format.parse(monthVipDeadline).getTime());//到期时间转换格式
//        System.out.println(newMonthVipDeadline.toString());
        tbUser.setMonthVipDeadline(newMonthVipDeadline);
        int count1 = monthService.addUser(tbUser);

        if (count1 != 0) {
            int userId = monthService.findIdByCarNumber(tbUser.getCarNumber());//新增用户的id
//            System.out.println("新增用户的id=" + userId);
            TbMonthVip tbMonthVip = new TbMonthVip();
            tbMonthVip.setUserId(userId);
            tbMonthVip.setOriginDeadline(newMonthVipDeadline);
            tbMonthVip.setCurrentDeadline(newMonthVipDeadline);
            tbMonthVip.setMcpId(Integer.parseInt(mcpId));
//            System.out.println("tbMonthVip=" + tbMonthVip.toString());
            int count2 = monthService.addMonthlyPayment(tbMonthVip);
            if (count2 != 0) {
                response.getWriter().print("success");
                System.out.println("月缴新增成功");
            }
        } else {
            response.getWriter().print("error");
            System.out.println("月缴新增失败");
        }
    }

    //查询月缴产品表
    @RequestMapping("/findMonthCharge")
    public String findMonthCharge(HttpServletRequest request, HttpServletResponse response) {
        String carNumber = request.getParameter("carNumber");
        System.out.println("办理月缴续费carNumber= " + carNumber);
        List<TbMonthChargeParameter> monthChargeParameterList = monthService.findMonthCharge();
        int count = monthService.findCarNumber(carNumber);
        if (count <= 0) {
            request.setAttribute("carNumber", carNumber);//判断用户为新用户，在弹窗中显示车牌号
        }
        request.setAttribute("monthChargeParameterList", monthChargeParameterList);
        return "charge/jsp/addPaymentPopup";
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

    //查询车牌号是否办理月缴VIP
    @RequestMapping("/monthVIP")
    public void monthVIP(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String carNumber = request.getParameter("carNumber");
        System.out.println("是否办理月缴VIPcarNumber= " + carNumber);
        int count = monthService.findCarNumber(carNumber);//根据车牌查询用户名判空
        System.out.println("count=" + count);
        if (count > 0) {
            TbUser tbUser = monthService.findUserByCarNumber(carNumber);
            String monthVipDeadline = tbUser.getMonthVipDeadline().toString();
            System.out.println("今天的日期：" + today);
            int result = monthVipDeadline.compareTo(today);//result大于等于0，则月缴未到期
            if (result >= 0) {
                response.getWriter().print("success");
                System.out.println("用户已办理月缴，未到期");
            } else {
                response.getWriter().print("pass");
                System.out.println("用户办理月缴已过期，请缴费");
            }
        } else {
            response.getWriter().print("error");
            System.out.println("用户不是月缴用户");
        }
    }

    //月缴是否到期
    @RequestMapping("/timeOut")
    public String timeOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String carNumber = request.getParameter("carNumber");
        System.out.println("月缴是否到期carNumber= " + carNumber);
        int count = monthService.findCarNumber(carNumber);//根据车牌查询用户名判空
        System.out.println("count=" + count);
        if (count > 0) {
            TbUser tbUser = monthService.findUserByCarNumber(carNumber);
            String monthVipBegin = tbUser.getMonthVipBegin().toString();//月缴生效时间
            String monthVipDeadline = tbUser.getMonthVipDeadline().toString();//月缴到期时间
//            System.out.println("今天的日期：" + today);
            int result = monthVipDeadline.compareTo(today);//result大于等于0，则月缴未到期
            String monthVipBeginNew = monthVipBegin.split("\\s+")[0];//根据空格切割日期
            if (result >= 0) {
                response.getWriter().print(monthVipBeginNew);
                System.out.println("月缴是否到期monthVipBeginNew=" + monthVipBeginNew);
            } else {
                response.getWriter().print("pass");
                System.out.println("用户办理月缴已过期，请缴费");
            }
        } else {
            response.getWriter().print("error");
            System.out.println("用户不是月缴用户");
        }
        return null;
    }

    //月缴续费显示
    @RequestMapping("/renewalFeeShow")
    public String renewalFeeShow(HttpServletRequest request, HttpServletResponse response) {
        String carNumber = request.getParameter("carNumber");
        System.out.println("办理月缴续费carNumber= " + carNumber);
        List<TbMonthChargeParameter> monthChargeParameterList = monthService.findMonthCharge();
        int count = monthService.findCarNumber(carNumber);
        if (count > 0) {
            request.setAttribute("carNumber", carNumber);//判断用户为需要续费用户，在弹窗中显示车牌号
        }
        request.setAttribute("monthChargeParameterList", monthChargeParameterList);
        return "/charge/jsp/renewalFee";
    }

    //办理月缴续费
    @RequestMapping("/renewalFee")
    public void renewalFee(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {

        String jsonstr = request.getParameter("tbUser");
        TbUser tbUser = g.fromJson(jsonstr, TbUser.class);
        String mcpId = request.getParameter("mcpId");
        System.out.println("月缴续费的tbUser= " + tbUser.toString());
        System.out.println("月缴续费的mcpId= " + mcpId);
        TbMonthChargeParameter tbmcp = monthService.findMonthById(Integer.parseInt(mcpId));//续费办理的月份
        int month = (int) tbmcp.getMonth();
        TbUser tbUser1 = monthService.findUserByCarNumber(tbUser.getCarNumber());
        String oldMonthVipBegin = tbUser1.getMonthVipBegin().toString();//原先的生效时间
        String oldMonthVipDeadline = tbUser1.getMonthVipDeadline().toString();//原先的到期时间
        String newMonthVipBegin = tbUser.getMonthVipBegin().toString();//新的生效时间
        int result = oldMonthVipBegin.compareTo(today);//result大于等于0，则月缴未到期
        String monthVipDeadline = null;
        if (result >= 0) {
            //未到期逻辑
            monthVipDeadline = timeFactory(oldMonthVipDeadline, month);//续费后，新的到期时间
        } else {
            //到期逻辑
            tbUser1.setMonthVipBegin(tbUser.getMonthVipBegin());//新的生效时间
            monthVipDeadline = timeFactory(newMonthVipBegin, month);//续费后，新的到期时间
        }
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        Timestamp newMonthVipDeadline = new Timestamp(format.parse(monthVipDeadline).getTime());//到期时间转换格式
        tbUser1.setMonthVipDeadline(newMonthVipDeadline);//修改到期日期

        int count1 = monthService.alterUserByCarNumber(tbUser1);//修改用户表日期信息
        //通过用户id，查询月缴用户记录信息
        TbMonthVip tbMonthVip = monthService.findMonthVipById((int) tbUser1.getUserId());
        tbMonthVip.setMcpId(Integer.parseInt(mcpId));
        tbMonthVip.setOriginDeadline(tbUser1.getMonthVipDeadline());
        tbMonthVip.setCurrentDeadline(tbUser1.getMonthVipDeadline());
        int count2 = monthService.alterMonthVipById(tbMonthVip);
        if (count1 != 0 && count2 != 0) {
            response.getWriter().print("success");
            System.out.println("月缴续费成功");
        } else {
            response.getWriter().print("error");
            System.out.println("月缴续费失败");
        }
    }

    //月缴用户退费显示
    @RequestMapping("/refundShow")
    public String refundShow(HttpServletRequest request, HttpServletResponse response) {
        String carNumber = request.getParameter("carNumber");
        System.out.println("办理月缴续费显示carNumber= " + carNumber);
        int count = monthService.findCarNumber(carNumber);
        if (count > 0) {
            request.setAttribute("carNumber", carNumber);//判断用户是否为月缴用户，在弹窗中显示车牌号
        }
        return "/charge/jsp/refund";
    }

    //月缴用户退费验证
    @RequestMapping("/refundCheck")
    @ResponseBody
    public void refundCheck(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String carNumber = request.getParameter("carNumber");
        System.out.println("办理月缴续费carNumber= " + carNumber);
        TbUser tbUser = monthService.findUserByCarNumber(carNumber);
        String monthVipBegin = tbUser.getMonthVipBegin().toString();//月缴生效时间
        String monthVipBeginNew = monthVipBegin.split("\\s+")[0];//根据空格切割日期
        TbMonthVip tbMonthVip = monthService.findMonthVipById((int) tbUser.getUserId());
        TbMonthChargeParameter tbmcp = monthService.findMonthById((int) tbMonthVip.getMcpId());
        refund.setMonthVipBegin(monthVipBeginNew);
        refund.setMonth(tbmcp.getMonth());
        refund.setPrice(tbmcp.getPrice());
        String jsonstr = g.toJson(refund);
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonstr);
        printWriter.flush();
    }

    //办理月缴退费
    @RequestMapping("/refund")
    public void refund(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        String carNumber = request.getParameter("carNumber");
        String price = request.getParameter("price");
        TbUser tbUser = monthService.findUserByCarNumber(carNumber);
        TbMonthVip tbMonthVip = monthService.findMonthVipById((int) tbUser.getUserId());
        //修改用户表的时间
        int count1 = monthService.resetTimeByCarNumber(carNumber);
        if (count1 > 0) {
            //添加退费表信息
            TbRefund tbRefund = new TbRefund();
            tbRefund.setUserId(tbUser.getUserId());
            tbRefund.setMvrId(tbMonthVip.getMvrId());
            tbRefund.setRefundPrice(Integer.parseInt(price));
            int count2 = monthService.addRefund(tbRefund);
            if (count2 > 0) {
                response.getWriter().print("success");
                System.out.println("月缴退费成功");
            } else {
                response.getWriter().print("error");
                System.out.println("月缴退费失败");
            }
        }
    }


}
