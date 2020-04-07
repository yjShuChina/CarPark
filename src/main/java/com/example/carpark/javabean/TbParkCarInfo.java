package com.example.carpark.javabean;

import org.springframework.stereotype.Component;

@Component
public class TbParkCarInfo {

  private Integer pci_id;
  private String car_number;
  private Integer car_identity;
  private String park_space_id;

  public Integer getPci_id() {
    return pci_id;
  }

  public void setPci_id(Integer pci_id) {
    this.pci_id = pci_id;
  }

  public String getCar_number() {
    return car_number;
  }

  public void setCar_number(String car_number) {
    this.car_number = car_number;
  }

  public Integer getCar_identity() {
    return car_identity;
  }

  public void setCar_identity(Integer car_identity) {
    this.car_identity = car_identity;
  }

  public String getPark_space_id() {
    return park_space_id;
  }

  public void setPark_space_id(String park_space_id) {
    this.park_space_id = park_space_id;
  }

  @Override
  public String toString() {
    return "TbParkCarInfo{" +
            "pci_id=" + pci_id +
            ", car_number='" + car_number + '\'' +
            ", car_identity=" + car_identity +
            ", park_space_id='" + park_space_id + '\'' +
            '}';
  }
}
