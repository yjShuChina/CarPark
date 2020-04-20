package com.example.carpark.javabean;


import java.math.BigDecimal;

public class TbTemporaryCarRecord {

  private long temporaryCarId;
  private long pciId;
  private BigDecimal price;
  private String outTradeNo;
  private String subject;
  private String carNumber;
  private java.sql.Timestamp entryTime;
  private java.sql.Timestamp handleTime;
  private String time;
  private String tradeStatus;


  public long getTemporaryCarId() {
    return temporaryCarId;
  }

  public void setTemporaryCarId(long temporaryCarId) {
    this.temporaryCarId = temporaryCarId;
  }


  public long getPciId() {
    return pciId;
  }

  public void setPciId(long pciId) {
    this.pciId = pciId;
  }


  public java.sql.Timestamp getHandleTime() {
    return handleTime;
  }

  public void setHandleTime(java.sql.Timestamp handleTime) {
    this.handleTime = handleTime;
  }


  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }


  public String getOutTradeNo() {
    return outTradeNo;
  }

  public void setOutTradeNo(String outTradeNo) {
    this.outTradeNo = outTradeNo;
  }


  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
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

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }


  public String getTradeStatus() {
    return tradeStatus;
  }

  public void setTradeStatus(String tradeStatus) {
    this.tradeStatus = tradeStatus;
  }

  @Override
  public String toString() {
    return "TbTemporaryCarRecord{" +
            "temporaryCarId=" + temporaryCarId +
            ", pciId=" + pciId +
            ", price=" + price +
            ", outTradeNo='" + outTradeNo + '\'' +
            ", subject='" + subject + '\'' +
            ", carNumber='" + carNumber + '\'' +
            ", entryTime=" + entryTime +
            ", handleTime=" + handleTime +
            ", time=" + time +
            ", tradeStatus='" + tradeStatus + '\'' +
            '}';
  }
}
