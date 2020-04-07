package com.example.carpark.javabean;


public class TbMonthVip {

  private long mvrId;
  private long userId;
  private java.sql.Timestamp handleTime;
  private java.sql.Timestamp originDeadline;
  private java.sql.Timestamp currentDeadline;
  private long month;
  private long price;


  public long getMvrId() {
    return mvrId;
  }

  public void setMvrId(long mvrId) {
    this.mvrId = mvrId;
  }


  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }


  public java.sql.Timestamp getHandleTime() {
    return handleTime;
  }

  public void setHandleTime(java.sql.Timestamp handleTime) {
    this.handleTime = handleTime;
  }


  public java.sql.Timestamp getOriginDeadline() {
    return originDeadline;
  }

  public void setOriginDeadline(java.sql.Timestamp originDeadline) {
    this.originDeadline = originDeadline;
  }


  public java.sql.Timestamp getCurrentDeadline() {
    return currentDeadline;
  }

  public void setCurrentDeadline(java.sql.Timestamp currentDeadline) {
    this.currentDeadline = currentDeadline;
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

}
