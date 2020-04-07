package com.example.carpark.dao;


import com.example.carpark.javabean.Tbadmin;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;


@Mapper
public interface SYJLoginDao
{
	public Tbadmin login(HashMap<String, String> hashMap);
}
