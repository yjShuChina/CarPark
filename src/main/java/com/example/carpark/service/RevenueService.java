package com.example.carpark.service;

import com.example.carpark.javabean.CountData;
import com.example.carpark.javabean.ResultDate;
import com.example.carpark.javabean.TbMonthChargeParameter;
import com.example.carpark.javabean.TbRevenue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface RevenueService {
    ResultDate<TbRevenue> findRevenueByPage(Map<String, Object> map);

    List<TbMonthChargeParameter> findAllMonthParameter();

    String addRevenue(TbRevenue tbRevenue);

    BigDecimal selectPriceByMonth(Integer month);

    Integer deleteRevenueById(Integer revenueId);

    TbRevenue findRevenueById(Integer revenueId);

    String updateRevenue(TbRevenue tbRevenue);

    Map<String, Object> queryNearlySevenDays();

    Map<String, Object> queryNearlyMonth();

    Map<String, Object> queryCurYearBySeason();

    Map<String, Object> queryCurYearByMonth();

    Map<String, Object> queryMonthRevenue();
}
