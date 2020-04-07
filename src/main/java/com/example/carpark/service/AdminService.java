package com.example.carpark.service;

import com.example.carpark.javabean.TbAdmin;

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
}
