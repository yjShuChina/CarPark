package com.example.carpark.service.impl;

import com.example.carpark.dao.AdminDao;
import com.example.carpark.dao.ChargeDao;
import com.example.carpark.javabean.TbCashier;
import com.example.carpark.javabean.TbParkCarInfo;
import com.example.carpark.javabean.TbUser;
import com.example.carpark.javabean.TbWhiteList;
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
     * */
    @Override
    public TbCashier chargeLogin(Map<String, Object> map) {

        return chargeDao.chargeLogin(map);
    }

    //收费规则计算
    public int chargeCalculation(String carNumber) {

        //白名单查询
        TbWhiteList tbWhiteList = chargeDao.whitelistQuery(carNumber);
        if (tbWhiteList != null) {
            return 0;
        }

        //车辆场内信息查询
        TbParkCarInfo tbParkCarInfo = chargeDao.carParkQuery(carNumber);

        //月卡查询
        TbUser tbUser = chargeDao.userQuery(carNumber);



        return 0;
    }
}
