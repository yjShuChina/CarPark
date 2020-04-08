package com.example.carpark.dao;

import com.example.carpark.javabean.TbAdmin;
import com.example.carpark.javabean.TbMenu;
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
}
