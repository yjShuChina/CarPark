package com.example.carpark.javabean;

import org.springframework.stereotype.Component;

@Component
public class TbCashierShifts {

  private Integer cs_id;
  private String shifts;
  private String cashier_id;

  public long getCs_id() {
    return cs_id;
  }

  public void setCs_id(Integer cs_id) {
    this.cs_id = cs_id;
  }

  public String getShifts() {
    return shifts;
  }

  public void setShifts(String shifts) {
    this.shifts = shifts;
  }

  public String getCashier_id() {
    return cashier_id;
  }

  public void setCashier_id(String cashier_id) {
    this.cashier_id = cashier_id;
  }

  @Override
  public String toString() {
    return "TbCashierShifts{" +
            "cs_id=" + cs_id +
            ", shifts='" + shifts + '\'' +
            ", cashier_id='" + cashier_id + '\'' +
            '}';
  }
}
