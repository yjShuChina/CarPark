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
    public void addUser(TbUser tbUser) {
        monthDao.addUser(tbUser);
    }

    @Override//添加月缴信息
    public void addMonthlyPayment(TbMonthVip tbMonthVip) {
        monthDao.addMonthlyPayment(tbMonthVip);
    }

    @Override//查询月缴产品表
    public List<TbMonthChargeParameter> findMonthCharge() {
        return monthDao.findMonthCharge();
    }

    @Override//通过月缴参数id，查询缴交月份
    public int findMonthById(int mcpId) {
        return monthDao.findMonthById(mcpId);
    }

    @Override//通过车牌号，查询用户id
    public int findIdByCarNumber(String carNumber) {
        return monthDao.findIdByCarNumber(carNumber);
    }

}
