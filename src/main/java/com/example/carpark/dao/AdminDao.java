package com.example.carpark.dao;

import com.example.carpark.javabean.TbAdmin;
import com.example.carpark.javabean.TbCashier;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 管理员Dao
 */
@Mapper
public interface AdminDao {
    //管理员登陆
    TbAdmin adminLogin(Map<String,Object> map);

    /**
     *  林堂星——用户管理
     */
    List<TbCashier> findAll(Map<String, String> parameters);
    int findCount(Map<String, String> parameters);
    int forbiddenState(Map<String, String> parameters);
    int openState(Map<String, String> parameters);
}
