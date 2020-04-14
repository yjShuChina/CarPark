package com.example.carpark.dao;

import com.example.carpark.javabean.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 收费员Dao
 */
@Mapper
public interface ChargeDao {
    //收费员登陆
    TbCashier chargeLogin(Map<String, Object> map);

    //白名单查询
    TbWhiteList whitelistQuery(@Param("carNumber") String carNumber);

    //场内车辆信息查询
    TbParkCarInfo carParkQuery(@Param("carNumber") String carNumber);

    //用户信息查询
    TbUser userQuery(@Param("carNumber") String carNumber);

    //收费规则数据查询接口
    List<TbChargerParameter> chargePrice();

    //收费规则修改
    int modifyChargePrice(TbChargerParameter tbChargerParameter);

    //收费规则增加
    int addChargePrice(TbChargerParameter tbChargerParameter);

    //收费规则删除
    int delChargePrice(@Param("id")String id);
}
