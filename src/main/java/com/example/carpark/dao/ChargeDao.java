package com.example.carpark.dao;

import com.example.carpark.javabean.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

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
    int delChargePrice(List<TbChargerParameter> tbChargerParameters);

    //白名单总数查询
    int queryWhiteListCount();

    //白名单表查询
    List<TbWhiteList> queryWhiteList(RowBounds rowBounds);

    //白名单修改
    int modifyWhiteList(TbWhiteList tbWhiteList);

    //白名单添加
    int addWhiteList(TbWhiteList tbWhiteList);

    //白名单删除
    int delWhiteList(List<TbWhiteList> tbWhiteLists);

    //场内车辆总数
    int parkQueryCount();

    //场内所有车辆信息查询
    List<TbParkCarInfo> parkQuery(RowBounds rowBounds);

    //出场车辆总数
    int carExitQueryCount();

    //出场车辆信息查询
    List<TbTotalCarExit> carExitQuery(Map<String,Integer> map);

    //最新进场车辆查询
    TbParkCarInfo gateMaxQuery();

    //查询自助缴费记录
    List<TbTemporaryCarRecord> tbTemporaryCarRecordQuery(TbParkCarInfo tbParkCarInfo);

    //当前班次车辆出场记录
    int addTbCurrentCarExit(Map<String,String> map);

    //出场记录总表
    int addTbTotalCarExit(Map<String,String> map);

    //删除场内信息记录
    int delTbParkCarInfo(Map<String,String> map);

    //当前班次车辆出场记录查询
    List<TbCurrentCarExit> tbCurrentCarExitQuery();

    //当前班次车辆出场记录清空
    int delTbCurrentCarExit();

    //收费员登录时间记录
    int addTbCashierShifts(TbCashierShifts tbCashierShifts);

    //收费员查询首次登录时间
    List<TbCashierShifts> tbCashierShiftsQuery();

    //日结单查看
    List<TbTotalCarExit> settlementQuery(Map<String,String> map);
}
