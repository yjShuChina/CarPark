package com.example.carpark.dao;

import com.example.carpark.javabean.TbReceivable;
import com.example.carpark.javabean.TbUser;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface AlipayDao
{
    //添加收费表信息
    public int addReceivable(TbReceivable tbReceivable);

    //通过订单号，查询自助收费记录表
    public TbReceivable findReceivableById(String outTradeNo);

    //通过订单号，修改收费记录状态
    public int alterReceivableById(String outTradeNo);
}
