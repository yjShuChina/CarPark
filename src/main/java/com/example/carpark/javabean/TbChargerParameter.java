package com.example.carpark.javabean;

public class TbChargerParameter {

  private long cpId;
  private java.sql.Timestamp chargeTime;
  private long price;


  public long getCpId() {
    return cpId;
  }

  public void setCpId(long cpId) {
    this.cpId = cpId;
  }


  public java.sql.Timestamp getChargeTime() {
    return chargeTime;
  }

  public void setChargeTime(java.sql.Timestamp chargeTime) {
    this.chargeTime = chargeTime;
  }


  public long getPrice() {
    return price;
  }

  public void setPrice(long price) {
    this.price = price;
  }

}
