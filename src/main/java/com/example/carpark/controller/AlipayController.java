package com.example.carpark.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.example.carpark.javabean.*;
import com.example.carpark.service.AlipayService;
import com.example.carpark.service.CostCalculationService;
import com.example.carpark.service.MonthService;
import com.example.carpark.service.RevenueService;
import com.example.carpark.util.AlipayConfig;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 支付宝支付控制类
 */
@Controller
@RequestMapping("/alipay")
public class AlipayController {

    @Resource
    private MonthService monthService;

    @Resource
    private AlipayService alipayService;

    @Resource
    private RevenueService revenueService;

    @Resource
    private CostCalculationService costCalculationService;

    Gson g = new Gson();
    Date d = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String today = df.format(d);//今天时间

    //实例化客户端,填入所需参数
    AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
    //设置请求参数
    AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();

    /**
     * 路径跳转
     *
     * @param path
     * @return
     */
    @RequestMapping("/path/{uri}")
    public String redirect(@PathVariable(value = "uri") String path) {
        return "/alipay/jsp/" + path;
    }

    //查询月缴产品表
    @RequestMapping("/findMonthCharge")
    public String findMonthCharge(HttpServletRequest request, HttpServletResponse response) {
        List<TbMonthChargeParameter> monthChargeParameterList = monthService.findMonthCharge();
        request.setAttribute("monthChargeParameterList", monthChargeParameterList);
        return "/alipay/jsp/selfAdd";
    }

    //月缴续费显示
    @RequestMapping("/renewalFeeShow")
    public String renewalFeeShow(HttpServletRequest request, HttpServletResponse response) {
        List<TbMonthChargeParameter> monthChargeParameterList = monthService.findMonthCharge();
        request.setAttribute("monthChargeParameterList", monthChargeParameterList);
        return "/alipay/jsp/selfRenewalFee";
    }

