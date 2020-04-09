package com.example.carpark.service.impl;

import com.example.carpark.dao.AdminDao;
import com.example.carpark.javabean.ResultDate;
import com.example.carpark.javabean.TbAdmin;
import com.example.carpark.javabean.TbMenu;
import com.example.carpark.service.AdminService;
import com.example.carpark.util.ApplicationContextHelper;
import com.example.carpark.util.MD5;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
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
        map.put("adminPwd",MD5.machining(map.get("adminPwd").toString()));//将管理员输入的密码转成MD5加密
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
     * 菜单查询service层
     * @param map
     * @return
     */
    @Override
    public ResultDate<TbMenu> findMenuById(Map<String,Object> map) {
        ResultDate<TbMenu> rd = ApplicationContextHelper.getBean("ResultDate",ResultDate.class);
        rd.setCode(0);
        rd.setData(adminDao.findMenuByPage(map));
        rd.setCount(adminDao.findMenuCount(map));
        rd.setMsg("");
        System.out.println(rd.toString());
        return rd;
    }


}
