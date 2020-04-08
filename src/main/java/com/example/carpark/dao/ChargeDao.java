package com.example.carpark.dao;

import com.example.carpark.javabean.TbAdmin;
import com.example.carpark.javabean.TbCashier;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 收费员Dao
 */
@Mapper
public interface ChargeDao {
    //收费员登陆
    public TbCashier chargeLogin(Map<String, Object> map);
}
