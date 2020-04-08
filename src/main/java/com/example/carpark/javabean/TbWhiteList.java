package com.example.carpark.javabean;

import org.springframework.stereotype.Component;

@Component
public class TbWhiteList {

  private Integer wl_id;
  private String user_tel;
  private String car_number;
  private String user_name;

  public Integer getWl_id() {
    return wl_id;
  }

  public void setWl_id(Integer wl_id) {
    this.wl_id = wl_id;
  }

  public String getUser_tel() {
    return user_tel;
  }

  public void setUser_tel(String user_tel) {
    this.user_tel = user_tel;
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

  @Override
  public String toString() {
    return "TbWhiteList{" +
            "wl_id=" + wl_id +
            ", user_tel='" + user_tel + '\'' +
            ", car_number='" + car_number + '\'' +
            ", user_name='" + user_name + '\'' +
            '}';
  }
}
