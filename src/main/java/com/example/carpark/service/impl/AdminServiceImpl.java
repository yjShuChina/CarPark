package com.example.carpark.service.impl;

import com.example.carpark.dao.AdminDao;
import com.example.carpark.javabean.TbAdmin;
import com.example.carpark.javabean.TbCashier;
import com.example.carpark.javabean.TbMenu;
import com.example.carpark.service.AdminService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员service层
 * 郭子淳
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminDao adminDao;

    /**
     * 将登陆账号密码拿去数据库验证
     * @param map
     * @param session
     * @return
     */
    @Override
    public String adminLogin(Map<String,Object> map, HttpSession session) {
        TbAdmin tbAdmin2 = adminDao.adminLogin(map);
        if(tbAdmin2 != null){
            session.setAttribute("tbAdmin",tbAdmin2);//将管理员信息放到session
            return "验证成功";
        }
        return "账号或密码错误";
    }

    /**
     * 根据角色id查询菜单
     * @param tbAdmin
     * @return
     */
    @Override
    public List<TbMenu> findMenu(TbAdmin tbAdmin) {
        List<TbMenu> parentMenuList = adminDao.findParentMenu(0);//查询父级菜单
        for (int i = 0;i < parentMenuList.size();i++)//循环遍历父级菜单
        {
            HashMap<String,Object> map = new HashMap<>();
            map.put("roleId",tbAdmin.getRoleId());//将角色id添加到map
            map.put("parentId",parentMenuList.get(i).getMenuId());//将父级菜单id添加到map
            List<TbMenu> submenuList = adminDao.findMenu(map);//查询该父级菜单下所有子菜单
            for (int j = 0; j < submenuList.size(); j++) {//遍历子菜单集
                if(submenuList.get(j).getState() == 2)//如果状态为2,则移除
                {
                    submenuList.remove(j);
                }
            }
            if(submenuList.size()==0)//如果该子集菜单为null,则移除父级菜单,否则添加到父级菜单
            {
                parentMenuList.remove(parentMenuList.get(i));
            }else
            {
                parentMenuList.get(i).setSubmenuList(submenuList);
            }
        }
        System.out.println(parentMenuList.toString());
        return parentMenuList;
    }


    /**
     *  林堂星——用户管理
     */
    @Override
    @Transactional
    public List<TbCashier> findAll(String cashierName, int currentPage, int pageSize,String startTime,String endTime)
    {
        Map parameters=new HashMap<>();
        parameters. put( "cashierName" , cashierName);
        parameters. put( "currentPage" , (currentPage-1)*pageSize);
        parameters. put( "pageSize" , pageSize);
        parameters. put( "startTime" , startTime);
        parameters. put( "endTime" , endTime);
        List<TbCashier> list=adminDao.findAll(parameters);
        return list;
    }
    @Override
    @Transactional
    public int findCount(String cashierName,String startTime,String endTime)
    {
        Map parameters=new HashMap<>();
        parameters. put( "cashierName" , cashierName);
        parameters. put( "startTime" , startTime);
        parameters. put( "endTime" , endTime);
        int count=adminDao.findCount(parameters);
        return count;
    }
    @Override
    @Transactional
    public int forbiddenState(String stateId)
    {
        int count=0;
        if (stateId!=null){
            Map parameters=new HashMap<>();
            parameters. put( "cashierId" , stateId);
            count=adminDao.forbiddenState(parameters);
        }else {
            count =2;
        }
        return count;
    }
    @Override
    @Transactional
    public int openState(String stateId)
    {
        int count=0;
        if (stateId!=null){
            Map parameters=new HashMap<>();
            parameters. put( "cashierId" , stateId);
            count=adminDao.openState(parameters);
        }else {
            count =2;
        }
        return count;
    }

    @Override
    @Transactional
    public int resignState(String resignId)
    {
        int count=0;
        if (resignId!=null){
            Map parameters=new HashMap<>();
            parameters. put( "cashierId" , resignId);
            count=adminDao.resignState(parameters);
        }else {
            count =2;
        }
        return count;
    }

    @Override
    @Transactional
    public String addCashier(String cashierAccount, String cashierPwd, String cashierName, String cashierSex, String cashierPhone, String cashierAddress, long cashierState)
    {
        Map<String, String> parameters=new HashMap<>();
        parameters. put( "cashierAccount" , cashierAccount);
        parameters. put( "cashierPwd" , cashierPwd);
        parameters. put( "cashierName" , cashierName);
        parameters. put( "cashierSex" , cashierSex);
        parameters. put( "cashierPhone" , cashierPhone);
        parameters. put( "cashierAddress" , cashierAddress);
        parameters. put( "cashierState" , String.valueOf(cashierState));
        boolean flag = adminDao.addCashier(parameters);
        if (flag){
            return "success";
        }else {
            return "fail";
        }
    }



}
