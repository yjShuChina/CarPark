package com.example.carpark.javabean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class TbUser {

  private long userId;
  private String userTel;
  private String userPwd;
  private String carNumber;
  private String userName;
  private java.sql.Timestamp monthVipBegin;
  private java.sql.Timestamp monthVipDeadline;


  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }


  public String getUserTel() {
    return userTel;
  }

  public void setUserTel(String userTel) {
    this.userTel = userTel;
  }


  public String getUserPwd() {
    return userPwd;
  }

  public void setUserPwd(String userPwd) {
    this.userPwd = userPwd;
  }


  public String getCarNumber() {
    return carNumber;
  }

  public void setCarNumber(String carNumber) {
    this.carNumber = carNumber;
  }


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


  public java.sql.Timestamp getMonthVipBegin() {
    return monthVipBegin;
  }

  public void setMonthVipBegin(java.sql.Timestamp monthVipBegin) {
    this.monthVipBegin = monthVipBegin;
  }


  public java.sql.Timestamp getMonthVipDeadline() {
    return monthVipDeadline;
  }

  public void setMonthVipDeadline(java.sql.Timestamp monthVipDeadline) {
    this.monthVipDeadline = monthVipDeadline;
  }

  @Override
  public String toString() {
    return "TbUser{" +
            "userId=" + userId +
            ", userTel='" + userTel + '\'' +
            ", userPwd='" + userPwd + '\'' +
            ", carNumber='" + carNumber + '\'' +
            ", userName='" + userName + '\'' +
            ", monthVipBegin=" + monthVipBegin +
            ", monthVipDeadline=" + monthVipDeadline +
            '}';
  }
}
