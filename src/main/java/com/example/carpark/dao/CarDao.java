package com.example.carpark.dao;

import com.example.carpark.javabean.TbAdmin;
import com.example.carpark.javabean.TbParkCarInfo;
import com.example.carpark.javabean.TbUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

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
}
