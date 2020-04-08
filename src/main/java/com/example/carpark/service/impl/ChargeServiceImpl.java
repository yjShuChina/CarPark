package com.example.carpark.service.impl;

import com.example.carpark.dao.AdminDao;
import com.example.carpark.dao.ChargeDao;
import com.example.carpark.javabean.TbCashier;
import com.example.carpark.service.ChargeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;


/*
*  收费员service类
* */
@Service
public class ChargeServiceImpl implements ChargeService {

    @Resource
    private ChargeDao chargeDao;


    /*
    * 收费员登录
    *
    *
    * */
    @Override
    public TbCashier chargeLogin(Map<String, Object> map) {

        return chargeDao.chargeLogin(map);
    }
}
