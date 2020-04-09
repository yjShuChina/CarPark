package com.example.carpark.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.carpark.dao.AdminDao;
import com.example.carpark.dao.ChargeDao;
import com.example.carpark.javabean.TbCashier;
import com.example.carpark.javabean.TbParkCarInfo;
import com.example.carpark.javabean.TbUser;
import com.example.carpark.javabean.TbWhiteList;
import com.example.carpark.service.ChargeService;
import com.example.carpark.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.apache.tomcat.util.codec.binary.Base64.encodeBase64;


/*
 *  收费员service类
 * */
@Service
public class ChargeServiceImpl implements ChargeService {

    @Resource
    private ChargeDao chargeDao;


    /*
     * 收费员登录
     * */
    @Override
    public TbCashier chargeLogin(Map<String, Object> map) {

        return chargeDao.chargeLogin(map);
    }

    //收费规则计算
    public int chargeCalculation(String carNumber) {

        //白名单查询
        TbWhiteList tbWhiteList = chargeDao.whitelistQuery(carNumber);
        if (tbWhiteList != null) {
            return 0;
        }

        //车辆场内信息查询
        TbParkCarInfo tbParkCarInfo = chargeDao.carParkQuery(carNumber);

        //月卡查询
        TbUser tbUser = chargeDao.userQuery(carNumber);


        return 0;
    }


    //图片识别车牌
    @Override
    public String findcarnumber(MultipartFile imgFile) {
        String carnumber = "";
        String host = "https://ocrcp.market.alicloudapi.com";
        String path = "/rest/160601/ocr/ocr_vehicle_plate.json";
        String appcode = "6b7ded664bdc40c68843e2431cbddde7";
        Boolean is_old_format = false;//如果文档的输入中含有inputs字段，设置为True， 否则设置为False
        //请根据线上文档修改configure字段
        JSONObject configObj = new JSONObject();
        configObj.put("multi_crop", false);
        String config_str = configObj.toString();


        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);

        Map<String, String> querys = new HashMap<String, String>();

        // 对图像进行base64编码
        String imgBase64 = "";

        File file = null;
        if (imgFile.equals("") || imgFile.getSize() <= 0) {
            imgFile = null;
        } else {
            InputStream ins = null;
            try {
                ins = imgFile.getInputStream();
                file = new File(imgFile.getOriginalFilename());
                inputStreamToFile(ins, file);
                ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        try {

            byte[] content = new byte[(int) file.length()];
            FileInputStream finputstream = new FileInputStream(file);
            finputstream.read(content);
            finputstream.close();
            imgBase64 = new String(encodeBase64(content));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        // 拼装请求body的json字符串
        JSONObject requestObj = new JSONObject();
        try {
            if (is_old_format) {
                JSONObject obj = new JSONObject();
                obj.put("image", getParam(50, imgBase64));
                if (config_str.length() > 0) {
                    obj.put("configure", getParam(50, config_str));
                }
                JSONArray inputArray = new JSONArray();
                inputArray.add(obj);
                requestObj.put("inputs", inputArray);
            } else {
                requestObj.put("image", imgBase64);
                if (config_str.length() > 0) {
                    requestObj.put("configure", config_str);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String bodys = requestObj.toString();

        try {

            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            int stat = response.getStatusLine().getStatusCode();
            if (stat != 200) {
                System.out.println("Http code: " + stat);
                System.out.println("http header error msg: " + response.getFirstHeader("X-Ca-Error-Message"));
                System.out.println("Http body error msg:" + EntityUtils.toString(response.getEntity()));
                return "";
            }

            String res = EntityUtils.toString(response.getEntity());
            JSONObject res_obj = JSON.parseObject(res);
            if (is_old_format) {
                JSONArray outputArray = res_obj.getJSONArray("outputs");
                String output = outputArray.getJSONObject(0).getJSONObject("outputValue").getString("dataValue");
                JSONObject out = JSON.parseObject(output);
                System.out.println(out.toJSONString());
            } else {
                String str = res_obj.toJSONString();
                System.out.println(str);
                Map mapTypes = JSON.parseObject(str);
                System.out.println("这个是用JSON类的parseObject来解析JSON字符串!!!");
                for (Object obj : mapTypes.keySet()) {
                    System.out.println("key为：" + obj + "值为：" + mapTypes.get(obj));
                }
                String str1 = mapTypes.get("plates").toString();
                str1 = str1.split("\"txt\":\"")[1].split("\",\"")[0];
                System.out.println(str1);
                carnumber = str1;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        delteTempFile(file);
        return carnumber;
    }

    /*
     * 车牌识别附属方法
     * 获取参数的json对象
     */
    public JSONObject getParam(int type, String dataValue) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("dataType", type);
            obj.put("dataValue", dataValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    //获取流文件、车牌识别附属方法
    private void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除本地临时文件
     * 车牌识别附属方法
     * @param file
     */
    public static void delteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }
    }

}
