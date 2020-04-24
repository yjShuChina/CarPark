package com.example.carpark.controller;

import com.example.carpark.javabean.*;
import com.example.carpark.service.*;
import com.example.carpark.util.MD5;
import com.example.carpark.websocket.WebSocket;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import javax.websocket.Session;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.io.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.commons.codec.binary.Base64.encodeBase64;

/**
 * 收费员控制类
 */
@Controller
@RequestMapping("/charge")
public class ChargeController {

    @Resource
    private ChargeService chargeService;

    @Resource
    private CarService carService;

    @Resource
    private CostCalculationService costCalculationService;

    private static Map<String, String> chargeMap = new HashMap<>();

    @Resource
    private RevenueService revenueService;

    @Resource
    private MonthService monthService;
    Gson g = new Gson();
    Date d = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String today = df.format(d);//今天时间
    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String today1 = df1.format(d);//今天时间

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
                if (tbCashier.getCashierState() == 1) {
                    Map<String, String> map = new HashMap<>();
                    map.put("cashierAccount",tbCashier.getCashierAccount());
                    map.put("name", tbCashier.getCashierName());

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                    String time = sdf.format(new Date());
                    map.put("time", time);
                    request.getSession().setAttribute("tbCashier", map);
                    return "验证成功";
                } else if (tbCashier.getCashierState() == 0) {
                    return "该账户已禁用";
                } else if (tbCashier.getCashierState() == 2) {
                    return "该管理员已离职";
                }
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
        TbChargerParameter[] tbChargerParameter = new Gson().fromJson(data, TbChargerParameter[].class);
        System.out.println("后台接受数据===" + new Gson().toJson(tbChargerParameter));
        Integer i = chargeService.delChargePrice(tbChargerParameter);
        if (i == tbChargerParameter.length) {
            response.getWriter().print("succeed");
        }
    }


    //车辆出场图片识别车牌号
    @RequestMapping("/uploadTrainPicture")
    @ResponseBody
    public void addTrainPicture(@RequestParam("file") MultipartFile file, HttpServletResponse response, HttpServletRequest request) throws IOException {

        System.out.println(file.getOriginalFilename());

        Map<String, String> map = new HashMap<>();
        map.put("type", "departure");
        //获取车牌号
        String car = chargeService.findcarnumber(file);
        System.out.println("车牌号=" + car);
        map.put("carnumber", car);


        //出场时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String timeC = sdf.format(new Date());
        map.put("timeC", timeC);

        //场内信息查询
        TbParkCarInfo tbParkCarInfo = costCalculationService.carInfo(car);

        String timej = "未找到";
        String timeData = "无法计算";
        int payment = 0;
        if (tbParkCarInfo != null) {
            timej = "" + tbParkCarInfo.getCarTime();
            //查询自助缴费记录
            List<TbTemporaryCarRecord> tbTemporaryCarRecord = chargeService.tbTemporaryCarRecordQuery(tbParkCarInfo);
            if (tbTemporaryCarRecord != null) {
                for (int i = 0; i < tbTemporaryCarRecord.size(); i++) {
                    payment += tbTemporaryCarRecord.get(i).getPrice().intValue();
                    if (tbTemporaryCarRecord.get(i).getHandleTime().getTime() + 600000 >= new Date().getTime()) {
                        map.put("type", "payment");
                    } else {
                        map.put("type", "paymentExpire");
                    }
                }
            }
            //车位
            map.put("parkSpaceId", tbParkCarInfo.getParkSpaceId());

            map.put("cashierId", "0");

            //进场图片url
            map.put("entryImgUrl", tbParkCarInfo.getImgUrl());

            //获取停放时长
            timeData = costCalculationService.getTimeDifference(timej, timeC);

            String imgBase64 = "";
            try {
                File files = new File(tbParkCarInfo.getImgUrl());
                byte[] content = new byte[(int) files.length()];
                FileInputStream finputstream = new FileInputStream(files);
                finputstream.read(content);
                finputstream.close();
                imgBase64 = new String(encodeBase64(content));
            } catch (IOException e) {
                e.printStackTrace();
            }
            map.put("gateUrl", imgBase64);
        }
        map.put("timej", timej);
        map.put("timeData", timeData);

        /*
         * 车辆交费状态查询
         *  0 :白名单
         *  -1 :包月车
         *  -2 :临时车
         *  -3 :无进场记录
         *  >0 :包月时间包含停车时间的特殊车辆
         */
        long type = costCalculationService.chargeCalculation(car);
        int money = 0;
        if (type > 0) {
            //获取应缴费用
            money = costCalculationService.timeDifferenceCount(type);
            map.put("state", "月缴过期");
        }
        if (type == -2) {
            //获取应缴费用
            money = costCalculationService.carCount(timej, timeC);
            map.put("state", "临时车辆");
        }
        if (type == -1) {
            map.put("state", "月缴车辆");
        }
        if (type == -3) {
            map.put("state", "无进场记录");
        }
        if (type == 0) {
            map.put("state", "高级VIP");
        }

        if (map.get("type").equals("payment")) {
            map.put("state", "临时自助缴费");
        }

        money -= payment;
        map.put("payment", "" + payment);
        map.put("money", "" + money);
        if (map.get("type").equals("paymentExpire")) {
            map.put("state", "自助缴费超时");
        }

        // new Date()为获取当前系统时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");//设置日期格式
        String timeDate = df.format(new Date());
        System.out.println(timeDate);
        String path = request.getSession().getServletContext().getRealPath("/upload");

        //获取图片url
//        String url = chargeService.uploadImage(file, timeDate + car);
        String fileName = file.getOriginalFilename();  //获取文件名
        String fileTyle = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        System.out.println("后缀名：" + fileTyle);

        //出场图片url
        String url = path + "/" + timeDate + car + fileTyle;
        map.put("exitImgUrl", url);
        System.out.println("url=================" + url);
        file.transferTo(new File(url));

        String imgBase64 = "";
        try {
            File files = new File(url);
            byte[] content = new byte[(int) files.length()];
            FileInputStream finputstream = new FileInputStream(files);
            finputstream.read(content);
            finputstream.close();
            imgBase64 = new String(encodeBase64(content));
        } catch (IOException e) {
            e.printStackTrace();
        }

        map.put("departureUrl", imgBase64);

        chargeMap = map;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String str = new Gson().toJson(map);
        System.out.println(str);
        response.getWriter().print(str);
        if (WebSocket.electricSocketMap.get("charge") != null) {
            for (Session session : WebSocket.electricSocketMap.get("charge")) {
                session.getBasicRemote().sendText(str);
            }
        }

        if (map.get("type").equals("payment")) {
            map.put("state", "临时自助缴费");
            automation();
        }
        if (map.get("type").equals("paymentExpire")) {
            automation();
        } else {
            if (money <= 0) {
                monthly();
            }
        }
    }

    //临时自助缴费电脑自动放行记录
    private void automation() {
        chargeMap.put("cashierId", "0");
        chargeMap.put("channel", "自助缴费");
        chargeMap.put("collect", chargeMap.get("payment"));
        chargeService.confirmCollection(chargeMap);
    }

    //月缴
    private void monthly(){
        chargeMap.put("cashierId", "0");
        chargeMap.put("channel", chargeMap.get("state"));
        chargeMap.put("collect", chargeMap.get("money"));
        chargeService.confirmCollection(chargeMap);
    }

    //收费员确认收款
    @RequestMapping("/confirmCollection")
    public void confirmCollection(HttpServletResponse response, HttpServletRequest request) {
        String money = chargeMap.get("money");
        try {
            Map<String, String> tbCashier = (Map<String, String>) request.getSession().getAttribute("tbCashier");
            chargeMap.put("cashierId", tbCashier.get("id"));
            if ((money.matches("[0-9]+"))) {
                chargeMap.put("channel", "人工收取");
                chargeMap.put("collect", money);
                response.getWriter().print(chargeService.confirmCollection(chargeMap));
            } else {
                response.getWriter().print("error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //excel下载
    @RequestMapping("/download")
    public ResponseEntity<byte[]> download(HttpServletRequest request) throws IOException {

        String url = chargeService.excelGenerate(request);
        System.out.println("url=============="+url);
        File file = new File(url);
        byte[] body = null;
        InputStream is = new FileInputStream(file);
        body = new byte[is.available()];
        is.read(body);
        String fileName = new String(file.getName().getBytes("UTF-8"), "iso-8859-1");//为了解决中文名称乱码问题
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=" + fileName);
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
        return entity;
    }

    //进场车辆数据获取
    @RequestMapping("/gateMaxQuery")
    public void gateMaxQuery(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(chargeService.gateMaxQuery());
    }

    //停车场车位数量查询
    @RequestMapping("/chargeQuery")
    public void chargeQuery(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        Map<String, Integer> map = new HashMap<>();
        int parkspase = carService.findParkSpacenum("1", "");//未停车
        int allps = carService.findParkSpacenum("", "");//所有车位
        map.put("parkspase", parkspase);
        map.put("allps", allps);
        response.getWriter().print(new Gson().toJson(map));
    }


    //场内车辆信息查看
    @RequestMapping("/parkQuery")
    public void parkQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));

        response.getWriter().write(chargeService.parkQuery(page, limit));
        response.getWriter().flush();
    }

    //出场车辆信息查看
    @RequestMapping("/carExitQuery")
    public void carExitQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));

        response.getWriter().write(chargeService.carExitQuery(page, limit));
        response.getWriter().flush();
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
            String monthVipDeadline = tbUser.getMonthVipDeadline().toString();//月缴到期时间
            int result = monthVipDeadline.compareTo(today);//result大于等于0，则月缴未到期
            String monthVipBeginNew = monthVipDeadline.split("\\s+")[0];//根据空格切割日期
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
        TbMonthChargeParameter tbmcp = monthService.findMonthById(Integer.parseInt(mcpId));
        int month = (int) tbmcp.getMonth();//续费办理的月份
        TbUser tbUser1 = monthService.findUserByCarNumber(tbUser.getCarNumber());//续费前，用户信息
        String newMonthVipBegin = tbUser.getMonthVipBegin().toString();//新的生效时间
        String monthVipDeadline = timeFactory(newMonthVipBegin, month);//续费后，新的到期时间
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

            //添加统计
            TbRevenue tbRevenue = new TbRevenue();
            tbRevenue.setIncomeType("manual");
            tbRevenue.setMonth(month);
            tbRevenue.setPrice(new BigDecimal(tbmcp.getPrice()));
            tbRevenue.setTime(today1);
            tbRevenue.setRevenue(1);
            String num = revenueService.addRevenue(tbRevenue);
            if (num != null) {
                response.getWriter().print("success");
                System.out.println("月缴续费成功");
            }
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
