package com.example.carpark.controller;


import com.example.carpark.javabean.*;
import com.example.carpark.service.AdminService;
import com.example.carpark.service.RevenueService;
import com.example.carpark.util.ApplicationContextHelper;
import com.example.carpark.util.MD5;
import com.example.carpark.util.ResponseUtils;
import com.example.carpark.util.ScheduledUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * 管理员控制类
 * 郭子淳
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Resource
    private RevenueService revenueService;

    @Resource
    private Diagis datagridResult;

    private char[] codeSequence = { 'A', '1','B', 'C', '2','D','3', 'E','4', 'F', '5','G','6', 'H', '7','I', '8','J',
            'K',   '9' ,'L', '1','M',  '2','N',  'P', '3', 'Q', '4', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z'};

    /**
     * 管理员登陆验证
     * @param param
     * param说明：param里面有admin_account、admin_pwd、captcha
     */
    @RequestMapping(value = "/adminLogin",produces = { "application/json;charset=UTF-8"})
    @ResponseBody
    public String adminLogin(@RequestParam Map<String,Object> param, HttpSession session)  {
        System.out.println("===============================管理员登陆=============================");
        String vcode = session.getAttribute("vcode").toString();//获取session上的验证码
        System.out.println("验证码："+vcode);
        if(vcode.equalsIgnoreCase(param.get("captcha").toString())){
            return adminService.adminLogin(param,session);//获取service层返回的信息
        }
        return "验证码错误";
    }

    /**
     *  获取菜单
     * @param session
     */
    @RequestMapping("/findMenu")
    @ResponseBody
    public List<TbMenu> findMenu(HttpSession session){
        System.out.println("==================查询角色菜单===================");
        TbAdmin tbAdmin = (TbAdmin) session.getAttribute("tbAdmin");//获取session上的管理员信息
        if(tbAdmin != null){
            List<TbMenu> parentMenuList = adminService.findMenu(tbAdmin);
            return parentMenuList;
        }
        return null;
    }

    /**
     * 验证管理是否登陆
     * @param session
     * @return
     */
    @RequestMapping("/findCurrentAdmin")
    @ResponseBody
    public TbAdmin findCurrentAdmin(HttpSession session){
        System.out.println("==================判断管理员是否登陆===================");
        return  (TbAdmin) session.getAttribute("tbAdmin");//获取session上的管理员信息
    }

    /**
     * 管理员退出
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/exit")
    public String exit(HttpSession session){
        System.out.println("=====================管理员退出=====================");
        session.setAttribute("tbAdmin",null);
        return "success";
    }

    /**
     * 根据条件分页查询菜单(page,limit,menuName)
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping("/findMenuById")
    public ResultDate findMenuById(@RequestParam Map<String,Object> param){
        System.out.println("===========================查询菜单列表=========================");
        Integer page = Integer.valueOf(param.get("page").toString());
        Integer limit = Integer.valueOf(param.get("limit").toString());
        page = (page - 1) * limit;//计算第几页
        param.put("page",page);
        param.put("limit",limit);
        return adminService.findMenuById(param);
    }

    /**
     * 获取验证码图片
     * @param session
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/CheckCodeServlet")
    public void CheckCodeServlet(HttpSession session, HttpServletResponse response) throws ServletException, IOException{
        System.out.println("=====================获取验证码=======================");
        int width = 75;
        int height = 37;
        Random random = new Random();
        //设置response头信息
        //禁止缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        //生成缓冲区image类
        BufferedImage image = new BufferedImage(width, height, 1);
        //产生image类的Graphics用于绘制操作
        Graphics g = image.getGraphics();
        //Graphics类的样式
        g.setColor(this.getColor(200, 250));
        g.setFont(new Font("Times New Roman",0,28));
        g.fillRect(0, 0, width, height);
        //绘制干扰线
        for(int i=0;i<40;i++){
            g.setColor(this.getColor(130, 200));
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(12);
            int y1 = random.nextInt(12);
            g.drawLine(x, y, x + x1, y + y1);
        }

        //绘制字符
        String strCode = "";
        for(int i=0;i<4;i++){
            String rand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            strCode = strCode + rand;
            g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
            g.drawString(rand, 15*i+6, 28);
        }
        //将字符保存到session中用于前端的验证
        session.setAttribute("vcode", strCode.toLowerCase());
        g.dispose();

        ImageIO.write(image, "JPEG", response.getOutputStream());
        response.getOutputStream().flush();
    }

    public  Color getColor(int fc,int bc){
        Random random = new Random();
        if(fc>255)
            fc = 255;
        if(bc>255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r,g,b);
    }

    /**
     *  根据父级ID查询菜单
     * @param parentId
     * @return
     */
    @ResponseBody
    @RequestMapping("/findSubmenu")
    public List<TbMenu> findSubmenu(Integer parentId){
        System.out.println("=================根据父级ID查询菜单================");
        return adminService.findSubmenu(parentId);
    }

    /**
     * 根据父级菜单ID、菜单名、url增加菜单
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping("/addMenu")
    public String addMenu(@RequestParam Map<String,Object> param){
        System.out.println("=================增加菜单===================");
        TbMenu tbMenu = ApplicationContextHelper.getBean(TbMenu.class);
        tbMenu.setMenuName(param.get("menuName").toString());
        tbMenu.setMenuUrl(param.get("menuUrl").toString());
        tbMenu.setParentId(Integer.valueOf(param.get("parentId").toString()));
        return adminService.addMenu(tbMenu) > 0?"success":"existed";//如果返回值大于1则添加成功，否则添加失败
    }

    /**
     * 更新菜单信息(menuId,menuName,menuUrl)
     * @param tbMenu
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateMenu")
    public String updateMenu(TbMenu tbMenu){
        System.out.println("=================更新菜单信息============");
        return adminService.updateMenu(tbMenu) > 0?"success":"error";
    }

    /**
     *  修改菜单父ID(menuId,parentId)
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateMenuParentId")
    public String updateMenuParentId(@RequestParam Map<String,Object> param){
        System.out.println("===============修改菜单父级菜单=============");
        return adminService.updateMenuParentId(param) > 0 ? "success":"error";
    }

    /**
     * 新增子菜单(menuName,menuUrl,parentId,use)
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping("/addSubmenu")
    public String addSubmenu(@RequestParam Map<String,Object> param){
        System.out.println("=================新增子菜单=================");
        return adminService.addSubmenu(param)>0?"success":"error";
    }

    /**
     * 删除菜单（menuId,parentId）
     * @param tbMenu
     * @return
     */
    @RequestMapping("/deleteMenu")
    @ResponseBody
    public String deleteMenu(TbMenu tbMenu){
        System.out.println("=============删除菜单==============");
        System.out.println("tbMenu===>"+tbMenu.toString());
        return adminService.deleteMenu(tbMenu) > 0 ? "success":"error";
    }

    /**
     * 分页查询角色表
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping("/findRoleByPage")
    public ResultDate findRoleByPage(@RequestParam Map<String,Object> param){
        System.out.println("===========================查询角色列表=========================");
        Integer page = Integer.valueOf(param.get("page").toString());
        Integer limit = Integer.valueOf(param.get("limit").toString());
        page = (page - 1) * limit;//计算第几页
        param.put("page",page);
        param.put("limit",limit);
        return adminService.findRoleByPage(param);
    }

    /**
     * 新增角色
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addRole")
    public String addRole(@RequestParam Map<String,Object> param){
        System.out.println("===============新增角色=================");
        return adminService.addRole(param);
    }

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteRole")
    public String deleteRole(Integer roleId){
        System.out.println("================删除角色===============");
        return adminService.deleteRole(roleId) > 0 ? "success":"error";
    }

    /**
     * 根据角色id查询角色菜单关系表
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping("/findRoleMenu")
    public List<TreeData> findRoleMenu(Integer roleId){
        System.out.println("=============查询角色菜单============");
        return adminService.findRoleMenu(roleId);
    }

    /**
     * 修改权限
     * @param treeDate
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateRoleMenu")
    public String updateRoleMenu(String treeDate,Integer roleId){
        System.out.println("============修改权限=============");
        Gson gson = new Gson();
        List<TreeData> treeDataList = gson.fromJson(treeDate,new TypeToken<List<TreeData>>() {}.getType());
        return adminService.updateRoleMenu(treeDataList,roleId) > 0 ? "success":"error";
    }

    /**
     * 更新角色表
     * @param tbRole
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateRole")
    public String updateRole(TbRole tbRole){
        System.out.println("==========更新角色============");
        return adminService.updateRole(tbRole);
    }

    @ResponseBody
    @RequestMapping("/findRevenueByPage")
    public ResultDate<TbRevenue> findRevenueByPage(@RequestParam Map<String,Object> param){
        System.out.println("======================查询收支明细表=========================");
        Integer page = Integer.valueOf(param.get("page").toString()),
        limit = Integer.valueOf(param.get("limit").toString());
        page = (page - 1) * limit;//计算第几页
        param.put("page",page);
        param.put("limit",limit);
        if(param.containsKey("time")&&!"".equals(param.get("time"))){
            String start = param.get("time").toString().substring(0,param.get("time").toString().indexOf("~")).trim(),
            end = param.get("time").toString().substring(param.get("time").toString().indexOf("~")+1).trim();
            param.put("start",start);
            param.put("end",end);
        }
        return revenueService.findRevenueByPage(param);
    }

    /**
     * 分页查询月缴产品
     * @return
     */
    @ResponseBody
    @RequestMapping("/findMonthParameter")
    public List<TbMonthChargeParameter> findMonthParameter(){
        System.out.println("===========查询月缴产品==========");
        return revenueService.findAllMonthParameter();
    }

    /**
     * 新增明细表
     * @param tbRevenue
     * @return
     */
    @ResponseBody
    @RequestMapping("/addRevenue")
    public String addRevenue(TbRevenue tbRevenue){
        System.out.println("==============添加收支明细表=============");
        return revenueService.addRevenue(tbRevenue);
    }

    /**
     * 根据id删除收支明细表
     * @param revenueId
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteRevenueById")
    public String deleteRevenueById(Integer revenueId){
        System.out.println("==================根据"+revenueId+"删除明细表=============");
        return revenueService.deleteRevenueById(revenueId)>0?"success":"error";
    }

    /**
     * 根据id查询收支表
     * @param revenueId
     * @return
     */
    @ResponseBody
    @RequestMapping("/findRevenueById")
    public TbRevenue findRevenueById(Integer revenueId){
        System.out.println("=================根据"+revenueId+"查询明细表=============");
        return revenueService.findRevenueById(revenueId);
    }


    @ResponseBody
    @RequestMapping("/updateRevenue")
    public String updateRevenue(TbRevenue tbRevenue){
        System.out.println("============修改明细表=============");
        return revenueService.updateRevenue(tbRevenue);
    }

    /**
     * 根据月份查询价格
     * @param month
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectPriceByMonth")
    public BigDecimal selectPriceByMonth(Integer month){
        return revenueService.selectPriceByMonth(month);
    }

    /**
     * 请求近七天统计
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryNearlySevenDays")
    public Map<String,Object> queryNearlySevenDays(){
        return revenueService.queryNearlySevenDays();
    }

    /**
     * 请求上个一个月统计
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryNearlyMonth")
    public Map<String,Object> queryNearlyMonth(){
        return revenueService.queryNearlyMonth();
    }

    /**
     * 请求今天按季度查询收入
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryCurYearBySeason")
    public Map<String,Object> queryCurYearBySeason(){
        return revenueService.queryCurYearBySeason();
    }

    /**
     * 请求今年按月份统计
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryCurYearByMonth")
    public Map<String,Object> queryCurYearByMonth(){
        return revenueService.queryCurYearByMonth();
    }

    /**
     * 查询当月收支
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryMonthRevenue")
    public Map<String,Object> queryMonthRevenue(){
        return revenueService.queryMonthRevenue();
    }

    /**
     * 获取停车场实时状态
     * @return
     */
    @ResponseBody
    @RequestMapping("/getData")
    public Map<String,Object> getData(){
        return adminService.getData();
    }

    /**
     * 查询参数表
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping("/findSysParamByPage")
    public ResultDate<TbSystemParameter> findSysParamByPage(@RequestParam Map<String,Object> param){
        System.out.println("=============分页查询参数表=============");
        Integer page = Integer.valueOf(param.get("page").toString()),
                limit = Integer.valueOf(param.get("limit").toString());
        page = (page - 1) * limit;//计算第几页
        param.put("page",page);
        param.put("limit",limit);
        return adminService.findSysParamByPage(param);
    }

    /**
     * 添加参数
     * @param tbSystemParameter
     * @return
     */
    @ResponseBody
    @RequestMapping("/addSysParam")
    public String addSysParam(TbSystemParameter tbSystemParameter){ return adminService.addSysParam(tbSystemParameter); }

    /**
     * 删除参数
     * @param parameterId
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteSysParam")
    public String deleteSysParam(Integer parameterId){return adminService.deleteSysParam(parameterId);}

    /**
     * 修改参数
     * @param tbSystemParameter
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateSysParam")
    public String updateSysParam(TbSystemParameter tbSystemParameter){
        return adminService.updateSysParam(tbSystemParameter);
    }

    /**
     * 管理员重置密码
     * @param oldPassword
     * @param newPassword
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/resetAdminPassword")
    public String resetAdminPassword(String oldPassword,String newPassword,HttpSession session){
        return adminService.resetAdminPassword(oldPassword,newPassword,session);
    }

    //日志查找 4.11
    @ResponseBody
    @RequestMapping("/table")
    public void table(HttpServletRequest request, HttpServletResponse response){
        HashMap<String,Object> condition = new HashMap<>();
        String name=request.getParameter("key");
        String type= request.getParameter("type");
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        System.out.println(type);
        if(null!=name&&!"".equals(name.trim())){
            condition.put("UNAME",name);
        }
        if(null!=type&&!"".equals(type.trim())){
            condition.put("TYPE",type);
        }
        int pageInt = Integer.valueOf(page);
        int limitInt = Integer.valueOf(limit);
        condition.put("pageInt",limitInt * (pageInt - 1));
        condition.put("limitInt",limitInt);
        int count=adminService.findLogCount(condition);
        datagridResult.setCode(0);
        datagridResult.setMsg("");
        datagridResult.setCount(count);
        List<TbLog> users=new ArrayList<>();
        users=adminService.findLog(condition);



	    datagridResult.setData(users);
        System.out.println("表格数据==="+toJson(datagridResult));
        ResponseUtils.outJson(response,toJson(datagridResult));
    }
    //转json(日志)
    protected String toJson(Diagis datagridResult){
        Gson gson=new Gson();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"code\":").append(datagridResult.getCode())
                .append(",\"msg\":\"").append(datagridResult.getMsg())
                .append("\",\"count\":").append(datagridResult.getCount())
                .append(",\"data\":[");
        if(datagridResult.getData().size()!=0){
            for(Object object : datagridResult.getData()){
                TbLog user = (TbLog) object;
                String sql=gson.toJson(user);
                System.out.println("对象转gson"+sql);
                sb.append(sql);
                sb.append(",");
            }
            sb.delete(sb.length() - 1, sb.length());
            sb.append("]}");
        }else{
            for(int i=0;i<8;i++){
                sb.delete(sb.length(),sb.length());
            }
            sb.append("}");
        }

        return sb.toString();
    }


    //林堂星_用户管理_收费员
    @RequestMapping("/test")
    public String test(){
        return "administration/jsp/admin/adminManagement";
    }
    @RequestMapping("/adminManagement")
    @ResponseBody
    public DataManagementResult adminManagement(String page, String limit, String uid, String startTime, String endTime, String stateId, String oId,String resignId,String resetId)
    {
        int pageInt = Integer.valueOf(page);
        int limitInt = Integer.valueOf(limit);
        List<TbCashier> list = adminService.findAll(uid,pageInt,limitInt,startTime,endTime);
        int count = adminService.findCount(uid,startTime,endTime);
        DataManagementResult dataManagementResult = new DataManagementResult();
        dataManagementResult.setCode(0);
        dataManagementResult.setMsg("");
        dataManagementResult.setCount(count);
        dataManagementResult.setData(list);
        return dataManagementResult;
    }

    @RequestMapping("/cashierForbidden")
    @ResponseBody
    public DataManagementResult cashierForbidden(String page, String limit, String uid, String startTime, String endTime, String stateId)
    {
        int pageInt = Integer.valueOf(page);
        int limitInt = Integer.valueOf(limit);
        int forbidden = adminService.forbiddenState(stateId);
        List<TbCashier> list = adminService.findAll(uid,pageInt,limitInt,startTime,endTime);
        int count = adminService.findCount(uid,startTime,endTime);
        DataManagementResult dataManagementResult = new DataManagementResult();
        dataManagementResult.setCode(0);
        dataManagementResult.setMsg("");
        dataManagementResult.setCount(count);
        dataManagementResult.setData(list);
        return dataManagementResult;
    }
	@RequestMapping("/cashierOpen")
	@ResponseBody
	public DataManagementResult cashierOpen(String page, String limit, String uid, String startTime, String endTime,String oId)
	{
		int pageInt = Integer.valueOf(page);
		int limitInt = Integer.valueOf(limit);
		int open = adminService.openState(oId);
		List<TbCashier> list = adminService.findAll(uid,pageInt,limitInt,startTime,endTime);
		int count = adminService.findCount(uid,startTime,endTime);
		DataManagementResult dataManagementResult = new DataManagementResult();
		dataManagementResult.setCode(0);
		dataManagementResult.setMsg("");
		dataManagementResult.setCount(count);
		dataManagementResult.setData(list);
		return dataManagementResult;
	}

	@RequestMapping("/cashierResign")
	@ResponseBody
	public DataManagementResult cashierResign(String page, String limit, String uid, String startTime, String endTime, String resignId)
	{
		int pageInt = Integer.valueOf(page);
		int limitInt = Integer.valueOf(limit);
		int resign = adminService.resignState(resignId);
		List<TbCashier> list = adminService.findAll(uid,pageInt,limitInt,startTime,endTime);
		int count = adminService.findCount(uid,startTime,endTime);
		DataManagementResult dataManagementResult = new DataManagementResult();
		dataManagementResult.setCode(0);
		dataManagementResult.setMsg("");
		dataManagementResult.setCount(count);
		dataManagementResult.setData(list);
		return dataManagementResult;
	}

	@RequestMapping("/cashierReset")
	@ResponseBody
	public DataManagementResult cashierReset(String page, String limit, String uid, String startTime, String endTime,String resetId)
	{
		int pageInt = Integer.valueOf(page);
		int limitInt = Integer.valueOf(limit);
		int rest = adminService.resetPwd(resetId);
		List<TbCashier> list = adminService.findAll(uid,pageInt,limitInt,startTime,endTime);
		int count = adminService.findCount(uid,startTime,endTime);
		DataManagementResult dataManagementResult = new DataManagementResult();
		dataManagementResult.setCode(0);
		dataManagementResult.setMsg("");
		dataManagementResult.setCount(count);
		dataManagementResult.setData(list);
		return dataManagementResult;
	}

    @RequestMapping("/addCashier")
    @ResponseBody
    public String addCashier(String cashierAccount, String cashierPwd, String cashierName,String cashierSex,String cashierPhone,String cashierAddress,String images)
    {
        long cashierState =1;
	    String cashierPwdMD5 = MD5.machining(cashierPwd);
        String flag = adminService.addCashier(cashierAccount,cashierPwdMD5,cashierName,cashierSex,cashierPhone,cashierAddress,cashierState,images);
        if (flag.equals("success")){
            return "新增收费员成功";
        }
        else {
            return "新增收费员失败";
        }
    }
    @RequestMapping("/updateCashier")
    @ResponseBody
    public TbCashier updateCashier(String uid, HttpSession session)
    {
        TbCashier tbCashier = adminService.updateCashier(uid);
        session.setAttribute("tbCashier", tbCashier);
        return tbCashier;
    }
    @RequestMapping("/toUpdateCashier")
    @ResponseBody
    public String toUpdateCashier(String uid,String cashierAccountUpdate,String cashierNameUpdate,String cashierPhoneUpdate,String cashierAddressUpdate,String images)
    {
        String flag = adminService.toUpdateCashier(uid,cashierAccountUpdate,cashierNameUpdate,cashierPhoneUpdate,cashierAddressUpdate,images);
        if (flag.equals("success")){
            return "修改成功";
        }
        else {
            return "修改失败";
        }
    }
    @RequestMapping(value = "/uploadHeadImgCashier", method = {RequestMethod.POST})
    @ResponseBody
    public Object headImgCashier(@RequestParam(value="file",required=false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String prefix="";
        String dateStr="";
        //保存上传
        OutputStream out = null;
        InputStream fileInput=null;
        String uuid = UUID.randomUUID()+"";
        String originalName = file.getOriginalFilename();
        prefix=originalName.substring(originalName.lastIndexOf(".")+1);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateStr = simpleDateFormat.format(date);
        System.out.println(request.getServletContext().getRealPath("/upload/"));
        String filepath = request.getServletContext().getRealPath("/upload/") + dateStr +"\\"+uuid+ "." + prefix;
        filepath = filepath.replace("\\", "/");
        try{
            if(file!=null){
                File files=new File(filepath);
                //打印查看上传路径
                System.out.println(filepath);
                if(!files.getParentFile().exists()){
                    files.getParentFile().mkdirs();
                }
                file.transferTo(files);
            }
        }catch (Exception e){
        }finally{
            try {
                if(out!=null){
                    out.close();
                }
                if(fileInput!=null){
                    fileInput.close();
                }
            } catch (IOException e) {
            }
        }
        Map<String,Object> map2=new HashMap<>();
        Map<String,Object> map=new HashMap<>();
        map.put("code",0);
        map.put("msg","");
        map.put("data",map2);
        map2.put("src","/upload/"+ dateStr+"/"+uuid+"." + prefix);
        return map;
    }

	//林堂星_用户管理_管理员
	@RequestMapping("/adminManagementDirector")
	@ResponseBody
	public DataManagementResult adminManagementDirector(String page, String limit, String uid, String startTime, String endTime, String stateId, String oId,String resignId,String resetId)
	{
		int pageInt = Integer.valueOf(page);
		int limitInt = Integer.valueOf(limit);
		int forbidden = adminService.forbiddenStateAdmin(stateId);
		int open = adminService.openStateAdmin(oId);
		int resign = adminService.resignStateAdmin(resignId);
		int rest = adminService.resetPwdAdmin(resetId);
		List<TbAdmin> list = adminService.findAllAdmin(uid,pageInt,limitInt,startTime,endTime);
		int count = adminService.findCountAdmin(uid,startTime,endTime);
		DataManagementResult dataManagementResult = new DataManagementResult();
		dataManagementResult.setCode(0);
		dataManagementResult.setMsg("");
		dataManagementResult.setCount(count);
		dataManagementResult.setData(list);
		return dataManagementResult;
	}

	@RequestMapping("/addAdmin")
	@ResponseBody
	public String addAdmin(String adminAccount, String adminPwd, String adminName,String adminSex,String adminPhone,String adminAddress,String images)
	{
		long adminState =1;
		String adminPwdMD5 = MD5.machining(adminPwd);
		String flag = adminService.addAdmin(adminAccount,adminPwdMD5,adminName,adminSex,adminPhone,adminAddress,adminState,images);
		if (flag.equals("success")){
			return "新增管理员成功";
		}
		else {
			return "新增收管理员失败";
		}
	}
	@RequestMapping("/updateAdmin")
	@ResponseBody
	public TbAdmin updateAdmin(String uid, HttpSession session)
	{
		TbAdmin tbAdmin = adminService.updateAdmin(uid);
		session.setAttribute("tbAdmin", tbAdmin);
		return tbAdmin;
	}
	@RequestMapping("/toUpdateAdmin")
	@ResponseBody
	public String toUpdateAdmin(String uid,String adminAccountUpdate,String adminNameUpdate,String adminPhoneUpdate,String adminAddressUpdate,String images)
	{
		String flag = adminService.toUpdateAdmin(uid,adminAccountUpdate,adminNameUpdate,adminPhoneUpdate,adminAddressUpdate,images);
		if (flag.equals("success")){
			return "修改成功";
		}
		else {
			return "修改失败";
		}
	}
    @RequestMapping(value = "/uploadHeadImg", method = {RequestMethod.POST})
    @ResponseBody
    public Object headImg(@RequestParam(value="file",required=false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String prefix="";
        String dateStr="";
        //保存上传
        OutputStream out = null;
        InputStream fileInput=null;
        String uuid = UUID.randomUUID()+"";
        String originalName = file.getOriginalFilename();
        prefix=originalName.substring(originalName.lastIndexOf(".")+1);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateStr = simpleDateFormat.format(date);
        System.out.println(request.getServletContext().getRealPath("/upload/"));
        String filepath = request.getServletContext().getRealPath("/upload/") + dateStr +"\\"+uuid+ "." + prefix;
        filepath = filepath.replace("\\", "/");
        try{
            if(file!=null){
                File files=new File(filepath);
                //打印查看上传路径
                System.out.println(filepath);
                if(!files.getParentFile().exists()){
                    files.getParentFile().mkdirs();
                }
                file.transferTo(files);
            }
        }catch (Exception e){
        }finally{
            try {
                if(out!=null){
                    out.close();
                }
                if(fileInput!=null){
                    fileInput.close();
                }
            } catch (IOException e) {
            }
        }
        Map<String,Object> map2=new HashMap<>();
        Map<String,Object> map=new HashMap<>();
        map.put("code",0);
        map.put("msg","");
        map.put("data",map2);
        map2.put("src","/upload/"+ dateStr+"/"+uuid+"." + prefix);
        return map;
    }

}