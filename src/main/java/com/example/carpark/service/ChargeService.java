package com.example.carpark.service;


import com.example.carpark.javabean.TbCashier;

import java.util.Map;

/**
 * 收费员service接口类
 */
public interface ChargeService {

    /**
     *  收费员登陆
     * @param map
     * @return
     */
    public TbCashier chargeLogin(Map<String,Object> map);
}
