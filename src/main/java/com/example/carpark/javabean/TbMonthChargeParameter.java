package com.example.carpark.javabean;

import org.springframework.stereotype.Component;

@Component
public class TbMonthChargeParameter {

  private Integer mcp_id;
  private Integer month;
  private Integer price;

  public Integer getMcp_id() {
    return mcp_id;
  }

  public void setMcp_id(Integer mcp_id) {
    this.mcp_id = mcp_id;
  }

  public Integer getMonth() {
    return month;
  }

  public void setMonth(Integer month) {
    this.month = month;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "TbMonthChargeParameter{" +
            "mcp_id=" + mcp_id +
            ", month=" + month +
            ", price=" + price +
            '}';
  }
}
