package com.example.carpark.javabean;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TbChargerParameter {

  private Integer cp_id;
  private Date charge_time;
  private Integer price;

  public Integer getCp_id() {
    return cp_id;
  }

  public void setCp_id(Integer cp_id) {
    this.cp_id = cp_id;
  }

  public Date getCharge_time() {
    return charge_time;
  }

  public void setCharge_time(Date charge_time) {
    this.charge_time = charge_time;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "TbChargerParameter{" +
            "cp_id=" + cp_id +
            ", charge_time=" + charge_time +
            ", price=" + price +
            '}';
  }
}
