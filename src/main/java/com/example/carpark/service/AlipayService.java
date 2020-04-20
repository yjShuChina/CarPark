package com.example.carpark.service;

import com.example.carpark.javabean.*;

public interface AlipayService
{
    //添加收费表信息
    public int addReceivable(TbReceivable tbReceivable);

    //通过订单号，查询自助收费记录表
    public TbReceivable findReceivableById(String outTradeNo);

    //通过订单号，修改收费记录状态
    public int alterReceivableById(String outTradeNo);

    //临时车辆自助缴费表信息
    public int addTemporaryCarRecord(TbTemporaryCarRecord tbTemporaryCarRecord);

    //通过订单号，查询临时车辆自助缴费记录表
    public TbTemporaryCarRecord findTemporaryCarRecordById(String outTradeNo);

    //通过订单号，修改临时车辆自助缴费记录状态
    public int alterTemporaryCarRecordById(String outTradeNo);

    //通过车牌号，查询场内车辆信息表
    public TbParkCarInfo findParkCarInfoByCar(String carNumber);
}