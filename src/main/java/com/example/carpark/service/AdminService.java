package com.example.carpark.service;

import com.example.carpark.javabean.TbAdmin;
import com.example.carpark.javabean.TbMenu;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 管理员service接口类
 * 郭子淳
 */
public interface AdminService {
    String adminLogin(Map <String,Object> map, HttpSession session);

    List<TbMenu> findMenu(TbAdmin tbAdmin);
}
