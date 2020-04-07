package com.example.carpark.javabean;


import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TbMonthVip {

  private Integer mvr_id;
  private Integer user_id;
  private Date handle_time;
  private Date origin_deadline;
  private Date current_deadline;
  private Integer month;
  private Integer price;

  public Integer getMvr_id() {
    return mvr_id;
  }

  public void setMvr_id(Integer mvr_id) {
    this.mvr_id = mvr_id;
  }

  public Integer getUser_id() {
    return user_id;
  }

  public void setUser_id(Integer user_id) {
    this.user_id = user_id;
  }

  public Date getHandle_time() {
    return handle_time;
  }

  public void setHandle_time(Date handle_time) {
    this.handle_time = handle_time;
  }

  public Date getOrigin_deadline() {
    return origin_deadline;
  }

  public void setOrigin_deadline(Date origin_deadline) {
    this.origin_deadline = origin_deadline;
  }

  public Date getCurrent_deadline() {
    return current_deadline;
  }

  public void setCurrent_deadline(Date current_deadline) {
    this.current_deadline = current_deadline;
  }

  public Integer getMonth() {
    return month;
  }

  public void setMonth(Integer month) {
    this.month = month;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "TbMonthVip{" +
            "mvr_id=" + mvr_id +
            ", user_id=" + user_id +
            ", handle_time=" + handle_time +
            ", origin_deadline=" + origin_deadline +
            ", current_deadline=" + current_deadline +
            ", month=" + month +
            ", price=" + price +
            '}';
  }
}
