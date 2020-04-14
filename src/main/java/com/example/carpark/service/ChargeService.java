package com.example.carpark.service;


import com.example.carpark.javabean.PageBean;
import com.example.carpark.javabean.TbCashier;
import com.example.carpark.javabean.TbChargerParameter;
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

    //收费规则数据查询接口
    String chargePrice();

    //收费规则修改
    int modifyChargePrice(TbChargerParameter tbChargerParameter);

    //收费规则添加
    int addChargePrice(TbChargerParameter tbChargerParameter);

    //收费规则删除
    int delChargePrice(String id);
}
