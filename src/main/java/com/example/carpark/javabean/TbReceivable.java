package com.example.carpark.javabean;

import java.math.BigDecimal;

//收费信息表
public class TbReceivable {

  private long receivableId;
  private long pciId;
  private java.sql.Timestamp receivableTime;
  private BigDecimal price;
  private String outTradeNo;
  private String subject;
  private String carNumber;
  private java.sql.Timestamp monthVipBegin;
  private long mcpId;
  private String tradeStatus;


  public long getReceivableId() {
    return receivableId;
  }

  public void setReceivableId(long receivableId) {
    this.receivableId = receivableId;
  }


  public long getPciId() {
    return pciId;
  }

  public void setPciId(long pciId) {
    this.pciId = pciId;
  }


  public java.sql.Timestamp getReceivableTime() {
    return receivableTime;
  }

  public void setReceivableTime(java.sql.Timestamp receivableTime) {
    this.receivableTime = receivableTime;
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


  public java.sql.Timestamp getMonthVipBegin() {
    return monthVipBegin;
  }

  public void setMonthVipBegin(java.sql.Timestamp monthVipBegin) {
    this.monthVipBegin = monthVipBegin;
  }


  public long getMcpId() {
    return mcpId;
  }

  public void setMcpId(long mcpId) {
    this.mcpId = mcpId;
  }


  public String getTradeStatus() {
    return tradeStatus;
  }

  public void setTradeStatus(String tradeStatus) {
    this.tradeStatus = tradeStatus;
  }

  @Override
  public String toString() {
    return "TbReceivable{" +
            "receivableId=" + receivableId +
            ", pciId=" + pciId +
            ", receivableTime=" + receivableTime +
            ", price=" + price +
            ", outTradeNo='" + outTradeNo + '\'' +
            ", subject='" + subject + '\'' +
            ", carNumber='" + carNumber + '\'' +
            ", monthVipBegin=" + monthVipBegin +
            ", mcpId=" + mcpId +
            ", tradeStatus='" + tradeStatus + '\'' +
            '}';
  }
}
