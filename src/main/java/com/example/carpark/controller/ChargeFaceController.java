package com.example.carpark.controller;

import com.example.carpark.javabean.TbAdmin;
import com.example.carpark.javabean.TbCashier;
import com.example.carpark.service.FaceService;
import com.example.carpark.util.GetToken;
import com.example.carpark.util.GsonUtils;
import com.example.carpark.util.HttpUtil;
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
    public Map<String, Object> addChargeFace(HttpServletRequest request) {

        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
        Map<String, Object> mapAdd = new HashMap<String, Object>();
        String chargeFace = request.getParameter("chargeFace");//图片转换的base64
        System.out.println("chargeFace=" + chargeFace);

        Map<String, String> mapCashier = (Map<String, String>) request.getSession().getAttribute("tbCashier");
        System.out.println("mapCashier=" + mapCashier.toString());
        String cashierAccount = mapCashier.get("cashierAccount");//获取session的账户
        try {
            boolean flag = faceSearch(chargeFace);//判断百度中是否存在该人脸
            String result = null;
            if (flag == false) {

                Map<String, Object> map = new HashMap<>();
                map.put("image", chargeFace);
                map.put("group_id", "cashier_face");//分组
                map.put("user_id", cashierAccount);//账户
                map.put("liveness_control", "NORMAL");
                map.put("image_type", "BASE64");
                map.put("quality_control", "LOW");

                String param = GsonUtils.toJson(map);

                // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
                String accessToken = GetToken.getAuth();

                result = HttpUtil.post(url, accessToken, "application/json", param);
                System.out.println("添加result=" + result);
            } else {
                result = faceUpdate(chargeFace, cashierAccount);
                System.out.println("更新result=" + result);
            }
            JSONObject jsonObject = JSONObject.fromObject(result);
            JSONObject fromObject = jsonObject.getJSONObject("result");
            String face_token = (String) fromObject.get("face_token");//得到百度返回是人脸
            System.out.println("添加人脸face_token=" + face_token);

            if (face_token != null && !"".equals(face_token)) {
                mapAdd.put("msg", "1");

            } else {
                mapAdd.put("msg", "2");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapAdd;
    }

    //人脸登录
    @RequestMapping("/chargeFaceLogin")
    @ResponseBody
    public String chargeFaceLogin(HttpServletRequest request, HttpServletResponse response) {

        String chargeFace = request.getParameter("chargeFace");//base64

        String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", chargeFace);
            map.put("liveness_control", "NORMAL");
            map.put("group_id_list", "cashier_face");
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = GetToken.getAuth();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);

            JSONObject fromObject = JSONObject.fromObject(result);
            JSONObject jsonObject = fromObject.getJSONObject("result");
            // 此时需要加个判断
            if (jsonObject.isNullObject()) {
                System.out.println("jsonObject 为空");
                return "无法识别面部!";
            }
            JSONObject jsonArray = jsonObject.getJSONArray("user_list").getJSONObject(0);
            String cashierAccount = (String) jsonArray.get("user_id");
            System.out.println("cashierAccount=" + cashierAccount);
            TbCashier tbCashier = faceService.findChargeByAccount(cashierAccount);
            if (tbCashier != null) {
                if (tbCashier.getCashierState() == 1) {
                    Map<String, String> mapLogin = new HashMap<>();
                    map.put("cashierAccount", cashierAccount);
                    map.put("name", tbCashier.getCashierName());

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                    String time = sdf.format(new Date());
                    map.put("time", time);
                    request.getSession().setAttribute("tbCashier", map);
                    return "验证成功";
                } else if (tbCashier.getCashierState() == 0) {
                    return "该账户已禁用";
                } else if (tbCashier.getCashierState() == 2) {
                    return "该管理员已离职";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "无法识别面部,请用账户密码登录!";
    }

    /**
     * 人脸搜索
     * 查看百度中是否存在当前人脸
     */
    public boolean faceSearch(String image) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", image);
            map.put("liveness_control", "NORMAL");
            map.put("group_id_list", "cashier_face");
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = GetToken.getAuth();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println("人脸搜索result=" + result);
            JSONObject jsonObject = JSONObject.fromObject(result);
            String error_msg = (String) jsonObject.get("error_msg");
            if (error_msg.equals("SUCCESS") && !"".equals(error_msg)){
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 人脸更新
     */
    public String faceUpdate(String image, String cashierAccount) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/update";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", image);
            map.put("group_id", "cashier_face");
            map.put("user_id", cashierAccount);
            map.put("liveness_control", "NORMAL");
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = GetToken.getAuth();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println("人脸更新方法result=" + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
