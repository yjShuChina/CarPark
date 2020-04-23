package com.example.carpark.javabean;


public class TbTotalCarExit {

  private long tceId;
  private String carNumber;
  private String carIdentity;
  private java.sql.Timestamp entryTime;
  private java.sql.Timestamp exitTime;
  private long price;
  private String channel;
  private String parkSpaceId;
  private long cashierId;
  private String entryImgUrl;
  private String exitImgUrl;


  public long getTceId() {
    return tceId;
  }

  public void setTceId(long tceId) {
    this.tceId = tceId;
  }

  public String getCarNumber() {
    return carNumber;
  }

  public void setCarNumber(String carNumber) {
    this.carNumber = carNumber;
  }

  public String getCarIdentity() {
    return carIdentity;
  }

  public void setCarIdentity(String carIdentity) {
    this.carIdentity = carIdentity;
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


  public String getEntryImgUrl() {
    return entryImgUrl;
  }

  public void setEntryImgUrl(String entryImgUrl) {
    this.entryImgUrl = entryImgUrl;
  }


  public String getExitImgUrl() {
    return exitImgUrl;
  }

  public void setExitImgUrl(String exitImgUrl) {
    this.exitImgUrl = exitImgUrl;
  }

}
