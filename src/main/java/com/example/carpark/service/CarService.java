package com.example.carpark.service;

import com.example.carpark.javabean.TbUser;

/**
 * 车辆service接口类
 */
public interface CarService
{
    /**
     *  获取用户信息
     *
     */
    String findcarnumber(String imgFile);

    TbUser findUsermsg(String carnumber);
}
