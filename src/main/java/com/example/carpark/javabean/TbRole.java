package com.example.carpark.javabean;

import org.springframework.stereotype.Component;

@Component
public class TbRole {

  private long roleId;
  private String role;


  public long getRoleId() {
    return roleId;
  }

  public void setRoleId(long roleId) {
    this.roleId = roleId;
  }


  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

}
