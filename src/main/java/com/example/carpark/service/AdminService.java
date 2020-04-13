package com.example.carpark.service;

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

    Integer updateMenu(Map<String,Object> param);

    Integer updateMenuParentId(Map<String,Object> map);

    Integer addSubmenu(Map<String,Object> map);

    Integer deleteMenu(TbMenu tbMenu);

    ResultDate findRoleByPage(Map<String,Object> map);

    String addRole(Map<String,Object> map);

    Integer deleteRole(Integer roleId);

    List<TreeData> findRoleMenu(Integer roleId);

    Integer updateRoleMenu(List<TreeData> list,Integer roleId);

    String updateRole(TbRole tbRole);

    //4.11 hyh添加
    Integer findLogCount(HashMap<String, Object> condition);

    List<TbLog> findLog(HashMap<String, Object> condition);
}
