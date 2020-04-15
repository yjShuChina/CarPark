package com.example.carpark.dao;

import com.example.carpark.javabean.CountData;
import com.example.carpark.javabean.TbRevenue;
import org.apache.ibatis.annotations.Mapper;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface RevenueDao {
    //根据主键删除
    int deleteByPrimaryKey(Integer revenueId);
    //插入
    int insert(TbRevenue record);

    int insertSelective(TbRevenue record);
    //根据主键查询
    TbRevenue selectByPrimaryKey(Integer revenueId);

    int updateByPrimaryKeySelective(TbRevenue record);
    //根据主键更新
    int updateByPrimaryKey(TbRevenue record);
    //分页查询
    List<TbRevenue> findRevenueByPage(Map<String,Object> param);
    //总条数
    Integer findRevenueCount(Map<String,Object> map);
    //查询近七天收入
    List<CountData> queryNearlySevenDays(String incomeType);
    //上一个月收入
    BigDecimal queryNearlyMonth(Map<String,Object> map);
    //今年收入（季度）
    List<CountData> queryCurYearBySeason(String incomeType);
    //今年收入（月）
    List<CountData> queryCurYearByMonth(String incomeType);
    //查询不同月缴产品
    CountData queryMonthRevenue(Integer month);
}