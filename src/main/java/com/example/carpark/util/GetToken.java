package com.example.carpark.util;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * 获取token类
 */
public class GetToken {

    // 设置APPID/AK/SK
    public static final String APP_ID = "19542037";
    public static final String API_KEY = "DTCR63PLQH4dEA0pHMRXAdMg";
    public static final String SECRET_KEY = "O9iKdb6yC2baIMuDHqhBTxwaDfds19pT";

    public static String getAuth() {

        String clientId = API_KEY;// 官网获取的 API Key 更新为你注册的
        String clientSecret = SECRET_KEY;// 官网获取的 Secret Key 更新为你注册的
        return getAuth(clientId, clientSecret);
    }

    public static String getAuth(String ak, String sk) {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost

                + "grant_type=client_credentials"		// 1. grant_type为固定参数
                + "&client_id=" + ak					// 2. 官网获取的 API Key
                + "&client_secret=" + sk;				// 3. 官网获取的 Secret Key
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            System.err.println("result:" + result);

            JSONObject jsonObject = JSONObject.parseObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }

//    public static void main(String[] args) {
//        System.out.println(GetToken.getAuth());
//    }

}
