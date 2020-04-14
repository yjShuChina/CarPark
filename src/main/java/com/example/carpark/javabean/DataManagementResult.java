package com.example.carpark.javabean;

import java.util.List;

//林堂星_用户管理 返回表的数据
public class DataManagementResult
{
	private int code;
	private int count;
	private String msg="";
	private List<?> data;

	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public List<?> getData()
	{
		return data;
	}

	public void setData(List<?> data)
	{
		this.data = data;
	}
}
