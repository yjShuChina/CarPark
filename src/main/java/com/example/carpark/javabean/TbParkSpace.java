package com.example.carpark.javabean;

import org.springframework.stereotype.Component;

@Component
public class TbParkSpace {

  private String park_space_id;
  private Integer park_space_state;

  public String getPark_space_id() {
    return park_space_id;
  }

  public void setPark_space_id(String park_space_id) {
    this.park_space_id = park_space_id;
  }

  public Integer getPark_space_state() {
    return park_space_state;
  }

  public void setPark_space_state(Integer park_space_state) {
    this.park_space_state = park_space_state;
  }

  @Override
  public String toString() {
    return "TbParkSpace{" +
            "park_space_id='" + park_space_id + '\'' +
            ", park_space_state=" + park_space_state +
            '}';
  }
}
