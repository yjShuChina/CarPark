package com.example.carpark.javabean;

import org.springframework.stereotype.Component;

import java.util.List;

//处理表格数据
@Component
public class PageBean<T>
{
    private int code;//code=0为成功，失败为其他值
    private String msg = "";//失败后的提示信息
    private int count;
    private List<?> data;//传输的数据

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}
