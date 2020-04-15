package com.example.carpark.javabean;


public class TbChargerParameter {

  private long cpId;
  private String chargeTime;
  private long cpType;
  private String stackTime;
  private long price;


  public long getCpId() {
    return cpId;
  }

  public void setCpId(long cpId) {
    this.cpId = cpId;
  }

  public String getChargeTime() {
    return chargeTime;
  }

  public void setChargeTime(String chargeTime) {
    this.chargeTime = chargeTime;
  }

  public long getCpType() {
    return cpType;
  }

  public void setCpType(long cpType) {
    this.cpType = cpType;
  }

  public String getStackTime() {
    return stackTime;
  }

  public void setStackTime(String stackTime) {
    this.stackTime = stackTime;
  }

  public long getPrice() {
    return price;
  }

  public void setPrice(long price) {
    this.price = price;
  }

}
