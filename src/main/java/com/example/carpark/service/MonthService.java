package com.example.carpark.service;

import com.example.carpark.javabean.TbMonthVip;
import com.example.carpark.javabean.TbUser;

public interface MonthService {


    //添加用户信息
    public void addUser(TbUser tbUser);

    //添加月缴信息
    public void addMonthlyPayment(TbMonthVip tbMonthVip);

}
