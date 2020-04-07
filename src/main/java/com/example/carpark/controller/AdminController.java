package com.example.carpark.controller;


import com.example.carpark.javabean.TbAdmin;
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
import java.util.Map;
import java.util.Random;

/**
 * 管理员控制类
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    private Random random = new Random();

    /**
     * 管理员登陆验证
     * @param param
     * param说明：param里面有admin_account、admin_pwd、captcha
     */
    @RequestMapping("/adminLogin")
    @ResponseBody
    public String adminLogin(@RequestParam Map<String,Object> param, HttpSession session){
        System.out.println("===============================管理员登陆=============================");
        String vcode = session.getAttribute("vcode").toString();
        if(vcode.equalsIgnoreCase(param.get("captcha").toString())){
            String ret = adminService.adminLogin(param);
            return ret;
        }
        return "验证码错误";
    }

    /**
     * 获取验证码图片
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/CheckCodeServlet")
    public void CheckCodeServlet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        System.out.println("=====================获取验证码=======================");
        //内存图片对象(TYPE_INT_BGR 选择图片模式RGB模式)
        BufferedImage image = new BufferedImage(100, 35, BufferedImage.TYPE_INT_BGR);
        //得到画笔
        Graphics graphics = image.getGraphics();
        //画之前要设置颜色，设置画笔颜色
        graphics.setColor(new Color(236, 255, 253, 255));
        //设置字体类型、字体大小、字体样式　
        graphics.setFont(new Font("黑体", Font.BOLD, 23));
        //填充矩形区域（指定要画的区域设置区）
        graphics.fillRect(0, 0, 100, 35);
        //为了防止黑客软件通过扫描软件识别验证码。要在验证码图片上加干扰线
        //给两个点连一条线graphics.drawLine();
        for (int i = 0; i < 5; i++) {
            //颜色也要随机（设置每条线随机颜色）
            graphics.setColor(getRandomColor());
            int x1 = random.nextInt(100);
            int y1 = random.nextInt(35);
            int x2 = random.nextInt(100);
            int y2 = random.nextInt(35);
            graphics.drawLine(x1, y1, x2, y2);
        }

        //拼接4个验证码，画到图片上
        char[] arrays = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            //设置字符的颜色

            int index = random.nextInt(arrays.length);
            builder.append(arrays[index]);
        }
        //创建session对象将生成的验证码字符串以名字为checkCode保存在session中request.getSession().setAttribute("checkCode",builder.toString());
        //将4个字符画到图片上graphics.drawString(str ,x,y);一个字符一个字符画
        request.getSession().setAttribute("vcode", builder.toString());
        for (int i = 0; i < builder.toString().length(); i++) {
            graphics.setColor(getRandomColor());
            char item = builder.toString().charAt(i);
            graphics.drawString(item + "", 10 + (i * 20), 25);
        }

        //输出内存图片到输出流
        ImageIO.write(image, "jpg", response.getOutputStream());
    }

    private Color getRandomColor() {
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);
    }
}