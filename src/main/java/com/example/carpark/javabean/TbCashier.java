package com.example.carpark.javabean;

import org.springframework.stereotype.Component;

@Component
public class TbCashier {

  private String cashier_id;
  private String cashier_pwd;
  private String cashier_name;
  private Integer cashier_state;

  public String getCashier_id() {
    return cashier_id;
  }

  public void setCashier_id(String cashier_id) {
    this.cashier_id = cashier_id;
  }

  public String getCashier_pwd() {
    return cashier_pwd;
  }

  public void setCashier_pwd(String cashier_pwd) {
    this.cashier_pwd = cashier_pwd;
  }

  public String getCashier_name() {
    return cashier_name;
  }

  public void setCashier_name(String cashier_name) {
    this.cashier_name = cashier_name;
  }

  public long getCashier_state() {
    return cashier_state;
  }

  public void setCashier_state(Integer cashier_state) {
    this.cashier_state = cashier_state;
  }

  @Override
  public String toString() {
    return "TbCashier{" +
            "cashier_id='" + cashier_id + '\'' +
            ", cashier_pwd='" + cashier_pwd + '\'' +
            ", cashier_name='" + cashier_name + '\'' +
            ", cashier_state=" + cashier_state +
            '}';
  }
}
