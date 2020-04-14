package com.example.carpark.dao;

import com.example.carpark.javabean.TbAdmin;
import com.example.carpark.javabean.TbCashier;
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
     *  林堂星——用户管理
     */
    List<TbCashier> findAll(Map<String, String> parameters);
    int findCount(Map<String, String> parameters);
    int forbiddenState(Map<String, String> parameters);
    int openState(Map<String, String> parameters);
	boolean addCashier(Map<String, String> parameters);
	int resignState(Map parameters);
}
