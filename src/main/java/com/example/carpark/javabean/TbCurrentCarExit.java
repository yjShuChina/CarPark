package com.example.carpark.javabean;


public class TbCurrentCarExit {

  private long cceId;
  private String carNumber;
  private java.sql.Timestamp entryTime;
  private java.sql.Timestamp exitTime;
  private long price;
  private String channel;
  private String parkSpaceId;
  private long cashierId;


  public long getCceId() {
    return cceId;
  }

  public void setCceId(long cceId) {
    this.cceId = cceId;
  }


  public String getCarNumber() {
    return carNumber;
  }

  public void setCarNumber(String carNumber) {
    this.carNumber = carNumber;
  }


  public java.sql.Timestamp getEntryTime() {
    return entryTime;
  }

  public void setEntryTime(java.sql.Timestamp entryTime) {
    this.entryTime = entryTime;
  }


  public java.sql.Timestamp getExitTime() {
    return exitTime;
  }

  public void setExitTime(java.sql.Timestamp exitTime) {
    this.exitTime = exitTime;
  }


  public long getPrice() {
    return price;
  }

  public void setPrice(long price) {
    this.price = price;
  }


  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }


  public String getParkSpaceId() {
    return parkSpaceId;
  }

  public void setParkSpaceId(String parkSpaceId) {
    this.parkSpaceId = parkSpaceId;
  }


  public long getCashierId() {
    return cashierId;
  }

  public void setCashierId(long cashierId) {
    this.cashierId = cashierId;
  }

}
