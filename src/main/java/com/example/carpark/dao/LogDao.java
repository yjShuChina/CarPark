package com.example.carpark.dao;

import com.example.carpark.javabean.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志Dao
 *
 */
@Mapper
public interface LogDao
{
    int insertLog(TbLog log);
}
