package com.example.carpark.javabean;

public class TbMonthVip {

  private long mvrId;
  private long userId;
  private java.sql.Timestamp handleTime;
  private java.sql.Timestamp originDeadline;
  private java.sql.Timestamp currentDeadline;
  private long mcpId;


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


  public long getMcpId() {
    return mcpId;
  }

  public void setMcpId(long mcpId) {
    this.mcpId = mcpId;
  }

  @Override
  public String toString() {
    return "TbMonthVip{" +
            "mvrId=" + mvrId +
            ", userId=" + userId +
            ", handleTime=" + handleTime +
            ", originDeadline=" + originDeadline +
            ", currentDeadline=" + currentDeadline +
            ", mcpId=" + mcpId +
            '}';
  }
}
