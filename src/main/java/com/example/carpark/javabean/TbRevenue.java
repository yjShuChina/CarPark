package com.example.carpark.javabean;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TbRevenue {
    //收支表id
    private Integer revenueId;
    //收入类型（phone/auto/manual）
    private String incomeType;
    //月缴产品（0为临时/月缴产品根据参数表来）
    private Integer month;
    //缴费或支出金额
    private BigDecimal price;
    //缴费时间(yyyy-MM-dd HH:mm:ss)
    private String time;
    //收入或支出（1为收入/2为支出）
    private Integer revenue;
    //车牌号
    private String carNumber;

    public Integer getRevenueId() {
        return revenueId;
    }

    public void setRevenueId(Integer revenueId) {
        this.revenueId = revenueId;
    }

    public String getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType == null ? null : incomeType.trim();
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    @Override
    public String toString() {
        return "TbRevenue{" +
                "revenueId=" + revenueId +
                ", incomeType='" + incomeType + '\'' +
                ", month=" + month +
                ", price=" + price +
                ", time='" + time + '\'' +
                ", revenue=" + revenue +
                ", carNumber='" + carNumber + '\'' +
                '}';
    }
}