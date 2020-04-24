package com.example.carpark.service;


import com.example.carpark.javabean.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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

    //图片存蓄
    String uploadImage(MultipartFile file, String name);

    //收费规则数据查询接口
    String chargePrice();

    //收费规则修改
    int modifyChargePrice(TbChargerParameter tbChargerParameter);

    //收费规则添加
    int addChargePrice(TbChargerParameter tbChargerParameter);

    //收费规则删除
    int delChargePrice(TbChargerParameter[] tbChargerParameter);

    //场内信息查看
    String parkQuery(int page,int limit);

    //出场车辆信息查看
    String carExitQuery(int page,int limit);

    //进场车辆数据获取
    String gateMaxQuery();

    //收费员确认收款
    String confirmCollection(Map<String,String> map);

    //查询自助缴费记录
    List<TbTemporaryCarRecord> tbTemporaryCarRecordQuery(TbParkCarInfo tbParkCarInfo);

    String excelGenerate(HttpServletRequest request);
}
