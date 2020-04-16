package com.example.carpark.service;

import com.example.carpark.javabean.TbAdmin;
import com.example.carpark.javabean.TbCashier;
import com.example.carpark.javabean.TbMenu;
import com.example.carpark.aoplog.Log;
import com.example.carpark.javabean.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员service接口类
 * 郭子淳
 */
public interface AdminService {
    String adminLogin(Map <String,Object> map, HttpSession session);

    List<TbMenu> findMenu(TbAdmin tbAdmin);

    ResultDate<TbMenu> findMenuById(Map<String,Object> map);

    List<TbMenu> findSubmenu(Integer parentId);

    Integer addMenu(TbMenu tbMenu);

    Integer updateMenu(TbMenu tbMenu);

    Integer updateMenuParentId(Map<String,Object> map);

    Integer addSubmenu(Map<String,Object> map);

    Integer deleteMenu(TbMenu tbMenu);

    ResultDate findRoleByPage(Map<String,Object> map);

    String addRole(Map<String,Object> map);

    Integer deleteRole(Integer roleId);

    List<TreeData> findRoleMenu(Integer roleId);

    Integer updateRoleMenu(List<TreeData> list,Integer roleId);

    String updateRole(TbRole tbRole);

    Map<String,Object> getData();

    ResultDate<TbSystemParameter> findSysParamByPage(Map<String,Object> param);

    String addSysParam(TbSystemParameter tbSystemParameter);

    String deleteSysParam(Integer parameterId);

    String updateSysParam(TbSystemParameter tbSystemParameter);

    //4.11 hyh添加
    Integer findLogCount(HashMap<String, Object> condition);

    List<TbLog> findLog(HashMap<String, Object> condition);


    /**
     *  林堂星——用户管理
     */
    List<TbCashier> findAll(String userName, int pageInt, int limitInt,String startTime,String endTime);
    int findCount(String userName,String startTime,String endTime);
    int forbiddenState(String stateId);
    int openState(String stateId);
	String addCashier(String cashierAccount, String cashierPwd, String cashierName, String cashierSex, String cashierPhone, String cashierAddress, long cashierState);
    int resignState(String resignId);
	int resetPwd(String resetId);
    TbCashier updateCashier(String uid);
	String toUpdateCashier(String uid, String cashierAccountUpdate, String cashierPwdUpdate, String cashierNameUpdate, String cashierPhoneUpdate, String cashierAddressUpdate);
}
