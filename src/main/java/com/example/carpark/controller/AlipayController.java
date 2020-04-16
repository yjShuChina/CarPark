package com.example.carpark.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.example.carpark.javabean.TbMonthChargeParameter;
import com.example.carpark.javabean.TbMonthVip;
import com.example.carpark.javabean.TbReceivable;
import com.example.carpark.javabean.TbUser;
import com.example.carpark.service.AlipayService;
import com.example.carpark.service.MonthService;
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


    @RequestMapping("/tradePay")//办理月缴续费
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
        //车牌号，可空
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
        //赋值
        TbReceivable tbReceivable = new TbReceivable();
        tbReceivable.setPrice(new BigDecimal(totalAmount));
        tbReceivable.setOutTradeNo(outTradeNo);
        tbReceivable.setSubject(subject);
        tbReceivable.setCarNumber(carNumber);
        tbReceivable.setMonthVipBegin(newMonthVipBegin);
        tbReceivable.setMcpId(Integer.parseInt(mcpId));
        int count = alipayService.addReceivable(tbReceivable);

        //参数保存成功后，执行支付
        if (count > 0) {

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


    //    @RequestMapping("/tradeQuery")//交 易 查 询
//    public void tradeQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//        //在公共参数中设置回跳和通知地址
//        alipayRequest.setReturnUrl(return_url);
//        alipayRequest.setNotifyUrl(notify_url);
//
//        //商户订单号，商户网站订单系统中唯一订单号
//        String out_trade_no = new String(request.getParameter("WIDTQout_trade_no").getBytes("ISO-8859-1"), "UTF-8");
//        //支付宝交易号
//        String trade_no = new String(request.getParameter("WIDTQtrade_no").getBytes("ISO-8859-1"), "UTF-8");
//        //请二选一设置
//
//        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\"," + "\"trade_no\":\"" + trade_no + "\"}");
//
//        String form = "";
//        try {
//            form = alipayClient.pageExecute(alipayRequest).getBody(); // 调用SDK生成表单
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//        response.setContentType("text/html;charset=" + charset);
//        response.getWriter().write(form);// 直接将完整的表单html输出到页面
//        response.getWriter().flush();
//        response.getWriter().close();
//    }
//
//    @RequestMapping("/tradeRefund")//退 款
//    public void tradeRefund(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//        //商户订单号，商户网站订单系统中唯一订单号
//        String out_trade_no = new String(request.getParameter("WIDTRout_trade_no").getBytes("ISO-8859-1"), "UTF-8");
//        //支付宝交易号
//        String trade_no = new String(request.getParameter("WIDTRtrade_no").getBytes("ISO-8859-1"), "UTF-8");
//        //请二选一设置
//        //需要退款的金额，该金额不能大于订单金额，必填
//        String refund_amount = new String(request.getParameter("WIDTRrefund_amount").getBytes("ISO-8859-1"), "UTF-8");
//        //退款的原因说明
//        String refund_reason = new String(request.getParameter("WIDTRrefund_reason").getBytes("ISO-8859-1"), "UTF-8");
//        //标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
//        String out_request_no = new String(request.getParameter("WIDTRout_request_no").getBytes("ISO-8859-1"), "UTF-8");
//
//        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
//                + "\"trade_no\":\"" + trade_no + "\","
//                + "\"refund_amount\":\"" + refund_amount + "\","
//                + "\"refund_reason\":\"" + refund_reason + "\","
//                + "\"out_request_no\":\"" + out_request_no + "\"}");
//
//        String form = "";
//        try {
//            form = alipayClient.pageExecute(alipayRequest).getBody(); // 调用SDK生成表单
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//        response.setContentType("text/html;charset=" + charset);
//        response.getWriter().write(form);// 直接将完整的表单html输出到页面
//        response.getWriter().flush();
//        response.getWriter().close();
//    }
//
//    @RequestMapping("/tradeFastpay")//退 款 查 询
//    public void tradeFastpay(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//        //商户订单号，商户网站订单系统中唯一订单号
//        String out_trade_no = new String(request.getParameter("WIDRQout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
//        //支付宝交易号
//        String trade_no = new String(request.getParameter("WIDRQtrade_no").getBytes("ISO-8859-1"),"UTF-8");
//        //请二选一设置
//        //请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号，必填
//        String out_request_no = new String(request.getParameter("WIDRQout_request_no").getBytes("ISO-8859-1"),"UTF-8");
//
//        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
//                +"\"trade_no\":\""+ trade_no +"\","
//                +"\"out_request_no\":\""+ out_request_no +"\"}");
//
//        String form = "";
//        try {
//            form = alipayClient.pageExecute(alipayRequest).getBody(); // 调用SDK生成表单
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//        response.setContentType("text/html;charset=" + charset);
//        response.getWriter().write(form);// 直接将完整的表单html输出到页面
//        response.getWriter().flush();
//        response.getWriter().close();
//    }
//
//    @RequestMapping("/tradeClose")//交 易 关 闭
//    public void tradeClose(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//        //商户订单号，商户网站订单系统中唯一订单号
//        String out_trade_no = new String(request.getParameter("WIDTCout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
//        //支付宝交易号
//        String trade_no = new String(request.getParameter("WIDTCtrade_no").getBytes("ISO-8859-1"),"UTF-8");
//        //请二选一设置
//
//        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," +"\"trade_no\":\""+ trade_no +"\"}");
//
//        String form = "";
//        try {
//            form = alipayClient.pageExecute(alipayRequest).getBody(); // 调用SDK生成表单
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//        response.setContentType("text/html;charset=" + charset);
//        response.getWriter().write(form);// 直接将完整的表单html输出到页面
//        response.getWriter().flush();
//        response.getWriter().close();
//    }
//
//
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

            //通过订单号，查询自助收费记录表
            TbReceivable tbReceivable = alipayService.findReceivableById(out_trade_no);

            String carNumber = tbReceivable.getCarNumber();//车牌号
            Timestamp monthVipBegin = tbReceivable.getMonthVipBegin();//新的生效时间
            int mcpId = (int) tbReceivable.getMcpId();
            TbMonthChargeParameter tbmcp = monthService.findMonthById(mcpId);
            int month = (int) tbmcp.getMonth();//续费办理的月份
            TbUser tbUser = monthService.findUserByCarNumber(carNumber);
            String oldMonthVipBegin = tbUser.getMonthVipBegin().toString();//原先的生效时间
            String oldMonthVipDeadline = tbUser.getMonthVipDeadline().toString();//原先的到期时间
            int result = oldMonthVipBegin.compareTo(today);//result大于等于0，则月缴未到期
            String monthVipDeadline = null;
            if (result >= 0) {
                //未到期逻辑
                monthVipDeadline = timeFactory(oldMonthVipDeadline, month);//续费后，新的到期时间
            } else {
                //到期逻辑
                tbUser.setMonthVipBegin(monthVipBegin);//新的生效时间
                monthVipDeadline = timeFactory(monthVipBegin.toString(), month);//续费后，新的到期时间
            }
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
            alipayService.alterReceivableById(out_trade_no);
            }
            return "alipay/jsp/ok";//跳转付款成功页面
        } else {
            return "alipay/jsp/no";//跳转付款失败页面
        }

    }

    @RequestMapping(value = "/notifyUrl", method = RequestMethod.POST)
    public String notifyUrl(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AlipayApiException {
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

            if (trade_status.equals("TRADE_FINISHED")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            } else if (trade_status.equals("TRADE_SUCCESS")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //付款完成后，支付宝系统发送该交易状态通知
            }

            //支付成功，修复支付状态
//            payService.updateById(Integer.valueOf(out_trade_no));
            return "alipay/jsp/ok";//跳转付款成功页面
        } else {
            return "alipay/jsp/no";//跳转付款失败页面
        }
    }


}
