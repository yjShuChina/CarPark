package com.example.carpark.dao;

import com.example.carpark.javabean.TbSystemParameter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SystemParameterDao {
    int deleteByPrimaryKey(Integer parameterId);

    int insert(TbSystemParameter record);

    int insertSelective(TbSystemParameter record);

    TbSystemParameter selectByPrimaryKey(Integer parameterId);

    int updateByPrimaryKeySelective(TbSystemParameter record);

    int updateByPrimaryKey(TbSystemParameter record);

    List<TbSystemParameter> findSysParamByPage(Map<String,Object> map);

    Integer findSysParamCount(Map<String,Object> map);

    TbSystemParameter selectByName(String parameterName);
}