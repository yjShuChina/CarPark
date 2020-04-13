package com.example.carpark.dao;

import com.example.carpark.javabean.TbRevenue;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RevenueDao {
    int deleteByPrimaryKey(Integer revenueId);

    int insert(TbRevenue record);

    int insertSelective(TbRevenue record);

    TbRevenue selectByPrimaryKey(Integer revenueId);

    int updateByPrimaryKeySelective(TbRevenue record);

    int updateByPrimaryKey(TbRevenue record);
}