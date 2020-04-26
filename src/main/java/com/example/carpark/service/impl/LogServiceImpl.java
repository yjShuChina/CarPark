package com.example.carpark.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.carpark.dao.CarDao;
import com.example.carpark.dao.ChargeDao;
import com.example.carpark.dao.LogDao;
import com.example.carpark.javabean.TbLog;
import com.example.carpark.javabean.TbParkCarInfo;
import com.example.carpark.javabean.TbUser;
import com.example.carpark.javabean.TbWhiteList;
import com.example.carpark.service.CarService;
import com.example.carpark.service.LogService;
import com.example.carpark.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.apache.tomcat.util.codec.binary.Base64.encodeBase64;

/**
 * 车辆service层
 */
@Service
public class LogServiceImpl implements LogService
{

    @Resource
    private LogDao logDao;

    @Override
    public int NLinsertLog(TbLog log){
        return logDao.insertLog(log);
    }

    @Override
    public int CashierLog(String uname)
    {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        TbLog tbLog = new TbLog();
        tbLog.setOperation("收费员登录");
        tbLog.setUname(uname);
        tbLog.setOperationType("login");
        tbLog.setOperationTime(dateTime.format(formatter));
        return logDao.insertLog(tbLog);
    }


}
