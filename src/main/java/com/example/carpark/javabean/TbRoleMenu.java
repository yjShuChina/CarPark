package com.example.carpark.javabean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class TbRoleMenu {

  private long rmId;
  private long roleId;
  private long menuId;
  private long state;

  public long getRmId() {
    return rmId;
  }

  public void setRmId(long rmId) {
    this.rmId = rmId;
  }

  public long getRoleId() {
    return roleId;
  }

  public void setRoleId(long roleId) {
    this.roleId = roleId;
  }

  public long getMenuId() {
    return menuId;
  }

  public void setMenuId(long menuId) {
    this.menuId = menuId;
  }

  public long getState() {
    return state;
  }

  public void setState(long state) {
    this.state = state;
  }

}
