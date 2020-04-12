package com.example.carpark.javabean;

import org.springframework.stereotype.Component;

//退费返回数据实体
@Component
public class Refund {

  private String monthVipBegin;//月缴生效时间
  private long month;//办理月份
  private long price;//退费金额

  public String getMonthVipBegin() {
    return monthVipBegin;
  }

  public void setMonthVipBegin(String monthVipBegin) {
    this.monthVipBegin = monthVipBegin;
  }

  public long getMonth() {
    return month;
  }

  public void setMonth(long month) {
    this.month = month;
  }

  public long getPrice() {
    return price;
  }

  public void setPrice(long price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "Refund{" +
            "monthVipBegin='" + monthVipBegin + '\'' +
            ", month=" + month +
            ", price=" + price +
            '}';
  }
}
