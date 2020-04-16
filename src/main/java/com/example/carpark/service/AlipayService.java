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
}