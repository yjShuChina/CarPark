package com.example.carpark.javabean;


public class TbRefund {

  private long refundId;
  private long userId;
  private long mvrId;
  private java.sql.Timestamp refundTime;
  private long refundPrice;


  public long getRefundId() {
    return refundId;
  }

  public void setRefundId(long refundId) {
    this.refundId = refundId;
  }


  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }


  public long getMvrId() {
    return mvrId;
  }

  public void setMvrId(long mvrId) {
    this.mvrId = mvrId;
  }


  public java.sql.Timestamp getRefundTime() {
    return refundTime;
  }

  public void setRefundTime(java.sql.Timestamp refundTime) {
    this.refundTime = refundTime;
  }


  public long getRefundPrice() {
    return refundPrice;
  }

  public void setRefundPrice(long refundPrice) {
    this.refundPrice = refundPrice;
  }

  @Override
  public String toString() {
    return "TbRefund{" +
            "refundId=" + refundId +
            ", userId=" + userId +
            ", mvrId=" + mvrId +
            ", refundTime=" + refundTime +
            ", refundPrice=" + refundPrice +
            '}';
  }
}
