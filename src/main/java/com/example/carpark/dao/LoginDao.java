package com.example.carpark.dao;



import com.example.carpark.javabean.TbAdmin;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;


@Mapper
public interface LoginDao
{
	public TbAdmin login(HashMap<String, String> hashMap);
}
