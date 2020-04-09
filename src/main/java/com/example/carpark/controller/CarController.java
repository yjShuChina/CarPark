package com.example.carpark.controller;


import com.example.carpark.javabean.TbUser;
import com.example.carpark.service.AdminService;
import com.example.carpark.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;

/**
 * 车辆控制类
 */
@Controller
@RequestMapping("/gate")
public class CarController
{

    @Resource
    private CarService carService;

    /**
     * 车入库信息获取
     */
    @RequestMapping("/findusermsg")
    @ResponseBody
    public void findcarmsg(@RequestParam("fileaot") MultipartFile fileaot, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        System.out.println("图片路径");
        try
        {
            fileaot.transferTo(new File("D:/Test/" + fileaot.getOriginalFilename()));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("图片路径");
        String str = "D:/Test/" + fileaot.getOriginalFilename();
        System.out.println(str);
        String carnumber=carService.findcarnumber(str);
        TbUser tbUser=carService.findUsermsg(carnumber);
//        System.out.println(tbUser.getUserName());
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (tbUser!=null){
            request.getSession().setAttribute("carnumber",carnumber);
            request.getSession().setAttribute("time",dateTime.format(formatter));
            request.getSession().setAttribute("username",tbUser.getUserName());
            response.sendRedirect("/Carpark/url/gate?");
        }else {
            request.getSession().setAttribute("carnumber",carnumber);
            request.getSession().setAttribute("time",dateTime.format(formatter));
            response.sendRedirect("/Carpark/url/gate?");
        }

    }
    @RequestMapping("/imgurl")
    @ResponseBody
    public void imgurl(HttpServletRequest request, HttpServletResponse response){
        String url=request.getParameter("img");
        System.out.println(url);
        request.getSession().setAttribute("img",url);
    }

}