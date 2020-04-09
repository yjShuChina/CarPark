package com.example.carpark.service;


import com.example.carpark.javabean.TbCashier;
import org.springframework.web.multipart.MultipartFile;

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
    TbCashier chargeLogin(Map<String,Object> map);

    //车牌识别
    String findcarnumber(MultipartFile file);
}
