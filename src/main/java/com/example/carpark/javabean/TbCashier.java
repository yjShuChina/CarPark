package com.example.carpark.javabean;


import java.util.Arrays;

public class TbCashier {

  private long cashierId;
  private String cashierAccount;
  private String cashierPwd;
  private String cashierName;
  private long cashierState;
  private String cashierSex;
  private String cashierPhone;
  private String cashierAddress;
  private String cashierTime;
  private String cashierHeadImg;
  private String cashierFace;

  public String getCashierHeadImg()
  {
    return cashierHeadImg;
  }

  public void setCashierHeadImg(String cashierHeadImg)
  {
    this.cashierHeadImg = cashierHeadImg;
  }

  public long getCashierId()
  {
    return cashierId;
  }

  public void setCashierId(long cashierId)
  {
    this.cashierId = cashierId;
  }

  public String getCashierAccount()
  {
    return cashierAccount;
  }

  public void setCashierAccount(String cashierAccount)
  {
    this.cashierAccount = cashierAccount;
  }

  public String getCashierPwd()
  {
    return cashierPwd;
  }

  public void setCashierPwd(String cashierPwd)
  {
    this.cashierPwd = cashierPwd;
  }

  public String getCashierName()
  {
    return cashierName;
  }

  public void setCashierName(String cashierName)
  {
    this.cashierName = cashierName;
  }

  public long getCashierState()
  {
    return cashierState;
  }

  public void setCashierState(long cashierState)
  {
    this.cashierState = cashierState;
  }

  public String getCashierSex()
  {
    return cashierSex;
  }

  public void setCashierSex(String cashierSex)
  {
    this.cashierSex = cashierSex;
  }

  public String getCashierPhone()
  {
    return cashierPhone;
  }

  public void setCashierPhone(String cashierPhone)
  {
    this.cashierPhone = cashierPhone;
  }

  public String getCashierAddress()
  {
    return cashierAddress;
  }

  public void setCashierAddress(String cashierAddress)
  {
    this.cashierAddress = cashierAddress;
  }

  public String getCashierTime()
  {
    return cashierTime;
  }

  public void setCashierTime(String cashierTime)
  {
    this.cashierTime = cashierTime;
  }

  public String getCashierFace() {
    return cashierFace;
  }

  public void setCashierFace(String cashierFace) {
    this.cashierFace = cashierFace;
  }

  @Override
  public String toString() {
    return "TbCashier{" +
            "cashierId=" + cashierId +
            ", cashierAccount='" + cashierAccount + '\'' +
            ", cashierPwd='" + cashierPwd + '\'' +
            ", cashierName='" + cashierName + '\'' +
            ", cashierState=" + cashierState +
            ", cashierSex='" + cashierSex + '\'' +
            ", cashierPhone='" + cashierPhone + '\'' +
            ", cashierAddress='" + cashierAddress + '\'' +
            ", cashierTime='" + cashierTime + '\'' +
            ", cashierHeadImg='" + cashierHeadImg + '\'' +
            ", cashierFace='" + cashierFace + '\'' +
            '}';
  }
}
