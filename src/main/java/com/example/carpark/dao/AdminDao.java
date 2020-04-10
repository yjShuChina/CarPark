package com.example.carpark.dao;

import com.example.carpark.javabean.TbAdmin;
import com.example.carpark.javabean.TbMenu;
import com.example.carpark.javabean.TbRole;
import com.example.carpark.javabean.TbRoleMenu;
import org.apache.ibatis.annotations.Mapper;

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
     * 根据父级菜单id和角色id查询子菜单
     * @param map
     * @return
     */
    List<TbMenu> findMenu(Map<String,Object> map);

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
}
