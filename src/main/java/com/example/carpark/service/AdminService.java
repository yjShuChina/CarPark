package com.example.carpark.service;

import com.example.carpark.javabean.TbAdmin;
import com.example.carpark.javabean.TbCashier;

import java.util.List;
import java.util.Map;

/**
 * 管理员service接口类
 */
public interface AdminService {
    /**
     *  管理员登陆
     * @param map
     * @return
     */
    String adminLogin(Map <String,Object> map);

    /**
     *  林堂星——用户管理
     */
    List<TbCashier> findAll(String userName, String star_time, String end_time, int pageInt, int limitInt);
    int findCount(String userName,String star_time,String end_time);
    int forbiddenState(String stateId);
    int openState(String stateId);
}
