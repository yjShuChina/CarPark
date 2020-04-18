package com.example.carpark.service;


import com.example.carpark.javabean.TbChargerParameter;
import com.example.carpark.javabean.TbParkCarInfo;

import java.util.List;

//费用计算
public interface CostCalculationService {

    //进场信息查询
    TbParkCarInfo carInfo(String carNumber);

    //时差计算时间
    String getTimeDifference(String formatTime1, String formatTime2);

    //时差计算停车费用方法一
    int carCount(String formatTime1, String formatTime2);

    //时差计算停车费用方法二
    int timeDifferenceCount(long suan);

    //车辆状态查询
    long chargeCalculation(String carNumber);
}
