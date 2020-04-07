package com.example.carpark.javabean;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TbUser {

  private Integer user_id;
  private String user_tel;
  private String user_pwd;
  private String car_number;
  private String user_name;
  private Date month_vip_begin;
  private Date month_vip_deadline;

  public Integer getUser_id() {
    return user_id;
  }

  public void setUser_id(Integer user_id) {
    this.user_id = user_id;
  }

  public String getUser_tel() {
    return user_tel;
  }

  public void setUser_tel(String user_tel) {
    this.user_tel = user_tel;
  }

  public String getUser_pwd() {
    return user_pwd;
  }

  public void setUser_pwd(String user_pwd) {
    this.user_pwd = user_pwd;
  }

  public String getCar_number() {
    return car_number;
  }

  public void setCar_number(String car_number) {
    this.car_number = car_number;
  }

  public String getUser_name() {
    return user_name;
  }

  public void setUser_name(String user_name) {
    this.user_name = user_name;
  }

  public Date getMonth_vip_begin() {
    return month_vip_begin;
  }

  public void setMonth_vip_begin(Date month_vip_begin) {
    this.month_vip_begin = month_vip_begin;
  }

  public Date getMonth_vip_deadline() {
    return month_vip_deadline;
  }

  public void setMonth_vip_deadline(Date month_vip_deadline) {
    this.month_vip_deadline = month_vip_deadline;
  }

  @Override
  public String toString() {
    return "TbUser{" +
            "user_id=" + user_id +
            ", user_tel='" + user_tel + '\'' +
            ", user_pwd='" + user_pwd + '\'' +
            ", car_number='" + car_number + '\'' +
            ", user_name='" + user_name + '\'' +
            ", month_vip_begin=" + month_vip_begin +
            ", month_vip_deadline=" + month_vip_deadline +
            '}';
  }
}
