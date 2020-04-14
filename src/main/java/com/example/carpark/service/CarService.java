package com.example.carpark.service;

import com.example.carpark.javabean.TbParkCarInfo;
import com.example.carpark.javabean.TbUser;
import com.example.carpark.javabean.TbWhiteList;

/**
 * 车辆service接口类
 */
public interface CarService
{
    /**
     *  获取用户信息
     *
     */
    //查找车牌
    String findcarnumber(String imgFile);
    //查找用户信息
    TbUser findUsermsg(String carnumber);
    //查找白名单
    TbWhiteList findWhite(String carnumber);
    //添加入库信息
    int CarIn(TbParkCarInfo tbParkCarInfo);
}
