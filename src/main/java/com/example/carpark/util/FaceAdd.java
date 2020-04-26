package com.example.carpark.util;

import com.alipay.api.internal.parser.json.ObjectJsonParser;
import com.google.gson.Gson;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 人脸注册测试类
 */
public class FaceAdd {

    public static String add() throws IOException {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
        byte[] bytes1 = FileUtil.readFileByBytes("D:\\photo\\pan1.png");
        String image1 = Base64Util.encode(bytes1);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", image1);
            map.put("group_id", "admin_face");
            map.put("user_id", "user1");
            map.put("face_type", "LIVE");
            map.put("liveness_control", "NORMAL");
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = GetToken.getAuth();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String match() {

        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/match";
        try {

            byte[] bytes1 = FileUtil.readFileByBytes("D:\\photo\\pan1.png");
            byte[] bytes2 = FileUtil.readFileByBytes("D:\\photo\\pan2.png");
            String image1 = Base64Util.encode(bytes1);
            String image2 = Base64Util.encode(bytes2);

            List<Map<String, Object>> images = new ArrayList<Map<String, Object>>();

            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("image", image1);
            map1.put("image_type", "BASE64");
            map1.put("face_type", "LIVE");
            map1.put("quality_control", "LOW");
            map1.put("liveness_control", "NORMAL");

            Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put("image", image2);
            map2.put("image_type", "BASE64");
            map2.put("face_type", "LIVE");
            map2.put("quality_control", "LOW");
            map2.put("liveness_control", "NORMAL");

            images.add(map1);
            images.add(map2);

            String param = GsonUtils.toJson(images);
            String accessToken = GetToken.getAuth();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String faceDetect() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/detect";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", "a0e0c0c5624808aaa1be305914cceea5");
            map.put("face_field", "faceshape,facetype");
            map.put("image_type", "BASE64");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = GetToken.getAuth();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String faceSearch() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", "ade9599b133fb1bce9885735a46ff6a5");
            map.put("liveness_control", "NORMAL");
            map.put("group_id_list", "cashier_face");
            map.put("image_type", "FACE_TOKEN");
            map.put("quality_control", "LOW");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = GetToken.getAuth();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) throws IOException {
//        String result = FaceAdd.add();
//        JSONObject jsonObject = JSONObject.fromObject(result);
//        JSONObject fromObject = jsonObject.getJSONObject("result");
//        String face_token = (String) fromObject.get("face_token");
//        System.out.println("face_token=" + face_token);



//        FaceAdd.match();

        //搜索人脸
//        String result = FaceAdd.faceDetect();
//        JSONObject jsonObject = JSONObject.fromObject(result);
//        JSONObject jsonArray = jsonObject.getJSONObject("result").getJSONArray("face_list").getJSONObject(0);
//        String face_token = (String) jsonArray.get("face_token");
//        System.out.println("face_token=" + face_token);
        faceSearch();
    }
}
