package com.example.carpark.dao;

import com.example.carpark.javabean.TbAdmin;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 管理员Dao
 */
@Mapper
public interface AdminDao {
    //管理员登陆
    TbAdmin adminLogin(Map<String,Object> map);
}
