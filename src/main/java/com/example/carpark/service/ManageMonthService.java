package com.example.carpark.service;

import com.example.carpark.javabean.TbMonthChargeParameter;

import java.util.HashMap;
import java.util.List;

public interface ManageMonthService {

    //月缴参数表查询
    public List<TbMonthChargeParameter> findMonthByPage(HashMap<String, Object> condition);

    //月缴参数表分页数量
    public int findMonthCount();

    //增加月缴参数
    public int addMonth(TbMonthChargeParameter tbMonthChargeParameter);

    //删除月缴参数
    public int delMonthById(int mcpId);

    //修改月缴参数
    public int alterMonth(TbMonthChargeParameter tbMonthChargeParameter);
}
