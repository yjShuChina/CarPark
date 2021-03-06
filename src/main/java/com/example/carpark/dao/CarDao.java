package com.example.carpark.dao;

import com.example.carpark.javabean.TbParkCarInfo;
import com.example.carpark.javabean.TbParkSpace;
import com.example.carpark.javabean.TbSystemParameter;
import com.example.carpark.javabean.TbUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 车辆Dao
 */
@Mapper
public interface CarDao
{
    //识别车牌（用户）
    TbUser findUsermsg(String carnumber);
    //添加入库信息
    int CarIn(TbParkCarInfo tbParkCarInfo);
    //找车位
    List<String> findParkSpace(String state);
    //车位数量
    Integer findParkSpacenum(String state,String area);
    //车位信息
    TbParkSpace findcarmsg(String carnum);
    TbParkCarInfo findmsg(String carnum);
    //改变车位属性
    Integer changestate(String state,String ps);
    //查找设备密码
    TbSystemParameter machinepwd(String pwd,String name);
}
