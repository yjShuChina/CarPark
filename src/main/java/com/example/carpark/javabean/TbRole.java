package com.example.carpark.javabean;

import org.springframework.stereotype.Component;

@Component
public class TbRole {

  private Integer role_id;
  private String role;

  public Integer getRole_id() {
    return role_id;
  }

  public void setRole_id(Integer role_id) {
    this.role_id = role_id;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "TbRole{" +
            "role_id=" + role_id +
            ", role='" + role + '\'' +
            '}';
  }
}
