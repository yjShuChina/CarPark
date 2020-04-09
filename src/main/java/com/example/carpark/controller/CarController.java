package com.example.carpark.controller;


import com.example.carpark.javabean.TbParkCarInfo;
import com.example.carpark.javabean.TbUser;
import com.example.carpark.javabean.TbWhiteList;
import com.example.carpark.service.AdminService;
import com.example.carpark.service.CarService;
import com.example.carpark.service.ChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
import java.sql.Timestamp;
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
    /**
     * 路径跳转
     *
     * @param path
     * @return
     */
    @RequestMapping("/cn/{uri}")
    public String redirect(@PathVariable(value = "uri") String path) {
        return "/gate/jsp/" + path;
    }


    @Resource
    private CarService carService;


    /**
     * 车入库信息获取
     */
    @RequestMapping("/findusermsg")
    @ResponseBody
    public String findcarmsg(@RequestParam("file") MultipartFile fileaot, HttpServletRequest request, HttpServletResponse response) throws IOException
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
        TbWhiteList tbWhiteList=carService.findWhite(carnumber);
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        TbParkCarInfo tbParkCarInfo =new TbParkCarInfo();
        if(tbWhiteList!=null){
            tbParkCarInfo.setCarTime(Timestamp.valueOf(dateTime.format(formatter)));
            tbParkCarInfo.setCarNumber(carnumber);
            tbParkCarInfo.setCarIdentity("白名单车辆");
            int i = carService.CarIn(tbParkCarInfo);

            return carnumber+","+tbWhiteList.getUserName()+",白名单车辆,"+dateTime.format(formatter);
        }else if (tbUser!=null){
            tbParkCarInfo.setCarTime(Timestamp.valueOf(dateTime.format(formatter)));
            tbParkCarInfo.setCarNumber(carnumber);
            tbParkCarInfo.setCarIdentity("月卡车辆");
            int i = carService.CarIn(tbParkCarInfo);

            return carnumber+","+tbUser.getUserName()+",月卡车辆,"+dateTime.format(formatter);
//            request.getSession().setAttribute("carnumber",carnumber);
//            request.getSession().setAttribute("time",dateTime.format(formatter));
//            request.getSession().setAttribute("username",tbUser.getUserName());
//            response.sendRedirect("/Carpark/url/gate?");
        }else {
            tbParkCarInfo.setCarTime(Timestamp.valueOf(dateTime.format(formatter)));
            tbParkCarInfo.setCarNumber(carnumber);
            tbParkCarInfo.setCarIdentity("临时车辆");
            int i = carService.CarIn(tbParkCarInfo);

            return carnumber+","+"临时用户"+",临时车辆,"+dateTime.format(formatter);
        }

    }

}