package com.example.carpark.javabean;


public class TbAdmin {

  private long adminId;
  private String adminAccount;
  private String adminPwd;
  private String adminName;
  private long roleId;
  private long adminState;


  public long getAdminId() {
    return adminId;
  }

  public void setAdminId(long adminId) {
    this.adminId = adminId;
  }


  public String getAdminAccount() {
    return adminAccount;
  }

  public void setAdminAccount(String adminAccount) {
    this.adminAccount = adminAccount;
  }


  public String getAdminPwd() {
    return adminPwd;
  }

  public void setAdminPwd(String adminPwd) {
    this.adminPwd = adminPwd;
  }


  public String getAdminName() {
    return adminName;
  }

  public void setAdminName(String adminName) {
    this.adminName = adminName;
  }


  public long getRoleId() {
    return roleId;
  }

  public void setRoleId(long roleId) {
    this.roleId = roleId;
  }


  public long getAdminState() {
    return adminState;
  }

  public void setAdminState(long adminState) {
    this.adminState = adminState;
  }

}
