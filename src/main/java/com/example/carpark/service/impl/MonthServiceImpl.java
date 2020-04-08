package com.example.carpark.service.impl;

import com.example.carpark.dao.MonthDao;
import com.example.carpark.javabean.*;
import com.example.carpark.service.MonthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
