package com.example.carpark.dao.SYJ;



import com.example.carpark.javabean.SYJ.SYJTbadmin;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;


@Mapper
public interface SYJLoginDao
{
	public SYJTbadmin login(HashMap<String, String> hashMap);
}
