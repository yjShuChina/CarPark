package com.example.carpark.javabean;


public class TbMonthChargeParameter {

  private long mcpId;
  private long month;
  private long price;


  public long getMcpId() {
    return mcpId;
  }

  public void setMcpId(long mcpId) {
    this.mcpId = mcpId;
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
    return "TbMonthChargeParameter{" +
            "mcpId=" + mcpId +
            ", month=" + month +
            ", price=" + price +
            '}';
  }
}
