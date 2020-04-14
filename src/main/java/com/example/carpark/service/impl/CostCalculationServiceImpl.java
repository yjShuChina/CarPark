package com.example.carpark.service.impl;


import com.example.carpark.dao.ChargeDao;
import com.example.carpark.javabean.TbChargerParameter;
import com.example.carpark.javabean.TbParkCarInfo;
import com.example.carpark.javabean.TbUser;
import com.example.carpark.javabean.TbWhiteList;
import com.example.carpark.service.CostCalculationService;
import com.google.gson.Gson;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//费用计算
public class CostCalculationServiceImpl implements CostCalculationService {


    @Resource
    private ChargeDao chargeDao;


    //停车时间规则计算收费金额主方法
    private String carCount(String carNumber) throws ParseException {

        //查下车辆状态
        int i = chargeCalculation(carNumber);
        if (i == -1) {
            return "场内无此车辆信息，请核对后重新查询";
        }

        //临时车计费
        if (i == 2) {
            int x = TemporaryCar(carNumber);
            return "停车费等于" + x;
        }


        //当前时间获取
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这个是你要转成后的时间的格式
        String sd = sdf.format(new Date());   // 时间戳转换成时间
        System.out.println(sd);//打印出你要的时间

        Timestamp newMonthVipDeadline = new Timestamp(sdf.parse(sd).getTime());//到期时间转换格式

        System.out.println(newMonthVipDeadline);
        Timestamp asdasd = new Timestamp(sdf.parse("2020-04-12 12:24:58").getTime());//到期时间转换格式
        System.out.println(asdasd);
        System.out.println(getTimeDifference(asdasd, newMonthVipDeadline));
        return null;
    }

    /*
     * 车辆交费状态查询
     *  0 :白名单
     *  -1:场内无车辆信息
     *  1 :有月卡记录
     *  2 :临时车
     */
    public int chargeCalculation(String carNumber) {

        //白名单查询
        TbWhiteList tbWhiteList = chargeDao.whitelistQuery(carNumber);
        if (tbWhiteList != null) {
            return 0;
        }

        //车辆场内信息查询
        TbParkCarInfo tbParkCarInfo = chargeDao.carParkQuery(carNumber);
        if (tbParkCarInfo == null) {
            return -1;
        }

        //月卡查询(用户查询)
        TbUser tbUser = chargeDao.userQuery(carNumber);
        if (tbUser != null) {
            return 1;
        }

        //临时车
        return 2;
    }


    //临时车费用计算
    private int TemporaryCar(String carNumber) {
        TbParkCarInfo tbParkCarInfo = chargeDao.carParkQuery(carNumber);
        tbParkCarInfo.getCarTime().getTime();
        return 0;
    }

    public String getTimeDifference(Timestamp formatTime1, Timestamp formatTime2) {
        long t1 = formatTime1.getTime();
        long t2 = formatTime2.getTime();
        int hours = (int) ((t1 - t2) / (1000 * 60 * 60));
        int minutes = (int) (((t1 - t2) / 1000 - hours * (60 * 60)) / 60);
        int second = (int) ((t1 - t2) / 1000 - hours * (60 * 60) - minutes * 60);

        return "" + ((t1 - t2) / 1000);
//        return ""+hours+"小时"+minutes+"分"+second+"秒";
    }

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这个是你要转成后的时间的格式

        //计费规则获取
        String str = "[{'cpId':8,'chargeTime':'1800000','cpType':0,'stackTime':'0','price':10},{'cpId':6,'chargeTime':'10800000','cpType':1,'stackTime':'3600000','price':5},{'cpId':2,'chargeTime':'18000000','cpType':1,'stackTime':'3600000','price':10},{'cpId':1,'chargeTime':'28800000','cpType':0,'stackTime':'0','price':80}]";
        TbChargerParameter[] tbChargerParameter = new Gson().fromJson(str, TbChargerParameter[].class);
        List<TbChargerParameter> rule = new ArrayList<>();
        for (int i = 0; i < tbChargerParameter.length; i++) {
            rule.add(tbChargerParameter[i]);
            System.out.println(new Gson().toJson(rule.get(i)));
        }


        try {
            long j = sdf.parse("2020-04-13 10:00:00").getTime();
            long c = sdf.parse("2020-04-13 18:00:00").getTime();

            long suan = c - j;
            System.out.println("停车时间等于：" + suan);
            int money = 0;

            //辅助计算
            int time = 0;
            for (int i = 0; i < rule.size(); i++) {
                if (rule.get(i).getCpType() == 0) {
                    if (suan > Integer.parseInt(rule.get(i).getChargeTime())) {
                        money = (int) rule.get(i).getPrice();
                    } else {
                        break;
                    }
                } else {
                    if (suan > Integer.parseInt(rule.get(i).getChargeTime())) {
                        time = Integer.parseInt(rule.get(i).getChargeTime());
                        boolean flag = (i < rule.size()-1);
                        while (true) {
                            money += (int) rule.get(i).getPrice();
                            time += Integer.parseInt(rule.get(i).getStackTime());
                            if (flag){
                                if (time >= Integer.parseInt(rule.get(i+1).getChargeTime()) || time >= suan){
                                    break;
                                }
                            }else if (time >= suan){
                                break;
                            }
                        }
                    } else {
                        break;
                    }
                }
            }
            System.out.println("计算最终费用等于" + money);
        } catch (
                ParseException e) {
            e.printStackTrace();
        }

    }

    //数组转时间
    public String getTimeDate(String timeSize) {
        long i = Long.valueOf(timeSize);
        long hours = (i / (1000 * 60 * 60));
        long minutes = ((i / 1000 - hours * (60 * 60)) / 60);
        long second = (i / 1000 - hours * (60 * 60) - minutes * 60);
        return "" + hours + ":" + minutes + ":" + second;
    }
}