    //新的生效时间
    @RequestMapping("/newTime")
    public String newTime(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String carNumber = request.getParameter("carNumber");
        System.out.println("月缴是否到期carNumber= " + carNumber);
        int count = monthService.findCarNumber(carNumber);//根据车牌查询用户名判空
        System.out.println("count=" + count);
        if (count > 0) {
            TbUser tbUser = monthService.findUserByCarNumber(carNumber);
            String monthVipDeadline = tbUser.getMonthVipDeadline().toString();//月缴原来到期时间
//            System.out.println("今天的日期：" + today);
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

    //根据月份，查询金额
    @RequestMapping("/payment")
    public String payment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String mcpId = request.getParameter("mcpId");
        System.out.println("根据月份，查询金额mcpId= " + mcpId);
        TbMonthChargeParameter tbmcp = monthService.findMonthById(Integer.parseInt(mcpId));
        if (tbmcp != null) {
            long price = tbmcp.getPrice();
            response.getWriter().print(price);
        }
        return null;
    }

    //临时车自助缴费数据显示
    @RequestMapping("/temporaryCarShow")
    public void temporaryCarShow(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //车牌号
        String carNumber = request.getParameter("carNumber");
        Map<String, String> map = new HashMap<>();
        System.out.println("车牌号=" + carNumber);

        //出场时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String timeC = sdf.format(new Date());
        map.put("timeC", timeC);

        //场内信息查询
        TbParkCarInfo tbParkCarInfo = costCalculationService.carInfo(carNumber);

        String timej = "未找到";
        String timeData = "无法计算";
        if (tbParkCarInfo != null) {
            timej = "" + tbParkCarInfo.getCarTime();

            //获取停放时长
            timeData = costCalculationService.getTimeDifference(timej, timeC);
            /*
             * 车辆状态查询
             *  0 :白名单
             *  -1 :包月车
             *  -2 :临时车
             *  >0 :包月时间包含停车时间的特殊车辆
             */
            long type = costCalculationService.chargeCalculation(carNumber);
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
            if (type == 0) {
                map.put("state", "高级VIP");
            }
            map.put("money", "" + money);
            map.put("timej", timej);
            map.put("timeData", timeData);

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            String str = new Gson().toJson(map);
            System.out.println(str);
            response.getWriter().print(str);
        }else{
            response.getWriter().print("error");
        }
    }

    //办理月缴续费
    @RequestMapping("/tradePay")
    public void tradePay(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {


        //在公共参数中设置回跳和通知地址
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);//异步
        alipayRequest.setReturnUrl(AlipayConfig.return_url);//同步

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String outTradeNo = new String(request.getParameter("outTradeNo").getBytes("UTF-8"), "UTF-8");
        //付款金额，必填
        String totalAmount = new String(request.getParameter("totalAmount").getBytes("UTF-8"), "UTF-8");
        //订单名称，必填
        String subject = new String(request.getParameter("subject").getBytes("UTF-8"), "UTF-8");

        TbReceivable tbReceivable = null;//月缴办理
        TbTemporaryCarRecord tbTemporaryCarRecord = null;//临时车缴费
        int count1 = 0, count2 = 0;
        if (subject.equals("月缴续费")) {
            //车牌号
            String carNumber = new String(request.getParameter("carNumber").getBytes("UTF-8"), "UTF-8");
            String monthVipBegin = request.getParameter("monthVipBegin");
            String mcpId = new String(request.getParameter("mcpId").getBytes("UTF-8"), "UTF-8");

            System.out.println("订单号=" + outTradeNo);
            System.out.println("付款金额=" + totalAmount);
            System.out.println("订单名称=" + subject);
            System.out.println("车牌号=" + carNumber);
            System.out.println("生效时间=" + monthVipBegin);
            System.out.println("月份id=" + mcpId);

            //生效时间转换格式
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            format.setLenient(false);
            Timestamp newMonthVipBegin = new Timestamp(format.parse(monthVipBegin).getTime());

            tbReceivable = new TbReceivable();

            //赋值
            tbReceivable.setPrice(new BigDecimal(totalAmount));
            tbReceivable.setOutTradeNo(outTradeNo);
            tbReceivable.setSubject(subject);
            tbReceivable.setCarNumber(carNumber);
            tbReceivable.setMonthVipBegin(newMonthVipBegin);
            tbReceivable.setMcpId(Integer.parseInt(mcpId));
            count1 = alipayService.addReceivable(tbReceivable);
        } else if (subject.equals("临时车辆")) {

            String carNumber = request.getParameter("carNumber");
            String entryTime = request.getParameter("entryTime");//进场时间
            String handleTime = request.getParameter("handleTime");//缴费时间
            String time = request.getParameter("time");//停放时长

            System.out.println("订单号=" + outTradeNo);
            System.out.println("付款金额=" + totalAmount);
            System.out.println("订单名称=" + subject);
            System.out.println("车牌号=" + carNumber);
            System.out.println("进场时间=" + entryTime);
            System.out.println("出场时间=" + handleTime);
            System.out.println("停放时长=" + time);

            TbParkCarInfo tbParkCarInfo = alipayService.findParkCarInfoByCar(carNumber);

            //生效时间转换格式
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format.setLenient(false);
            Timestamp newEntryTime = new Timestamp(format.parse(entryTime).getTime());//进场时间，时间格式
            Timestamp newHandleTime = new Timestamp(format.parse(handleTime).getTime());//出场时间，时间格式

            tbTemporaryCarRecord = new TbTemporaryCarRecord();
            tbTemporaryCarRecord.setPciId(tbParkCarInfo.getPciId());
            tbTemporaryCarRecord.setPrice(new BigDecimal(totalAmount));
            tbTemporaryCarRecord.setOutTradeNo(outTradeNo);
            tbTemporaryCarRecord.setSubject(subject);
            tbTemporaryCarRecord.setCarNumber(carNumber);
            tbTemporaryCarRecord.setEntryTime(newEntryTime);
            tbTemporaryCarRecord.setHandleTime(newHandleTime);
            tbTemporaryCarRecord.setTime(time);
            count2 = alipayService.addTemporaryCarRecord(tbTemporaryCarRecord);
        }


        //参数保存成功后，执行支付
        if (count1 > 0 || count2 > 0) {

            alipayRequest.setBizContent("{\"out_trade_no\":\"" + outTradeNo + "\","
                    + "\"total_amount\":\"" + totalAmount + "\","
                    + "\"subject\":\"" + subject + "\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
            String form = "";
            try {
                form = alipayClient.pageExecute(alipayRequest).getBody(); // 调用SDK生成表单
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
            response.setContentType("text/html;charset=" + AlipayConfig.charset);
            response.getWriter().write(form);// 直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();

        }
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


    @RequestMapping(value = "/returnUrl", method = RequestMethod.GET)
    public String returnUrl(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AlipayApiException, ParseException {
        System.out.println("=================================同步回调=====================================");

        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("utf-8"), "utf-8");
            params.put(name, valueStr);
        }

        System.out.println(params);//查看参数都有哪些
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); // 调用SDK验证签名
        //验证签名通过
        if (signVerified) {
            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            System.out.println("商户订单号=" + out_trade_no);
            System.out.println("支付宝交易号=" + trade_no);
            System.out.println("付款金额=" + total_amount);

            //-------------------------------------------
            //通过订单号，查询自助收费记录表
//            TbReceivable tbReceivable = alipayService.findReceivableById(out_trade_no);
//
//            //通过订单号，查询临时车辆自助缴费记录表
//            TbTemporaryCarRecord tbTemporaryCarRecord = alipayService.findTemporaryCarRecordById(out_trade_no);
//
//            //处理自助月缴续费的返回逻辑
//            if (tbReceivable != null && tbReceivable.getSubject().equals("月缴续费")) {
//
//                String carNumber = tbReceivable.getCarNumber();//车牌号
//                Timestamp monthVipBegin = tbReceivable.getMonthVipBegin();//新的生效时间
//                int mcpId = (int) tbReceivable.getMcpId();
//                TbMonthChargeParameter tbmcp = monthService.findMonthById(mcpId);
//                int month = (int) tbmcp.getMonth();//续费办理的月份
//                TbUser tbUser = monthService.findUserByCarNumber(carNumber);
//                String monthVipDeadline = timeFactory(monthVipBegin.toString(), month);//续费后，新的到期时间
//                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                format.setLenient(false);
//                Timestamp newMonthVipDeadline = new Timestamp(format.parse(monthVipDeadline).getTime());//到期时间转换格式
//                tbUser.setMonthVipDeadline(newMonthVipDeadline);//修改到期日期
//
//                int count1 = monthService.alterUserByCarNumber(tbUser);//修改用户表日期信息
//                //通过用户id，查询月缴用户记录信息
//                TbMonthVip tbMonthVip = monthService.findMonthVipById((int) tbUser.getUserId());
//                tbMonthVip.setMcpId(mcpId);
//                tbMonthVip.setOriginDeadline(tbUser.getMonthVipDeadline());
//                tbMonthVip.setCurrentDeadline(tbUser.getMonthVipDeadline());
//                int count2 = monthService.alterMonthVipById(tbMonthVip);
//                if (count1 != 0 && count2 != 0) {
//                    //支付成功，修复支付状态
//                    int num = alipayService.alterReceivableById(out_trade_no);
//                    if (num > 0){
//                        //添加统计
//                        TbRevenue tbRevenue = new TbRevenue();
//                        tbRevenue.setIncomeType("auto");
//                        tbRevenue.setMonth(month);
//                        tbRevenue.setPrice(new BigDecimal(total_amount));
//                        tbRevenue.setTime(tbReceivable.getReceivableTime().toString());
//                        tbRevenue.setRevenue(1);
//                        revenueService.addRevenue(tbRevenue);
//
//                    }
//                    return "/alipay/jsp/selfServicePayment";//跳转付款成功页面
//                }
//            } else if (tbTemporaryCarRecord != null && tbTemporaryCarRecord.getSubject().equals("临时车辆")) {
//                int num = alipayService.alterTemporaryCarRecordById(out_trade_no);
//                if (num > 0) {
//                    //添加统计
//                    TbRevenue tbRevenue = new TbRevenue();
//                    tbRevenue.setIncomeType("auto");
//                    tbRevenue.setMonth(0);
//                    tbRevenue.setPrice(new BigDecimal(total_amount));
//                    tbRevenue.setTime(tbTemporaryCarRecord.getHandleTime().toString());
//                    tbRevenue.setRevenue(1);
//                    revenueService.addRevenue(tbRevenue);
//                    return "/alipay/jsp/selfServicePayment";//跳转付款成功页面
//                }
//            }
//            return null;
            //-----------------------------------------------
            return "/alipay/jsp/selfServicePayment";//跳转付款成功页面
        } else {
            return "/alipay/jsp/no";//跳转付款失败页面
        }

    }

    @RequestMapping(value = "/notifyUrl", method = RequestMethod.POST)
    public void notifyUrl(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AlipayApiException, ParseException {
        System.out.println("=================================异步回调=====================================");

        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("utf-8"), "utf-8");
            params.put(name, valueStr);
        }

        System.out.println(params);//查看参数都有哪些
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); // 调用SDK验证签名
        //验证签名通过
        if (signVerified) {
            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

            System.out.println("商户订单号=" + out_trade_no);
            System.out.println("支付宝交易号=" + trade_no);
            System.out.println("交易状态=" + trade_status);

            //------------------------------------------------------------------------------------------------------------
            //通过订单号，查询自助收费记录表
            TbReceivable tbReceivable = alipayService.findReceivableById(out_trade_no);

            //通过订单号，查询临时车辆自助缴费记录表
            TbTemporaryCarRecord tbTemporaryCarRecord = alipayService.findTemporaryCarRecordById(out_trade_no);

            //处理自助月缴续费的返回逻辑
            if (tbReceivable != null && tbReceivable.getSubject().equals("月缴续费")) {

                String carNumber = tbReceivable.getCarNumber();//车牌号
                Timestamp monthVipBegin = tbReceivable.getMonthVipBegin();//新的生效时间
                int mcpId = (int) tbReceivable.getMcpId();
                TbMonthChargeParameter tbmcp = monthService.findMonthById(mcpId);
                int month = (int) tbmcp.getMonth();//续费办理的月份
                TbUser tbUser = monthService.findUserByCarNumber(carNumber);
                String monthVipDeadline = timeFactory(monthVipBegin.toString(), month);//续费后，新的到期时间
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                format.setLenient(false);
                Timestamp newMonthVipDeadline = new Timestamp(format.parse(monthVipDeadline).getTime());//到期时间转换格式
                tbUser.setMonthVipDeadline(newMonthVipDeadline);//修改到期日期

                int count1 = monthService.alterUserByCarNumber(tbUser);//修改用户表日期信息
                //通过用户id，查询月缴用户记录信息
                TbMonthVip tbMonthVip = monthService.findMonthVipById((int) tbUser.getUserId());
                tbMonthVip.setMcpId(mcpId);
                tbMonthVip.setOriginDeadline(tbUser.getMonthVipDeadline());
                tbMonthVip.setCurrentDeadline(tbUser.getMonthVipDeadline());
                int count2 = monthService.alterMonthVipById(tbMonthVip);
                if (count1 != 0 && count2 != 0) {
                    //支付成功，修复支付状态
                    int num = alipayService.alterReceivableById(out_trade_no);
                    if (num > 0) {
                        //添加统计
                        TbRevenue tbRevenue = new TbRevenue();
                        tbRevenue.setIncomeType("auto");
                        tbRevenue.setMonth(month);
                        tbRevenue.setPrice(new BigDecimal(String.valueOf(tbReceivable.getPrice())));
                        tbRevenue.setTime(tbReceivable.getReceivableTime().toString());
                        tbRevenue.setRevenue(1);
                        revenueService.addRevenue(tbRevenue);

                    }
                }
            } else if (tbTemporaryCarRecord != null && tbTemporaryCarRecord.getSubject().equals("临时车辆")) {
                //支付成功，修复支付状态
                int num = alipayService.alterTemporaryCarRecordById(out_trade_no);
                if (num > 0) {
                    //添加统计
                    TbRevenue tbRevenue = new TbRevenue();
                    tbRevenue.setIncomeType("auto");
                    tbRevenue.setMonth(0);
                    tbRevenue.setPrice(new BigDecimal(String.valueOf(tbTemporaryCarRecord.getPrice())));
                    tbRevenue.setTime(tbTemporaryCarRecord.getHandleTime().toString());
                    tbRevenue.setRevenue(1);
                    revenueService.addRevenue(tbRevenue);
                }
            }
            //------------------------------------------------------------------------------------------------------------

            if (trade_status.equals("TRADE_FINISHED")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序
//                System.out.println("TRADE_FINISHED");
//                response.getWriter().println("success");
                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            } else if (trade_status.equals("TRADE_SUCCESS")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序
//                System.out.println("TRADE_SUCCESS");
//                response.getWriter().println("success");
                //注意：
                //付款完成后，支付宝系统发送该交易状态通知
            }

            response.getWriter().println("success");
        } else {
            response.getWriter().println("fail");
        }
    }


}
