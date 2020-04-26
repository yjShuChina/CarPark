package com.example.carpark.service.impl;

import com.example.carpark.aoplog.Log;
import com.example.carpark.dao.AdminDao;
import com.example.carpark.dao.CarDao;
import com.example.carpark.dao.RevenueDao;
import com.example.carpark.dao.SystemParameterDao;
import com.example.carpark.javabean.TbAdmin;
import com.example.carpark.javabean.TbCashier;
import com.example.carpark.javabean.TbMenu;
import com.example.carpark.javabean.*;
import com.example.carpark.service.AdminService;
import com.example.carpark.util.ApplicationContextHelper;
import com.example.carpark.util.MD5;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 管理员service层
 * 郭子淳
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminDao adminDao;
    @Resource
    private CarDao carDao;
    @Resource
    private SystemParameterDao systemParameterDao;
    @Resource
    private RevenueDao revenueDao;

    /**
     * 将登陆账号密码拿去数据库验证
     * @param map
     * @param session
     * @return
     */
    @Override
    @Log(operationName = "管理员登录",operationType = "login")
    public String adminLogin(Map<String,Object> map, HttpSession session) {
        map.put("adminPwd",MD5.machining(map.get("adminPwd").toString()));//将管理员输入的密码转成MD5加密
        TbAdmin tbAdmin2 = adminDao.adminLogin(map);
        if(tbAdmin2 != null){
            if(tbAdmin2.getAdminState() == 1){
                session.setAttribute("tbAdmin",tbAdmin2);//将管理员信息放到session
                return "success";
            }
            return "您已被禁止登陆！";
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
	    Iterator<TbMenu> iterator = parentMenuList.iterator();//创建父级菜单迭代器
	    while (iterator.hasNext()) {
		    TbMenu tbMenu = iterator.next();
		    HashMap<String,Object> map = new HashMap<>();
		    map.put("roleId",tbAdmin.getRoleId());//将角色id添加到map
		    map.put("parentId",tbMenu.getMenuId());//将父级菜单id添加到map
		    List<TbMenu> submenuList = adminDao.findMenu(map);//查询该父级菜单下所有子菜单
		    Iterator<TbMenu> iterator2 = submenuList.iterator();//创建子级菜单迭代器
		    while (iterator2.hasNext()) {
			    TbMenu tbMenu2 = iterator2.next();
			    if(tbMenu2.getState() == 2){
				    iterator2.remove();
			    }
		    }
		    if(submenuList.size() < 1){//如果该子集菜单为null,则移除父级菜单,否则添加到父级菜单
			    iterator.remove();
		    }else {
			    tbMenu.setSubmenuList(submenuList);
		    }
	    }
	    System.out.println("处理后的菜单===>"+parentMenuList.toString());
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
     * 新增一级菜单
     * @param tbMenu
     * @return
     */
    @Override
    @Log(operationName = "新增一级菜单",operationType = "insert")
    public Integer addMenu(TbMenu tbMenu) {
        TbMenu tbMenu2 = adminDao.findMenuByName(tbMenu.getMenuName());//查询该菜单名是否存在
        if(tbMenu2 == null){
            return adminDao.addMenu(tbMenu);
        }
        return 0;
    }

    /**
     * 更新菜单信息
     * @param tbMenu
     * @return
     */
    @Override
    @Log(operationName = "编辑菜单",operationType = "update")
    public Integer updateMenu(TbMenu tbMenu) {
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
    @Log(operationName = "新增二级菜单",operationType = "insert")
    public Integer addSubmenu(Map<String, Object> map) {
        TbMenu tbMenu2 = adminDao.findMenuByName(map.get("menuName").toString());//查询该菜单名是否存在
        if(tbMenu2!=null){
            return 0;
        }
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
                if(tbRole.getRoleId() == 1){//如果是超级管理员，默认启用
                    rmmap.put("state",1);
                }else {
                    rmmap.put("state",state);
                }
                Integer j = adminDao.addRoleMenu(rmmap);
                if(j > 0){
                    k ++ ;
                }
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
    @Log(operationName = "删除菜单",operationType = "delete")
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
            return 0;
        }else {
            TbRoleMenu tbRoleMenu = ApplicationContextHelper.getBean(TbRoleMenu.class);
            tbRoleMenu.setMenuId(tbMenu.getMenuId());
            List<TbRoleMenu> roleMenuList = adminDao.findRoleMenuListById(tbRoleMenu);
            Integer i = adminDao.deleteRoleMenu(tbRoleMenu);
            if(i == roleMenuList.size()){
                return adminDao.deleteMenu((int) tbMenu.getMenuId());
            }
            return 0;
        }
    }

    /**
     * 分页查询角色表
     * @param map
     * @return
     */
    @Override
    public ResultDate<TbRole> findRoleByPage(Map<String, Object> map) {
        ResultDate<TbRole> rd = ApplicationContextHelper.getBean(ResultDate.class);
        rd.setCode(0);
        rd.setData(adminDao.findRoleByPage(map));
        rd.setCount(adminDao.findRoleListCount(map));
        rd.setMsg("");
        System.out.println(rd.toString());
        return rd;
    }

    /**
     *  新增角色以及给角色赋菜单,并初始化菜单状态
     * @param map
     * @return
     */
    @Override
    @Log(operationName = "新增角色",operationType = "insert")
    public String addRole(Map<String, Object> map) {
        Integer state = map.get("use").toString().equals("yes") ? 1 : 2;//是否开启所有菜单
        TbRole tbRole = adminDao.findRoleByName(map.get("role").toString());
        if(tbRole != null){
            return "角色名已存在";
        }
        Integer i = adminDao.addRole(map.get("role").toString());//添加角色表
        if(i > 0){
            Integer roleId = adminDao.selectMaxRoleId();//获取最新添加的角色ID
            List<TbMenu> menuList = adminDao.findAllSubmenu(0);//查询所有子菜单
            List<TbRoleMenu> roleMenuList = new ArrayList<>();
            for (TbMenu tbMenu: menuList) {//循环添加所有子菜单到角色菜单关系表
               TbRoleMenu tbRoleMenu = ApplicationContextHelper.getBean(TbRoleMenu.class);
               tbRoleMenu.setRoleId(roleId);
               tbRoleMenu.setMenuId(tbMenu.getMenuId());
               tbRoleMenu.setState(state);
               roleMenuList.add(tbRoleMenu);
            }
            Integer j = adminDao.addRoleMenu2(roleMenuList);
            if(j == menuList.size()){
                return "success";
            }
        }
        return "error";
    }

    /**
     * 删除角色以及其关系表
     * @param roleId
     * @return
     */
    @Override
    @Log(operationName = "删除角色",operationType = "delete")
    public Integer deleteRole(Integer roleId) {
        TbRoleMenu tbRoleMenu = ApplicationContextHelper.getBean(TbRoleMenu.class);
        tbRoleMenu.setRoleId(roleId);
        List<TbRoleMenu> roleMenuList = adminDao.findRoleMenuListById(tbRoleMenu);
        Integer i = adminDao.deleteRoleMenu(tbRoleMenu);
        if(i == roleMenuList.size()){
            return adminDao.deleteRole(roleId);
        }
        return 0;
    }

    /**
     * 根据角色id生成树形组件的数据
     * @param roleId
     * @return
     */
    @Override
    public List<TreeData> findRoleMenu(Integer roleId) {
        List<TreeData> treeDataList = new ArrayList<>();
        List<TbMenu> menuList = adminDao.findParentMenu(0);
        for (TbMenu tbMenu:menuList) {
            TreeData treeData = ApplicationContextHelper.getBean(TreeData.class);
            treeData.setId((int)tbMenu.getMenuId());
            treeData.setTitle(tbMenu.getMenuName());
            Map<String,Object> map = new HashMap<>();
            map.put("roleId",roleId);
            map.put("parentId",tbMenu.getMenuId());
            List<TbMenu> menuList2 = adminDao.findMenu(map);
            List<TreeData> treeDataList2 = new ArrayList<>();
            for (TbMenu tbMenu2:menuList2) {
                TreeData treeData2 = ApplicationContextHelper.getBean(TreeData.class);
                treeData2.setId((int) tbMenu2.getMenuId());
                treeData2.setTitle(tbMenu2.getMenuName());
                if(tbMenu2.getState() == 1){
                    treeData2.setChecked(true);
                }else {
                    treeData2.setChecked(false);
                }
                treeDataList2.add(treeData2);
            }
            treeData.setChildren(treeDataList2);
            treeDataList.add(treeData);
        }
        return treeDataList;
    }

    /**
     * 权限修改
     * @param list
     * @param roleId
     * @return
     */
    @Override
    @Log(operationName = "修改角色权限",operationType = "update")
    public Integer updateRoleMenu(List<TreeData> list, Integer roleId) {
        TbRoleMenu tbRoleMenu = ApplicationContextHelper.getBean(TbRoleMenu.class);
        tbRoleMenu.setRoleId(roleId);
        List<TbRoleMenu> roleMenuList = adminDao.findRoleMenuListById(tbRoleMenu);
        HashMap<String,Object> map = new HashMap<>();
        map.put("roleId",roleId);
        map.put("state",2);
        if(adminDao.resetAllMenu(map) == roleMenuList.size()){
            if(list.size() > 0){
                List<TbMenu> menuList = new ArrayList<>();
                for (TreeData t : list) {
                    for (TreeData t2:t.getChildren()) {
                        TbMenu tbMenu = ApplicationContextHelper.getBean(TbMenu.class);
                        tbMenu.setMenuId(t2.getId());
                        menuList.add(tbMenu);
                    }
                }
                map.put("list",menuList);
                map.put("state",1);
                return adminDao.resetMenuState(map);
            }
            return list.size();
        }
        return 0;
    }

    /**
     * 更新角色表
     * @param tbRole
     * @return
     */
    @Override
    @Log(operationName = "编辑角色",operationType = "update")
    public String updateRole(TbRole tbRole) {
        Integer i = adminDao.updateRole(tbRole);
        if(i > 0){
            return "success";
        }
        return "error";
    }


    //查找日志页数 4.11
    @Override
    public Integer findLogCount(HashMap<String,Object> condition)
    {
        return adminDao.findLogCount(condition);
    }
    //查找日志
    @Override
    public List<TbLog> findLog(HashMap<String,Object> condition)
    {
        return adminDao.findLog(condition);
    }

    /**
     * 监控数据
     * @return
     */
    @Override
    public Map<String, Object> getData() {
        Map<String,Object> map = new HashMap<>();
        map.put("restSpace",carDao.findParkSpacenum("1",""));
        map.put("totalSpace",carDao.findParkSpacenum("",""));
        map.put("todayIncome",revenueDao.selectTodayIncome());
        map.put("totalIncome",revenueDao.selectTotalIncome());
        map.put("todayUser",adminDao.selectTodayUser());
        map.put("totalUser",adminDao.selectTotalUser());
        return map;
    }

    /**
     * 分页查询系统参数表
     * @param param
     * @return
     */
    @Override
    public ResultDate<TbSystemParameter> findSysParamByPage(Map<String, Object> param) {
        ResultDate<TbSystemParameter> rd = ApplicationContextHelper.getBean(ResultDate.class);
        rd.setCode(0);
        rd.setMsg("");
        rd.setData(systemParameterDao.findSysParamByPage(param));
        rd.setCount(systemParameterDao.findSysParamCount(param));
        return rd;
    }

    /**
     * 新增系统参数
     * @param tbSystemParameter
     * @return
     */
    @Override
    @Log(operationName = "新增系统参数",operationType = "insert")
    public String addSysParam(TbSystemParameter tbSystemParameter) {
        if(systemParameterDao.selectByName(tbSystemParameter.getParameterName())!=null){
            return "exist";
        }
        return systemParameterDao.insert(tbSystemParameter) > 0 ? "success":"error";
    }

    /**
     * 删除系统参数
     * @param parameterId
     * @return
     */
    @Override
    @Log(operationName = "删除系统参数",operationType = "delete")
    public String deleteSysParam(Integer parameterId) {
        return systemParameterDao.deleteByPrimaryKey(parameterId) > 0 ? "success":"error";
    }

    /**
     * 更改系统参数
     * @param tbSystemParameter
     * @return
     */
    @Override
    @Log(operationName = "编辑系统参数",operationType = "update")
    public String updateSysParam(TbSystemParameter tbSystemParameter) {
        return systemParameterDao.updateByPrimaryKeySelective(tbSystemParameter) > 0 ? "success":"error";
    }

    /**
     * 管理员修改密码
     * @param oldPassword
     * @param newPassword
     * @param session
     * @return
     */
    @Override
    @Log(operationName = "管理员修改密码",operationType = "update")
    public String resetAdminPassword(String oldPassword, String newPassword,HttpSession session) {
        TbAdmin tbAdmin = (TbAdmin) session.getAttribute("tbAdmin");
        if(tbAdmin.getAdminPwd().equals(MD5.machining(oldPassword))){
            tbAdmin.setAdminPwd(MD5.machining(newPassword));
            if(adminDao.updateAdminPwd(tbAdmin) > 0){
                session.setAttribute("tbAdmin",tbAdmin);
                return "success";
            }
            return "修改时发生了错误";
        }
        return "原密码不正确";
    }

    /**
     *  林堂星——用户管理——收费员
     */
    @Override
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
    @Log(operationName = "收费员禁用",operationType = "update")
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
    @Log(operationName = "收费员启用",operationType = "update")
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
    @Log(operationName = "收费员离职",operationType = "update")
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
    @Log(operationName = "重置收费员密码",operationType = "update")
    public int resetPwd(String resetId)
    {
        int count=0;
        String pwdMD5;
        if (resetId!=null){
            pwdMD5=MD5.machining(adminDao.resetPwdAdminMD5());
            Map parameters=new HashMap<>();
            parameters. put( "cashierId" , resetId);
            parameters. put( "pwdMD5" , pwdMD5);
            count=adminDao.resetPwd(parameters);
        }else {
            count =2;
        }
        return count;
    }

    @Override
    public TbCashier updateCashier(String uid)
    {
        Map parameters=new HashMap<>();
        parameters. put( "cashierId" , uid);
        TbCashier tbCashier=adminDao.updateCashier(parameters);
        return tbCashier;
    }

    @Override
    @Log(operationName = "修改收费员信息",operationType = "update")
    public String toUpdateCashier(String uid, String cashierAccountUpdate, String cashierNameUpdate, String cashierPhoneUpdate, String cashierAddressUpdate,String images)
    {
        Map<String, String> parameters=new HashMap<>();
        parameters. put( "cashierAccount" , cashierAccountUpdate);
        parameters. put( "cashierName" , cashierNameUpdate);
        parameters. put( "cashierPhone" , cashierPhoneUpdate);
        parameters. put( "cashierAddress" , cashierAddressUpdate);
        parameters. put( "cashierId" , uid);
        parameters. put( "cashierHeadImg" , images);
        boolean flag = adminDao.toUpdateCashier(parameters);
        if (flag){
            return "success";
        }else {
            return "fail";
        }
    }

    @Override
    @Log(operationName = "新增收费员",operationType = "insert")
    public String addCashier(String cashierAccount, String cashierPwd, String cashierName, String cashierSex, String cashierPhone, String cashierAddress, long cashierState,String images)
    {
        Map<String, String> parameters=new HashMap<>();
        parameters. put( "cashierAccount" , cashierAccount);
        parameters. put( "cashierPwd" , cashierPwd);
        parameters. put( "cashierName" , cashierName);
        parameters. put( "cashierSex" , cashierSex);
        parameters. put( "cashierPhone" , cashierPhone);
        parameters. put( "cashierAddress" , cashierAddress);
        parameters. put( "cashierState" , String.valueOf(cashierState));
        parameters.put("cashierHeadImg",images);
        boolean flag = adminDao.addCashier(parameters);
        if (flag){
            return "success";
        }else {
            return "fail";
        }
    }

    /**
     *  林堂星——用户管理——管理员
     */
    @Override
    public List<TbAdmin> findAllAdmin(String adminName, int currentPage, int pageSize,String startTime,String endTime)
    {
        Map parameters=new HashMap<>();
        parameters. put( "adminName" , adminName);
        parameters. put( "currentPage" , (currentPage-1)*pageSize);
        parameters. put( "pageSize" , pageSize);
        parameters. put( "startTime" , startTime);
        parameters. put( "endTime" , endTime);
        List<TbAdmin> list=adminDao.findAllAdmin(parameters);
        return list;
    }
    @Override
    public int findCountAdmin(String adminName,String startTime,String endTime)
    {
        Map parameters=new HashMap<>();
        parameters. put( "adminName" , adminName);
        parameters. put( "startTime" , startTime);
        parameters. put( "endTime" , endTime);
        int count=adminDao.findCountAdmin(parameters);
        return count;
    }

    @Override
    @Log(operationName = "管理员禁用",operationType = "update")
    public int forbiddenStateAdmin(String stateId)
    {
        int count=0;
        if (stateId!=null){
            Map parameters=new HashMap<>();
            parameters. put( "adminId" , stateId);
            count=adminDao.forbiddenStateAdmin(parameters);
        }else {
            count =2;
        }
        return count;
    }
    @Override
    @Log(operationName = "管理员启用",operationType = "update")
    public int openStateAdmin(String stateId)
    {
        int count=0;
        if (stateId!=null){
            Map parameters=new HashMap<>();
            parameters. put( "adminId" , stateId);
            count=adminDao.openStateAdmin(parameters);
        }else {
            count =2;
        }
        return count;
    }

    @Override
    @Log(operationName = "管理员离职",operationType = "update")
    public int resignStateAdmin(String resignId)
    {
        int count=0;
        if (resignId!=null){
            Map parameters=new HashMap<>();
            parameters. put( "adminId" , resignId);
            count=adminDao.resignStateAdmin(parameters);
        }else {
            count =2;
        }
        return count;
    }

    @Override
    @Log(operationName = "重置管理员密码",operationType = "update")
    public int resetPwdAdmin(String resetId)
    {
        int count=0;
        String pwdMD5;
        if (resetId!=null){
            pwdMD5=MD5.machining(adminDao.resetPwdAdminMD5());
            Map parameters=new HashMap<>();
            parameters. put( "adminId" , resetId);
            parameters. put( "pwdMD5" , pwdMD5);
            count=adminDao.resetPwdAdmin(parameters);
        }else {
            count =2;
        }
        return count;
    }

    @Override
    public TbAdmin updateAdmin(String uid)
    {
        Map parameters=new HashMap<>();
        parameters. put( "adminId" , uid);
        TbAdmin tbAdmin=adminDao.updateAdmin(parameters);
        return tbAdmin;
    }

    @Override
    @Log(operationName = "修改管理员信息",operationType = "update")
    public String toUpdateAdmin(String uid, String adminAccountUpdate, String adminNameUpdate, String adminPhoneUpdate, String adminAddressUpdate,String images)
    {
        Map<String, String> parameters=new HashMap<>();
        parameters. put( "adminAccount" , adminAccountUpdate);
        parameters. put( "adminName" , adminNameUpdate);
        parameters. put( "adminPhone" , adminPhoneUpdate);
        parameters. put( "adminAddress" , adminAddressUpdate);
        parameters. put( "adminId" , uid);
        parameters. put( "adminHeadImg" , images);
        boolean flag = adminDao.toUpdateAdmin(parameters);
        if (flag){
            return "success";
        }else {
            return "fail";
        }
    }

    @Override
    @Log(operationName = "新增管理员",operationType = "insert")
    public String addAdmin(String adminAccount, String adminPwd, String adminName, String adminSex, String adminPhone, String adminAddress, long adminState,String images)
    {
        Map<String, String> parameters=new HashMap<>();
        parameters. put( "adminAccount" , adminAccount);
        parameters. put( "adminPwd" , adminPwd);
        parameters. put( "adminName" , adminName);
        parameters. put( "adminSex" , adminSex);
        parameters. put( "adminPhone" , adminPhone);
        parameters. put( "adminAddress" , adminAddress);
        parameters. put( "adminState" , String.valueOf(adminState));
        parameters.put("adminHeadImg",images);
        boolean flag = adminDao.addAdmin(parameters);
        if (flag){
            return "success";
        }else {
            return "fail";
        }
    }

}
