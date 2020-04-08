package com.example.carpark.javabean;


import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TbCurrentCarExit {

  private Integer cce_id;
  private String car_number;
  private Date entry_time;
  private Date exit_time;
  private Integer price;
  private String channel;
  private String park_space_id;
  private String cashier_id;

  public Integer getCce_id() {
    return cce_id;
  }

  public void setCce_id(Integer cce_id) {
    this.cce_id = cce_id;
  }

  public String getCar_number() {
    return car_number;
  }

  public void setCar_number(String car_number) {
    this.car_number = car_number;
  }

  public Date getEntry_time() {
    return entry_time;
  }

  public void setEntry_time(Date entry_time) {
    this.entry_time = entry_time;
  }

  public Date getExit_time() {
    return exit_time;
  }

  public void setExit_time(Date exit_time) {
    this.exit_time = exit_time;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public String getPark_space_id() {
    return park_space_id;
  }

  public void setPark_space_id(String park_space_id) {
    this.park_space_id = park_space_id;
  }

  public String getCashier_id() {
    return cashier_id;
  }

  public void setCashier_id(String cashier_id) {
    this.cashier_id = cashier_id;
  }

  @Override
  public String toString() {
    return "TbCurrentCarExit{" +
            "cce_id=" + cce_id +
            ", car_number='" + car_number + '\'' +
            ", entry_time=" + entry_time +
            ", exit_time=" + exit_time +
            ", price=" + price +
            ", channel='" + channel + '\'' +
            ", park_space_id='" + park_space_id + '\'' +
            ", cashier_id='" + cashier_id + '\'' +
            '}';
  }
}
