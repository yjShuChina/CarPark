package com.example.carpark.service;

import com.example.carpark.javabean.TbLog;
import com.example.carpark.javabean.TbParkCarInfo;
import com.example.carpark.javabean.TbUser;
import com.example.carpark.javabean.TbWhiteList;

/**
 * 日志service接口类
 */
public interface LogService
{
    /**
     * 日志
     */
    //添加日志
    int NLinsertLog(TbLog log);
}
