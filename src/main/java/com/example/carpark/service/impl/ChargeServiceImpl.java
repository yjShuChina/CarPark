package com.example.carpark.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.carpark.dao.AdminDao;
import com.example.carpark.dao.ChargeDao;
import com.example.carpark.javabean.*;
import com.example.carpark.service.CarService;
import com.example.carpark.service.ChargeService;
import com.example.carpark.util.HttpUtils;
import com.google.gson.Gson;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.tomcat.util.codec.binary.Base64.encodeBase64;


/*
 *  收费员service类
 * */
@Service
public class ChargeServiceImpl implements ChargeService {

    @Resource
    private ChargeDao chargeDao;

    @Resource
    private CarService carService;
    /*
     * 收费员登录
     * */
    @Override
    public TbCashier chargeLogin(Map<String, Object> map) {

        return chargeDao.chargeLogin(map);
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

    //图片存储返回url
    @Override
    public String uploadImage(MultipartFile file, String name) {

        String staticPath = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
        String fileName = file.getOriginalFilename();  //获取文件名
        String fileTyle = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        System.out.println("后缀名：" + fileTyle);

        // 图片存储目录及图片名称
        String url_path = "images" + File.separator + name + fileTyle;
        //图片保存路径
        String savePath = staticPath + File.separator + url_path;
        System.out.println("图片保存地址：" + savePath);
        // 访问路径=静态资源路径+文件目录路径
        String visitPath = "src/main/static/" + url_path;
        System.out.println("图片访问uri：" + visitPath);

        File saveFile = new File(savePath);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        try {
            file.transferTo(saveFile);  //将临时存储的文件移动到真实存储路径下
        } catch (IOException e) {
            e.printStackTrace();
        }

        return visitPath;
    }

    //收费规则数据查询接口
    @Override
    public String chargePrice() {
        PageBean pageBean = new PageBean();
        pageBean.setCode(0);
        List<TbChargerParameter> tbChargerParameters = chargeDao.chargePrice();

        System.out.println("车辆计费规则数据========="+new Gson().toJson(tbChargerParameters));
        if (tbChargerParameters == null) {
            pageBean.setCount(0);
        } else {
            pageBean.setCount(tbChargerParameters.size());
            for (int i = 0; i < tbChargerParameters.size(); i++) {
                tbChargerParameters.get(i).setChargeTime(getTimeDate(tbChargerParameters.get(i).getChargeTime()));
                tbChargerParameters.get(i).setStackTime(getTimeDate(tbChargerParameters.get(i).getStackTime()));
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

        if (tbChargerParameter.getCpType() == 0) {
            tbChargerParameter.setStackTime("0");
        } else {
            tbChargerParameter.setStackTime(getDateTime(tbChargerParameter.getStackTime()));
        }
        System.out.println("参数打印" + new Gson().toJson(tbChargerParameter));
        return chargeDao.modifyChargePrice(tbChargerParameter);
    }

    //收费规则添加
    @Override
    public int addChargePrice(TbChargerParameter tbChargerParameter) {
        tbChargerParameter.setChargeTime(getDateTime(tbChargerParameter.getChargeTime()));
        if (tbChargerParameter.getCpType() == 0) {
            tbChargerParameter.setStackTime("0");
        } else {
            tbChargerParameter.setStackTime(getDateTime(tbChargerParameter.getStackTime()));
        }
        System.out.println("参数打印" + new Gson().toJson(tbChargerParameter));
        return chargeDao.addChargePrice(tbChargerParameter);
    }

    //收费规则删除
    @Override
    public int delChargePrice(TbChargerParameter[] tbChargerParameter) {
        List<TbChargerParameter> tbChargerParameters = new ArrayList<>();
        for (int i = 0; i < tbChargerParameter.length; i++) {
            tbChargerParameters.add(tbChargerParameter[i]);
        }
        return chargeDao.delChargePrice(tbChargerParameters);
    }

    //场内信息查看
    @Override
    public String parkQuery(int page, int limit) {

        System.out.println("场内车辆信息,page = "+page +" limit = " +limit);
        PageBean pageBean = new PageBean();
        int page1 = (page - 1) * limit ;
        RowBounds rowBounds = new RowBounds(page1, limit);

        int count = chargeDao.parkQueryCount();

        List<TbParkCarInfo> tbParkCarInfos = chargeDao.parkQuery(rowBounds);
        if (tbParkCarInfos != null){
            pageBean.setData(tbParkCarInfos);
        }
        pageBean.setCount(count);
        pageBean.setCode(0);
        System.out.println(new Gson().toJson(pageBean));
        return new Gson().toJson(pageBean);
    }

    //出场车辆信息查看
    @Override
    public String carExitQuery(int page, int limit) {

        System.out.println("场内车辆信息,page = "+page +" limit = " +limit);
        int page1 = (page - 1) * limit ;
        Map<String,Integer> map = new HashMap<>();
        map.put("limit",limit);
        map.put("page",page1);
        PageBean pageBean = new PageBean();

        int count = chargeDao.carExitQueryCount();
        List<TbTotalCarExit> tbTotalCarExits = chargeDao.carExitQuery(map);

        if (tbTotalCarExits != null){
            pageBean.setData(tbTotalCarExits);
        }
        pageBean.setCount(count);
        pageBean.setCode(0);
        System.out.println(new Gson().toJson(pageBean));
        return new Gson().toJson(pageBean);

    }

    //进场车辆数据获取
    @Override
    public String gateMaxQuery() {
        TbParkCarInfo tbParkCarInfo = chargeDao.gateMaxQuery();
        if (tbParkCarInfo != null){
            String imgBase64 = "";
            try {
                File files = new File(tbParkCarInfo.getImgUrl());
                byte[] content = new byte[(int) files.length()];
                FileInputStream finputstream = new FileInputStream(files);
                finputstream.read(content);
                finputstream.close();
                imgBase64 = new String(Base64.encodeBase64(content));
                tbParkCarInfo.setImgUrl(imgBase64);
            } catch (IOException e) {
                e.printStackTrace();
            }
          return new Gson().toJson(tbParkCarInfo);
        }
        return "null";
    }

    //收费员确认收款
    @Override
    @Transactional
    public String confirmCollection(Map<String, String> map) {
        carService.changestate("1", map.get("parkSpaceId"));
        chargeDao.addTbCurrentCarExit(map);
        chargeDao.addTbTotalCarExit(map);
        chargeDao.delTbParkCarInfo(map);
        return "success";
    }

    //查询自助缴费记录
    @Override
    public List<TbTemporaryCarRecord> tbTemporaryCarRecordQuery(TbParkCarInfo tbParkCarInfo) {
        return chargeDao.tbTemporaryCarRecordQuery(tbParkCarInfo);
    }

    @Override
    public String excelGenerate(HttpServletRequest request) {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet("sheet");
        HSSFRow row = hssfSheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("车牌号");

        cell = row.createCell(1);
        cell.setCellValue("车辆状态");

        cell = row.createCell(2);
        cell.setCellValue("进场时间");

        cell = row.createCell(3);
        cell.setCellValue("出场时间");

        cell = row.createCell(4);
        cell.setCellValue("收费渠道");

        cell = row.createCell(5);
        cell.setCellValue("金额");

        int x = 1,total = 0 , self = 0,manual = 0;

        List<TbCurrentCarExit> tbCurrentCarExits =chargeDao.tbCurrentCarExitQuery();
        for (int i = 0; i < tbCurrentCarExits.size(); i++) {
            TbCurrentCarExit tblUser = tbCurrentCarExits.get(i);
            row = hssfSheet.createRow(x++);

            int r = 0;
            cell = row.createCell(r++);// 车牌号
            cell.setCellValue(tblUser.getCarNumber());

            cell = row.createCell(r++);// 车辆状态
            cell.setCellValue(tblUser.getCarIdentity());

            cell = row.createCell(r++);// 进场时间
            cell.setCellValue(tblUser.getEntryTime());

            cell = row.createCell(r++);// 出场时间
            cell.setCellValue(tblUser.getExitTime());

            cell = row.createCell(r++);// 收费渠道
            cell.setCellValue(tblUser.getChannel());

            cell = row.createCell(r++);// 金额
            cell.setCellValue(tblUser.getPrice());

            if ("人工收取".equals(tblUser.getChannel())){
                manual += tblUser.getPrice();
            }else {
                self += tblUser.getPrice();
            }
            total += tblUser.getPrice();
        }
        row = hssfSheet.createRow(x++);

        cell = row.createCell(4);
        cell.setCellValue("自助缴费合计:");

        cell = row.createCell(5);
        cell.setCellValue(self);

        row = hssfSheet.createRow(x++);

        cell = row.createCell(4);
        cell.setCellValue("人工收取合计:");

        cell = row.createCell(5);
        cell.setCellValue(manual);

        row = hssfSheet.createRow(x++);

        cell = row.createCell(4);
        cell.setCellValue("共合计:");

        cell = row.createCell(5);
        cell.setCellValue(total);

        String path = request.getSession().getServletContext().getRealPath("/excel");
        // new Date()为获取当前系统时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");//设置日期格式
        String timeDate = df.format(new Date());
        String url = path + "/" + timeDate + ".xls";
        try {
            FileOutputStream out = new FileOutputStream(url);

            hssfWorkbook.write(out);
            out.flush();

        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        chargeDao.delTbCurrentCarExit();
        return url;
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
        long hours = (Integer.parseInt(str[0]) * (1000L * 60 * 60));
        long minutes = (Integer.parseInt(str[1]) * 1000L * 60);
        long second = (Integer.parseInt(str[2]) * 1000L);
        return "" + (hours + minutes + second);
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
