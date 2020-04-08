package com.example.carpark.dao;

import com.example.carpark.javabean.TbMonthVip;
import com.example.carpark.javabean.TbUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface MonthDao
{
    //添加用户信息
    public void addUser(TbUser tbUser);

    //添加月缴信息
    public void addMonthlyPayment(TbMonthVip tbMonthVip);
}
