package com.example.carpark.dao;

import com.example.carpark.javabean.TbAdmin;
import com.example.carpark.javabean.TbCashier;
import com.example.carpark.javabean.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员Dao
 * 郭子淳
 */
@Mapper
public interface AdminDao {
    /**
     * 管理员登陆验证
     * @param map
     * @return
     */
    TbAdmin adminLogin(Map<String,Object> map);

    /**
     * 查询所有父级菜单
     * @param parentId
     * @return
     */
    List<TbMenu> findParentMenu(Integer parentId);

    /**
     * 查询所有父级菜单部位给定值的菜单
     * @param parentId
     * @return
     */
    List<TbMenu> findAllSubmenu(Integer parentId);

    /**
     * 根据父级菜单id和角色id查询子菜单
     * @param map
     * @return
     */
    List<TbMenu> findMenu(Map<String,Object> map);


    /**
     *  林堂星——用户管理
     */
    List<TbCashier> findAll(Map<String, String> parameters);
    int findCount(Map<String, String> parameters);
    int forbiddenState(Map<String, String> parameters);
    int openState(Map<String, String> parameters);
	boolean addCashier(Map<String, String> parameters);
	int resignState(Map parameters);
    int resetPwd(Map parameters);
    TbCashier updateCashier(Map parameters);
    boolean toUpdateCashier(Map<String, String> parameters);
    /**
     * 根据父级ID\菜单名称\page\limit查询菜单
     * @param map
     * @return
     */
    List<TbMenu> findMenuByPage(Map<String,Object> map);

    /**
     * 根据父级ID\菜单名称查询菜单总数
     * @param map
     * @return
     */
    Integer findMenuCount(Map<String,Object> map);

    /**
     * 新增菜单
     * @param tbMenu
     * @return
     */
    Integer addMenu(TbMenu tbMenu);

    /**
     * 修改菜单信息
     * @param tbMenu
     * @return
     */
    Integer updateMenu(TbMenu tbMenu);

    /**
     * 根据菜单名称查询菜单
     * @param menuName
     * @return
     */
    TbMenu findMenuByName(String menuName);

    /**
     * 根据url查询菜单
     * @param menuUrl
     * @return
     */
    TbMenu findMenuByUrl(String menuUrl);

    /**
     *  更新二级菜单的一级菜单
     * @param map
     * @return
     */
    Integer updateMenuParentId(Map<String,Object> map);

    /**
     * 查询所有角色
     * @return
     */
    List<TbRole> findAllRole();

    /**
     * 新增角色菜单关系表
     * @param map
     * @return
     */
    Integer addRoleMenu(Map<String,Object> map);

    /**
     * 查询最新添加的菜单ID
     * @return
     */
    Integer findMenuMaxId();

    /**
     * 删除父级菜单
     * @param menuId
     * @return
     */
    Integer deleteMenu(Integer menuId);

    /**
     * 删除父级菜单下的子菜单
     * @param parentId
     * @return
     */
    Integer deleteSubmenu(Integer parentId);

    /**
     * 删除菜单角色关联表
     * @param tbRoleMenu
     * @return
     */
    Integer deleteRoleMenu(TbRoleMenu tbRoleMenu);

    /**
     * 分页查询角色表
     * @param map
     * @return
     */
    List<TbRole> findRoleByPage(Map<String,Object> map);

    /**
     * 查询角色表的总数
     * @param map
     * @return
     */
    Integer findRoleListCount(Map<String,Object> map);

    /**
     * 新增角色
     * @param role
     * @return
     */
    Integer addRole(String role);

    /**
     * 查询新增角色ID
     * @return
     */
    Integer selectMaxRoleId();

    /**
     *  批量添加角色菜单表
     * @param list
     * @return
     */
    Integer addRoleMenu2(List<TbRoleMenu> list);

    /**
     * 根据角色名查询角色表
     * @param role
     * @return
     */
    TbRole findRoleByName(String role);

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    Integer deleteRole(Integer roleId);

    /**
     *  查询角色菜单关系表
     * @param tbRoleMenu
     * @return
     */
    List<TbRoleMenu> findRoleMenuListById(TbRoleMenu tbRoleMenu);

    /**
     * 修改角色菜单状态
     * @return
     */
    Integer resetMenuState(Map<String,Object> map);

    /**
     * 修改该角色所有菜单ID
     * @param map
     * @return
     */
    Integer resetAllMenu(Map<String,Object> map);

    Integer updateRole(TbRole tbRole);

    //查找日志页数
    Integer findLogCount(HashMap<String,Object> condition);

    //查找日志信息
    List<TbLog> findLog(HashMap<String,Object> condition);



}
