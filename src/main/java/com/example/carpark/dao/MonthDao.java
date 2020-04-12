package com.example.carpark.dao;

import com.example.carpark.javabean.TbMonthChargeParameter;
import com.example.carpark.javabean.TbMonthVip;
import com.example.carpark.javabean.TbRefund;
import com.example.carpark.javabean.TbUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MonthDao
{
    //添加用户信息
    public int addUser(TbUser tbUser);

    //添加月缴信息
    public int addMonthlyPayment(TbMonthVip tbMonthVip);

    //查询月缴产品表
    public List<TbMonthChargeParameter> findMonthCharge();

    //通过月缴参数id，查询缴交月份表
    public TbMonthChargeParameter findMonthById(int mcpId);

    //通过车牌号，查询用户id
    public int findIdByCarNumber(String carNumber);

    //通过车牌号，查询车牌号是否为月缴VIP
    public int findCarNumber(String carNumber);

    //通过车牌号，查询用户信息
    public TbUser findUserByCarNumber(String carNumber);

    //通过用户id，查询月缴用户记录信息
    public TbMonthVip findMonthVipById(int userId);

    //通过车牌号，修改用户信息
    public int alterUserByCarNumber(TbUser tbUser);

    //通过用户id，修改月缴用户记录表
    public int alterMonthVipById(TbMonthVip tbMonthVip);

    //通过车牌号，重置用户表时间
    public int resetTimeByCarNumber(String carNumber);

    //添加退费表信息
    public int addRefund(TbRefund tbRefund);
}
