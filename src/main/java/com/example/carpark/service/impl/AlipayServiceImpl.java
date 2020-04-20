package com.example.carpark.service.impl;

import com.example.carpark.dao.AlipayDao;
import com.example.carpark.javabean.TbParkCarInfo;
import com.example.carpark.javabean.TbReceivable;
import com.example.carpark.javabean.TbTemporaryCarRecord;
import com.example.carpark.service.AlipayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AlipayServiceImpl implements AlipayService {


    @Resource
    private AlipayDao alipayDao;

    @Override//添加收费表信息
    public int addReceivable(TbReceivable tbReceivable) {
        return alipayDao.addReceivable(tbReceivable);
    }

    @Override//通过订单号，查询自助收费记录表
    public TbReceivable findReceivableById(String outTradeNo) {
        return alipayDao.findReceivableById(outTradeNo);
    }

    @Override//通过订单号，修改收费记录状态
    public int alterReceivableById(String outTradeNo) {
        return alipayDao.alterReceivableById(outTradeNo);
    }

    @Override//临时车辆自助缴费表信息
    public int addTemporaryCarRecord(TbTemporaryCarRecord tbTemporaryCarRecord) {
        return alipayDao.addTemporaryCarRecord(tbTemporaryCarRecord);
    }

    @Override//通过订单号，查询临时车辆自助缴费记录表
    public TbTemporaryCarRecord findTemporaryCarRecordById(String outTradeNo) {
        return alipayDao.findTemporaryCarRecordById(outTradeNo);
    }

    @Override//通过订单号，修改临时车辆自助缴费记录状态
    public int alterTemporaryCarRecordById(String outTradeNo) {
        return alipayDao.alterTemporaryCarRecordById(outTradeNo);
    }

    @Override//通过车牌号，查询场内车辆信息表
    public TbParkCarInfo findParkCarInfoByCar(String carNumber) {
        return alipayDao.findParkCarInfoByCar(carNumber);
    }
}
