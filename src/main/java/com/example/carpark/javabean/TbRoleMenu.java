package com.example.carpark.javabean;

import org.springframework.stereotype.Component;

@Component
public class TbRoleMenu {

  private Integer rm_id;
  private Integer role_id;
  private Integer menu_id;
  private Integer state;

  public Integer getRm_id() {
    return rm_id;
  }

  public void setRm_id(Integer rm_id) {
    this.rm_id = rm_id;
  }

  public Integer getRole_id() {
    return role_id;
  }

  public void setRole_id(Integer role_id) {
    this.role_id = role_id;
  }

  public Integer getMenu_id() {
    return menu_id;
  }

  public void setMenu_id(Integer menu_id) {
    this.menu_id = menu_id;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  @Override
  public String toString() {
    return "TbRoleMenu{" +
            "rm_id=" + rm_id +
            ", role_id=" + role_id +
            ", menu_id=" + menu_id +
            ", state=" + state +
            '}';
  }
}
