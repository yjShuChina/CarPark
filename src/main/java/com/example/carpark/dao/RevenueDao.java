package com.example.carpark.dao;

import com.example.carpark.javabean.TbRevenue;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;
import java.util.Map;

@Mapper
public interface RevenueDao {
    int deleteByPrimaryKey(Integer revenueId);

    int insert(TbRevenue record);

    int insertSelective(TbRevenue record);

    TbRevenue selectByPrimaryKey(Integer revenueId);

    int updateByPrimaryKeySelective(TbRevenue record);

    int updateByPrimaryKey(TbRevenue record);

    List<TbRevenue> findRevenueByPage(Map<String,Object> param);

    Integer findRevenueCount(Map<String,Object> map);
}