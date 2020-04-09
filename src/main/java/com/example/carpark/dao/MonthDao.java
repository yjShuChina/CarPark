package com.example.carpark.dao;

import com.example.carpark.javabean.TbMonthChargeParameter;
import com.example.carpark.javabean.TbMonthVip;
import com.example.carpark.javabean.TbUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MonthDao
{
    //添加用户信息
    public void addUser(TbUser tbUser);

    //添加月缴信息
    public void addMonthlyPayment(TbMonthVip tbMonthVip);

    //查询月缴产品表
    public List<TbMonthChargeParameter> findMonthCharge();

    //通过月缴参数id，查询缴交月份
    public int findMonthById(int mcpId);

    //通过车牌号，查询用户id
    public int findIdByCarNumber(String carNumber);
}
