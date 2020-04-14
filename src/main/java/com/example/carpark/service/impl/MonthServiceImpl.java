package com.example.carpark.service.impl;

import com.example.carpark.dao.MonthDao;
import com.example.carpark.javabean.*;
import com.example.carpark.service.MonthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MonthServiceImpl implements MonthService {

    @Resource
    private MonthDao monthDao;

    @Override//添加用户信息
    public int addUser(TbUser tbUser) {
       return monthDao.addUser(tbUser);
    }

    @Override//添加月缴信息
    public int addMonthlyPayment(TbMonthVip tbMonthVip) {
       return monthDao.addMonthlyPayment(tbMonthVip);
    }

    @Override//查询月缴产品表
    public List<TbMonthChargeParameter> findMonthCharge() {
        return monthDao.findMonthCharge();
    }

    @Override//通过月缴参数id，查询缴交月份表
    public TbMonthChargeParameter findMonthById(int mcpId) {
        return monthDao.findMonthById(mcpId);
    }

    @Override//通过车牌号，查询用户id
    public int findIdByCarNumber(String carNumber) {
        return monthDao.findIdByCarNumber(carNumber);
    }

    @Override//通过车牌号，查询车牌号是否为月缴VIP
    public int findCarNumber(String carNumber) {
        return monthDao.findCarNumber(carNumber);
    }

    @Override //通过车牌号，查询用户信息
    public TbUser findUserByCarNumber(String carNumber) {
        return monthDao.findUserByCarNumber(carNumber);
    }

    @Override//通过用户id，查询月缴用户记录信息
    public TbMonthVip findMonthVipById(int userId) {
        return monthDao.findMonthVipById(userId);
    }

    @Override//通过车牌号，修改用户信息
    public int alterUserByCarNumber(TbUser tbUser) {
       return monthDao.alterUserByCarNumber(tbUser);
    }

    @Override//通过用户id，修改月缴用户记录表
    public int alterMonthVipById(TbMonthVip tbMonthVip) {
        return monthDao.alterMonthVipById(tbMonthVip);
    }

    @Override//通过车牌号，重置用户表时间
    public int resetTimeByCarNumber(String carNumber) {
        return monthDao.resetTimeByCarNumber(carNumber);
    }

    @Override//添加退费表信息
    public int addRefund(TbRefund tbRefund) {
        return monthDao.addRefund(tbRefund);
    }

}
