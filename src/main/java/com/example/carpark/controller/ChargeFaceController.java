package com.example.carpark.controller;

import com.example.carpark.javabean.TbAdmin;
import com.example.carpark.javabean.TbCashier;
import com.example.carpark.service.FaceService;
import com.example.carpark.util.GetToken;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 收费员人脸识别控制类
 */
@Controller
@RequestMapping("/chargeFace")
public class ChargeFaceController {

    @Resource
    private FaceService faceService;

    /**
     * 路径跳转
     *
     * @param path
     * @return
     */
    @RequestMapping("/path/{uri}")
    public String redirect(@PathVariable(value = "uri") String path) {
        return "/charge/jsp/" + path;
    }


    //添加人脸
    @RequestMapping("/addChargeFace")
    @ResponseBody
    public Map<String, Object> addChargeFace(HttpServletRequest request, HttpSession session) {

        String chargeFace = request.getParameter("chargeFace");
        System.out.println("chargeFace=" + chargeFace);
        Map<String, String> mapCashier = (Map<String, String>) request.getSession().getAttribute("tbCashier");
        System.out.println("mapCashier=" + mapCashier.toString());
        String cashierAccount = mapCashier.get("cashierAccount");
        TbCashier tbCashier = faceService.findChargeByAccount(cashierAccount);
        byte[] bytes = chargeFace.getBytes();
        tbCashier.setCashierFace(bytes);
        Map<String, Object> map = new HashMap<String, Object>();
        int i = faceService.addChargeFace(tbCashier);
        if (i > 0) {
            map.put("msg", "1");

        } else {
            map.put("msg", "2");
        }
        return map;
    }

    //刚拍的照片和数据库的照片对比
    @RequestMapping("/chargeFaceLogin")
    @ResponseBody
    public String chargeFaceLogin(HttpServletRequest request, HttpServletResponse response) {

        String chargeFace = request.getParameter("chargeFace");
        List<TbCashier> tbCashierList = faceService.chargeLoginFace();
        String base64 = "";
        response.reset();
        for (int i = 0; i < tbCashierList.size(); i++) {
            System.out.println("数据库脸:"+tbCashierList.get(i).getCashierFace().length);
            if (tbCashierList.get(i).getCashierFace().length > 0) {
                base64 = new String(tbCashierList.get(i).getCashierFace());
                boolean result = getResult(chargeFace, base64);
                if (result) {
                    System.out.println("状态:" + tbCashierList.get(i).getCashierState());
                    if (tbCashierList.get(i).getCashierState() == 1) {
                        Map<String, String> map = new HashMap<>();
                        map.put("cashierAccount", tbCashierList.get(i).getCashierAccount());
                        map.put("name", tbCashierList.get(i).getCashierName());

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                        String time = sdf.format(new Date());
                        map.put("time", time);
                        request.getSession().setAttribute("tbCashier", map);
                        return "验证成功";
                    } else if (tbCashierList.get(i).getCashierState() == 0) {
                        return "该账户已禁用";
                    } else if (tbCashierList.get(i).getCashierState() == 2) {
                        return "该管理员已离职";
                    }
                }
            }
        }
        return "无法识别面部,请用账户密码登录!";
    }

    /**
     * 人脸识别 比对
     */
    public boolean getResult(String imStr1, String imgStr2) {

        String accessToken = GetToken.getAuth();
        boolean flag = false;
        BufferedReader br = null;
        String result = "";
        // 定义请求地址
        String mathUrl = "https://aip.baidubce.com/rest/2.0/face/v3/match";
        try {
            //页面抓拍到的人脸
            List<JSONObject> images = new ArrayList<>();
            JSONObject image1 = new JSONObject();
            image1.put("image", imStr1);
            image1.put("image_type", "BASE64");
            image1.put("face_type", "LIVE");
            image1.put("quality_control", "LOW");
            image1.put("liveness_control", "NORMAL");

            //数据库中人脸
            JSONObject image2 = new JSONObject();
            image2.put("image", imgStr2);
            image2.put("image_type", "BASE64");
            image2.put("face_type", "LIVE");
            image2.put("quality_control", "LOW");
            image2.put("liveness_control", "NORMAL");
            images.add(image1);
            images.add(image2);
            // 调用百度云 【人脸对比】接口
            String genrearlURL = mathUrl + "?access_token=" + accessToken;
            // 创建请求对象
            URL url = new URL(genrearlURL);
            // 打开请求链接
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            // 设置请求方法
            connection.setRequestMethod("POST");
            // 设置通用的请求属性
            connection.setRequestProperty("Content-Type",
                    "application/json");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            // 获得请求输出流对象
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            out.writeBytes(images.toString());
            // 刷新流
            out.flush();
            // 关闭流
            out.close();
            // 建立实际链接
            connection.connect();
            // 读取URL的响应
            br = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println(result);
        JSONObject fromObject = JSONObject.fromObject(result);

        JSONObject jsonArray = fromObject.getJSONObject("result");

        double resultList = jsonArray.getDouble("score");
        if (resultList >= 90) {
            flag = true;

        }
        return flag;
    }

}
