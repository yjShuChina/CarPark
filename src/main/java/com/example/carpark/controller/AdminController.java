package com.example.carpark.controller;


import com.example.carpark.javabean.ResultDate;
import com.example.carpark.javabean.TbAdmin;
import com.example.carpark.javabean.TbMenu;
import com.example.carpark.service.AdminService;
import com.example.carpark.util.ApplicationContextHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 管理员控制类
 * 郭子淳
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    private char[] codeSequence = { 'A', '1','B', 'C', '2','D','3', 'E','4', 'F', '5','G','6', 'H', '7','I', '8','J',
            'K',   '9' ,'L', '1','M',  '2','N',  'P', '3', 'Q', '4', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z'};

    /**
     * 管理员登陆验证
     * @param param
     * param说明：param里面有admin_account、admin_pwd、captcha
     */
    @RequestMapping("/adminLogin")
    @ResponseBody
    public String adminLogin(@RequestParam Map<String,Object> param, HttpSession session){
        System.out.println("===============================管理员登陆=============================");
        String vcode = session.getAttribute("vcode").toString();//获取session上的验证码
        if(vcode.equalsIgnoreCase(param.get("captcha").toString())){
            String ret = adminService.adminLogin(param,session);//获取service层返回的信息
            return ret;
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
        int width = 63;
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
            g.drawString(rand, 13*i+6, 28);
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
        return adminService.addMenu(tbMenu) > 0?"增加成功":"菜单名已存在";//如果返回值大于1则添加成功，否则添加失败
    }

    /**
     * 更新菜单信息(menuId,menuName,menuUrl)
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateMenu")
    public String updateMenu(@RequestParam Map<String,Object> param){
        System.out.println("=================更新菜单信息============");
        return adminService.updateMenu(param) > 0?"success":"error";
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
}