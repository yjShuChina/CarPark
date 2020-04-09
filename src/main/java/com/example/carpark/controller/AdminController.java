package com.example.carpark.controller;


import com.example.carpark.javabean.TbAdmin;
import com.example.carpark.javabean.TbMenu;
import com.example.carpark.service.AdminService;
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
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
        session.setAttribute("tbAdmin",null);
        return "success";
    }

    /**
     * 获取验证码图片
     * @param session
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/CheckCodeServlet")
    public void CheckCodeServlet(HttpSession session, HttpServletResponse response)throws ServletException, IOException{
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
}