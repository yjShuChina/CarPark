package com.example.carpark.service.impl;

import com.example.carpark.dao.AdminDao;
import com.example.carpark.javabean.*;
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

    /**
     * 根据父级ID查询菜单
     * @param parentId
     * @return
     */
    @Override
    public List<TbMenu> findSubmenu(Integer parentId) {
        return adminDao.findParentMenu(parentId);
    }

    /**
     * 新增菜单
     * @param tbMenu
     * @return
     */
    @Override
    public Integer addMenu(TbMenu tbMenu) {
        TbMenu tbMenu2 = adminDao.findMenuByName(tbMenu.getMenuName());//查询该菜单名是否存在
        if(tbMenu2 == null){
            return adminDao.addMenu(tbMenu);
        }
        return 0;
    }

    /**
     * 更新菜单信息
     * @param param
     * @return
     */
    @Override
    public Integer updateMenu(Map<String,Object> param) {
        TbMenu tbMenu = ApplicationContextHelper.getBean(TbMenu.class);
        tbMenu.setMenuId(Integer.valueOf(param.get("menuId").toString()));
        if(param.containsKey("menuName")){
            TbMenu tbMenu2 = adminDao.findMenuByName(param.get("menuName").toString()) ;//判断菜单名是否已经存在
            if(tbMenu2 != null){
                return 0;
            }
            tbMenu.setMenuName(param.get("menuName").toString());
        }
        if(param.containsKey("menuUrl")){
            TbMenu tbMenu2 = adminDao.findMenuByUrl(param.get("menuUrl").toString()) ;//判断菜单路径是否存在
            if(tbMenu2 != null){
                return 0;
            }
            tbMenu.setMenuName(param.get("menuUrl").toString());
        }
        return adminDao.updateMenu(tbMenu);
    }

    @Override
    public Integer updateMenuParentId(Map<String, Object> map) {
        return adminDao.updateMenuParentId(map);
    }

    /**
     * 新增菜单，并判定管理是否给所有角色启用新菜单
     * @param map
     * @return
     */
    @Override
    public Integer addSubmenu(Map<String, Object> map) {
        TbMenu tbMenu = ApplicationContextHelper.getBean(TbMenu.class);
        tbMenu.setMenuName(map.get("menuName").toString());
        tbMenu.setMenuUrl(map.get("menuUrl").toString());
        tbMenu.setParentId(Integer.valueOf(map.get("parentId").toString()));
        Integer i = adminDao.addMenu(tbMenu),k = 0;//k用于计算是否关系表内所有角色都增加了
        if(i > 0){
            Integer menuId = adminDao.findMenuMaxId();//查询最新增加的菜单ID
            Integer state = map.get("use").toString().equals("yes") ? 1 : 2; //判断管理员选是/否
            List<TbRole> roleList = adminDao.findAllRole();
            for (TbRole tbRole : roleList) {
                Map<String,Object> rmmap = new HashMap<>();
                rmmap.put("menuId",menuId);
                rmmap.put("roleId", tbRole.getRoleId());
                rmmap.put("state",state);
                Integer j = adminDao.addRoleMenu(rmmap);
                k ++ ;
            }
            if(k == roleList.size()){
                return k;
            }
        }
        return 0;
    }

    /**
     * 删除菜单以及菜单角色关联表
     * @param tbMenu
     * @return
     */
    @Override
    public Integer deleteMenu(TbMenu tbMenu) {
        if(tbMenu.getParentId() == 0){//判断是否是父级菜单
            List<TbMenu> menuList = adminDao.findParentMenu((int) tbMenu.getMenuId());//查询该父级菜单下所有子菜单
            Integer k = 0;//计算删除字段数
            for (TbMenu tbMenu2:menuList) {
                TbRoleMenu tbRoleMenu = ApplicationContextHelper.getBean(TbRoleMenu.class);
                tbRoleMenu.setMenuId(tbMenu2.getMenuId());
                Integer i = adminDao.deleteRoleMenu(tbRoleMenu);//删除菜单角色关系表
                if(i > 0){
                    k++;
                }
            }
            if(k == menuList.size()){
                Integer j = adminDao.deleteSubmenu((int) tbMenu.getMenuId());//删除菜单下的子菜单
                if(j == menuList.size()){
                    return adminDao.deleteMenu((int) tbMenu.getMenuId());//删除菜单
                }
            }
        }else {
            TbRoleMenu tbRoleMenu = ApplicationContextHelper.getBean(TbRoleMenu.class);
            tbRoleMenu.setMenuId(tbMenu.getMenuId());
            Integer i = adminDao.deleteRoleMenu(tbRoleMenu);
            if(i >0){
                return adminDao.deleteMenu((int) tbMenu.getMenuId());
            }
        }
        return 0;
    }

}
