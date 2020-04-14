package com.example.carpark.service;

import com.example.carpark.javabean.TbAdmin;
import com.example.carpark.javabean.TbCashier;
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

    /**
     *  林堂星——用户管理
     */
    List<TbCashier> findAll(String userName, int pageInt, int limitInt,String startTime,String endTime);
    int findCount(String userName,String startTime,String endTime);
    int forbiddenState(String stateId);
    int openState(String stateId);
	String addCashier(String cashierAccount, String cashierPwd, String cashierName, String cashierSex, String cashierPhone, String cashierAddress, long cashierState);
    int resignState(String resignId);
}
