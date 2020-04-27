package com.example.carpark.controller;


import com.example.carpark.javabean.*;
import com.example.carpark.service.CarService;
import com.example.carpark.util.ResponseUtils;
import com.example.carpark.websocket.WebSocket;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static org.apache.tomcat.util.codec.binary.Base64.encodeBase64;


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
//        response.setHeader("Access-Control-Allow-Origin", "*");
        return "/gate/jsp/" + path;
    }


    @Resource
    private CarService carService;


    /**
     * 车入库信息获取
     */
    @RequestMapping("/findusermsg")
    @ResponseBody
    public String findcarmsg(@RequestParam("file") MultipartFile fileaot, HttpServletRequest request) throws IOException {
        String path = request.getSession().getServletContext().getRealPath("/upload");
        System.out.println("图片路径");
        SimpleDateFormat si = new SimpleDateFormat("yyyy-MM-dd hh.mm.ss");
        ///获得当前系统时间zd  年-月-日 时：分：秒版
        String time = si.format(new Date());
        //将时间拼接在文件名上权即可

        String fileName = fileaot.getOriginalFilename();  //获取文件名
        String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        try {
            fileaot.transferTo(new File(path + "/" + time + fileType));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("图片路径");
        String str = path + "/" + time + fileType;
        System.out.println(str);
        //车牌号
        String carnumber = carService.findcarnumber(str);
		TbParkSpace tbParkSpace =carService.findcarmsg(carnumber);
        if(carnumber=="NO")
        {
	        return "NOCAR";
        }else if(tbParkSpace!=null){
        	return  "HAVEING";
        }
        //查询用户信息（1、月卡 2、白名单）
        TbUser tbUser = carService.findUsermsg(carnumber);
        TbWhiteList tbWhiteList = carService.findWhite(carnumber);
        //自动选择车位
        List<String> PS2 = carService.findParkSpace("1");
        //时间
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (PS2.size() == 0) {//无车位
            return "NO";
        } else {
            String cps = PS2.get(0);

            TbParkCarInfo tbParkCarInfo = new TbParkCarInfo();

            if (tbWhiteList != null) {
                //添加停车信息
                tbParkCarInfo.setCarTime(Timestamp.valueOf(dateTime.format(formatter)));
                tbParkCarInfo.setCarNumber(carnumber);
                tbParkCarInfo.setCarIdentity("白名单车辆");
                tbParkCarInfo.setParkSpaceId(cps);
                tbParkCarInfo.setImgUrl(str);
                int i = carService.CarIn(tbParkCarInfo);
                //改变车位状态
                int c = carService.changestate("2", cps);
                //返回
				websocket();
                return carnumber + "," + tbWhiteList.getUserName() + ",白名单车辆,"
                        + dateTime.format(formatter) + "," + cps;
            } else if (tbUser != null) {
                tbParkCarInfo.setCarTime(Timestamp.valueOf(dateTime.format(formatter)));
                tbParkCarInfo.setCarNumber(carnumber);
                tbParkCarInfo.setCarIdentity("月卡车辆");
                tbParkCarInfo.setParkSpaceId(cps);
                tbParkCarInfo.setImgUrl(str);
                int i = carService.CarIn(tbParkCarInfo);

                //改变车位状态
                int c = carService.changestate("2", cps);
				websocket();
                return carnumber + "," + tbUser.getUserName() + ",月卡车辆," +
                        dateTime.format(formatter) + "," + cps;

            } else {
                tbParkCarInfo.setCarTime(Timestamp.valueOf(dateTime.format(formatter)));
                tbParkCarInfo.setCarNumber(carnumber);
                tbParkCarInfo.setCarIdentity("临时车辆");
                tbParkCarInfo.setParkSpaceId(cps);
                tbParkCarInfo.setImgUrl(str);
                int i = carService.CarIn(tbParkCarInfo);

                //改变车位状态
                int c = carService.changestate("2", cps);
				websocket();
                return carnumber + "," + "临时用户" + ",临时车辆,"
                        + dateTime.format(formatter) + "," + cps;
            }
        }

    }

    private void websocket() {
        try {
            if (WebSocket.electricSocketMap.get("charge") != null) {
                for (Session session : WebSocket.electricSocketMap.get("charge")) {
                    session.getBasicRemote().sendText("{\"type\":\"gate\"}");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


	@RequestMapping("/img")
	@ResponseBody
	public String img(HttpServletRequest request,HttpServletResponse response)  {
		String imgBase64 = "";
		try {
			File file = new File("D:\\Test\\null.jpg");
			byte[] content = new byte[(int) file.length()];
			FileInputStream finputstream = new FileInputStream(file);
			finputstream.read(content);
			finputstream.close();
			imgBase64 = new String(encodeBase64(content));
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
		return imgBase64;
	}
	//车位信息
	@RequestMapping("/findCpmsg")
	@ResponseBody
	public void findCpmsg(HttpServletRequest request,HttpServletResponse response)  {
		int Apsnum=carService.findParkSpacenum("1","A");
		int Aparking=carService.findParkSpacenum("2","A");
		int Bpsnum=carService.findParkSpacenum("1","B");
		int Bparking=carService.findParkSpacenum("2","B");
		int parkspase=carService.findParkSpacenum("1","");//未停车
		int allps=carService.findParkSpacenum("","");//所有车位

		request.getSession().setAttribute("Apsnum", Apsnum);
		request.getSession().setAttribute("Aparking", Aparking);
		request.getSession().setAttribute("Bpsnum", Bpsnum);
		request.getSession().setAttribute("Bparking", Bparking);
		request.getSession().setAttribute("parkspase", parkspase);
		request.getSession().setAttribute("allps", allps);

	}
	//已用停车位
	@RequestMapping("/findParking")
	@ResponseBody
	public void findParking(HttpServletResponse response) {
		List<String> PS2 = carService.findParkSpace("2");
		String[] array2 = new String[PS2.size()];
        PS2.toArray(array2);
		String [] car = array2;
		Gson g = new Gson();
		ResponseUtils.outJson(response, g.toJson(car));
	}
	//查找车辆信息
	@RequestMapping("/findcarmsg")
	@ResponseBody
	public String findcarmsg(HttpServletRequest request,HttpServletResponse response) {
		String carnum=request.getParameter("carnum");
		TbParkSpace tbParkSpace=carService.findcarmsg(carnum);

		if (tbParkSpace!=null){
			TbParkCarInfo tbParkCarInfo=carService.findmsg(carnum);
			request.getSession().setAttribute("Ctime", tbParkCarInfo.getCarTime().toString().split("\\.")[0]);
			request.getSession().setAttribute("Identity", tbParkCarInfo.getCarIdentity());
			request.getSession().setAttribute("Cnum", tbParkCarInfo.getCarNumber());
			request.getSession().setAttribute("Cps", tbParkCarInfo.getParkSpaceId());
			request.getSession().setAttribute("x", tbParkSpace.getX());
			request.getSession().setAttribute("y", tbParkSpace.getY());
			request.getSession().setAttribute("Area",'1');
			System.out.println(tbParkCarInfo.getCarTime().toString().split("\\.")[0]);
			return "yes";
		}else {
			return "no";
		}
	}
	@RequestMapping("/saveArea")
	@ResponseBody
	public String saveArea(HttpServletRequest request,HttpServletResponse response) {
//    	System.out.println("设备号以保存");
		String area=request.getParameter("area");
		request.getSession().setAttribute("Area",area);
		return "yes";
	}
	@RequestMapping("/machinepwd")
	@ResponseBody
	public String machinepwd(HttpServletRequest request,HttpServletResponse response) {
		String area=request.getParameter("pwd");
		TbSystemParameter tbSystemParameter=carService.machinepwd(area,"设备密码");
		if(tbSystemParameter==null){
			return "no";
		}
		return "yes";
	}

	@RequestMapping("/remove")
	@ResponseBody
	public void msgremove(HttpServletRequest request,HttpServletResponse response) {
		request.getSession().removeAttribute("x");
		request.getSession().removeAttribute("y");
	}

}