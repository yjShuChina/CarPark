package com.example.carpark.service.impl;


import com.example.carpark.dao.ChargeDao;
import com.example.carpark.javabean.TbChargerParameter;
import com.example.carpark.javabean.TbParkCarInfo;
import com.example.carpark.javabean.TbUser;
import com.example.carpark.javabean.TbWhiteList;
import com.example.carpark.service.CostCalculationService;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//费用计算
@Service
public class CostCalculationServiceImpl implements CostCalculationService {


    @Resource
    private ChargeDao chargeDao;


    //停车时间规则计算收费金额
    @Override
    public int carCount(String formatTime1, String formatTime2) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这个是你要转成后的时间的格式
        int money = 0;
        try {
            long j = sdf.parse(formatTime1).getTime();
            long c = sdf.parse(formatTime2).getTime();
            long suan = c - j;
            money = timeDifferenceCount(suan);
            System.out.println("停车时间等于：" + suan);
        } catch (
                ParseException e) {
            e.printStackTrace();
        }

        return money;
    }

    //时差计算金额
    @Override
    public int timeDifferenceCount(long suan) {
        int money = 0;

        //计费规则获取
        List<TbChargerParameter> rule = chargeDao.chargePrice();
        if (rule == null) {
            System.out.println("无规则计算默认等于" + money);
            return money;
        }

        if (rule.get(rule.size() - 1).getCpType() == 0) {
            int day = (int) (suan / (1000 * 60 * 60 * 24));
            if (day > 0) {
                suan = (suan - (day * 24 * 60 * 60 * 1000));
                money = carCount(suan, rule);
                money += day * Integer.parseInt("" + rule.get(rule.size() - 1).getPrice());
            } else {
                money = carCount(suan, rule);
            }
        } else {
            money = carCount(suan, rule);
        }
        System.out.println("计算最终费用等于" + money);
        return money;
    }

    //时差具体计算方法
    private int carCount(long suan, List<TbChargerParameter> rule) {
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
                    boolean flag = (i < rule.size() - 1);
                    while (true) {
                        money += (int) rule.get(i).getPrice();
                        time += Integer.parseInt(rule.get(i).getStackTime());
                        if (flag) {
                            if (time >= Integer.parseInt(rule.get(i + 1).getChargeTime()) || time >= suan) {
                                break;
                            }
                        } else if (time >= suan) {
                            break;
                        }
                    }
                } else {
                    break;
                }
            }
        }
        return money;
    }




    /*
     * 车辆交费状态查询
     *  0 :白名单
     *  -1 :包月车
     *  -2 :临时车
     *  >0 :包月时间包含停车时间的特殊车辆
     */

    @Override
    public long chargeCalculation(String carNumber) {

        //白名单查询
        TbWhiteList tbWhiteList = chargeDao.whitelistQuery(carNumber);
        if (tbWhiteList != null) {
            return 0l;
        }

        //月卡查询(用户查询)
        TbUser tbUser = chargeDao.userQuery(carNumber);
        if (tbUser == null) {
            //临时车
            return -2l;
        }

        //获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这个是你要转成后的时间的格式
        long dq = 0l;
        try {
            dq = sdf.parse(sdf.format(new Date())).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TbParkCarInfo tbParkCarInfo = chargeDao.carParkQuery(carNumber);

        //车辆进场时间
        long jc = tbParkCarInfo.getCarTime().getTime();

        //月卡生效失效时间
        long sx = tbUser.getMonthVipBegin().getTime();
        long yt = tbUser.getMonthVipDeadline().getTime();

        if (sx < jc && dq < yt) {
            //包月车
            return -1l;
        }

        if (jc > yt) {
            //临时车
            return -2l;
        }
        long timedate = 0;

        if (jc < sx) {
            timedate += (sx - jc);
        }
        if (dq > yt) {
            timedate += (dq - yt);
        }
        //过期特殊车
        return timedate;
    }


    //临时车费用计算
//    private int TemporaryCar(String carNumber) {
//        TbParkCarInfo tbParkCarInfo = chargeDao.carParkQuery(carNumber);
//        tbParkCarInfo.getCarTime().getTime();
//        return 0;
//    }

    @Override
    public String getTimeDifference(String formatTime1, String formatTime2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            long t2 = sdf.parse(formatTime1).getTime();
            long t1 = sdf.parse(formatTime2).getTime();
            int day = (int) ((t1 - t2) / (1000 * 60 * 60 * 24));
            int hours = (int) ((t1 - t2) / (1000 * 60 * 60) - (day * 24));
            int minutes = (int) (((t1 - t2) / 1000 - hours * (60 * 60)) / 60 - (day * 24 * 60));
            int second = (int) ((t1 - t2) / 1000 - hours * (60 * 60) - minutes * 60 - (day * 24 * 60 * 60));

            if (day > 0) {
                return "" + day + "天" + hours + "小时" + minutes + "分" + second + "秒";
            }
            return "" + hours + "小时" + minutes + "分" + second + "秒";

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
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
            long j = sdf.parse(sdf.format(new Date())).getTime();
            long c = sdf.parse("2020-04-13 18:00:00").getTime();
            System.out.println();
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
                        boolean flag = (i < rule.size() - 1);
                        while (true) {
                            money += (int) rule.get(i).getPrice();
                            time += Integer.parseInt(rule.get(i).getStackTime());
                            if (flag) {
                                if (time >= Integer.parseInt(rule.get(i + 1).getChargeTime()) || time >= suan) {
                                    break;
                                }
                            } else if (time >= suan) {
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


    //场内信息查询
    @Override
    public TbParkCarInfo carInfo(String carNumber) {
        return chargeDao.carParkQuery(carNumber);
    }
}
