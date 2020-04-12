package com.example.carpark.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.carpark.dao.AdminDao;
import com.example.carpark.dao.ChargeDao;
import com.example.carpark.javabean.*;
import com.example.carpark.service.ChargeService;
import com.example.carpark.util.HttpUtils;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    /*
     * 车辆交费状态查询
     *  0 :白名单
     *  -1:场内无车辆信息
     *  1 :有月卡记录
     *  2 :临时车
     */
    public int chargeCalculation(String carNumber) {

        //白名单查询
        TbWhiteList tbWhiteList = chargeDao.whitelistQuery(carNumber);
        if (tbWhiteList != null) {
            return 0;
        }

        //车辆场内信息查询
        TbParkCarInfo tbParkCarInfo = chargeDao.carParkQuery(carNumber);
        if (tbParkCarInfo == null) {
            return -1;
        }

        //当前时间获取
        String startTimeStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        Date startTime = new Date(startTimeStr);

        //月卡查询(用户查询)
        TbUser tbUser = chargeDao.userQuery(carNumber);
        if (tbUser != null) {
            return 1;
        }

        return 2;
    }

    //停车时间规则计算收费金额
    private int carCount() throws ParseException {
        //当前时间获取
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这个是你要转成后的时间的格式
        String sd = sdf.format(new Date());   // 时间戳转换成时间
        System.out.println(sd);//打印出你要的时间

        Timestamp newMonthVipDeadline = new Timestamp(sdf.parse(sd).getTime());//到期时间转换格式

        System.out.println(newMonthVipDeadline);
        Timestamp asdasd = new Timestamp(sdf.parse("2020-04-12 12:24:58").getTime());//到期时间转换格式
        System.out.println(asdasd);
        System.out.println(getTimeDifference(asdasd, newMonthVipDeadline));
        return 0;
    }

    public String getTimeDifference(Timestamp formatTime1, Timestamp formatTime2) {
        long t1 = formatTime1.getTime();
        long t2 = formatTime2.getTime();
        int hours = (int) ((t1 - t2) / (1000 * 60 * 60));
        int minutes = (int) (((t1 - t2) / 1000 - hours * (60 * 60)) / 60);
        int second = (int) ((t1 - t2) / 1000 - hours * (60 * 60) - minutes * 60);

        return "" + ((t1 - t2) / 1000);
//        return ""+hours+"小时"+minutes+"分"+second+"秒";
    }

    public static void main(String[] args) {
        try {
            new ChargeServiceImpl().carCount();
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    //收费规则数据查询接口
    @Override
    public String chargePrice() {
        PageBean pageBean = new PageBean();
        pageBean.setCode(0);
        List<TbChargerParameter> tbChargerParameters = chargeDao.chargePrice();
        if (tbChargerParameters == null) {
            pageBean.setCount(0);
        } else {
            pageBean.setCount(tbChargerParameters.size());
            for (int i = 0; i < tbChargerParameters.size(); i++) {
                tbChargerParameters.get(i).setChargeTime(getTimeDate(tbChargerParameters.get(i).getChargeTime()));
            }
            pageBean.setData(tbChargerParameters);
        }
        Gson g = new Gson();
        return g.toJson(pageBean);
    }

    //收费规则修改
    @Override
    public int modifyChargePrice(TbChargerParameter tbChargerParameter) {
        tbChargerParameter.setChargeTime(getDateTime(tbChargerParameter.getChargeTime()));
        return chargeDao.modifyChargePrice(tbChargerParameter);
    }

    //收费规则添加
    @Override
    public int addChargePrice(TbChargerParameter tbChargerParameter) {
        tbChargerParameter.setChargeTime(getDateTime(tbChargerParameter.getChargeTime()));
        return chargeDao.addChargePrice(tbChargerParameter);
    }

    //收费规则删除
    @Override
    public int delChargePrice(String id) {
        return chargeDao.delChargePrice(id);
    }

    //数组转时间
    public String getTimeDate(String timeSize) {
        int i = Integer.parseInt(timeSize);
        int hours = (i / (1000 * 60 * 60));
        int minutes = ((i / 1000 - hours * (60 * 60)) / 60);
        int second = (i / 1000 - hours * (60 * 60) - minutes * 60);
        return "" + hours + ":" + minutes + ":" + second;
    }

    //时间转数字
    public String getDateTime(String timeStr) {
        String[] str = timeStr.split(":");
        int hours = (Integer.parseInt(str[0]) * (1000 * 60 * 60));
        int minutes = (Integer.parseInt(str[1]) * 1000 * 60);
        int second = (Integer.parseInt(str[2]) * 1000);
        return ""+(hours + minutes + second);
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
     *
     * @param file
     */
    public static void delteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }
    }

}
