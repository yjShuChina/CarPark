package com.example.carpark.javabean;


public class TbAdmin {

  private long adminId;//管理员id
  private String adminAcc;//管理员账户
  private String adminPwd;//管理员密码
  private String adminName;
  private long roleId;
  private long adminState;


  public long getAdminId() {
    return adminId;
  }

  public void setAdminId(long adminId) {
    this.adminId = adminId;
  }


  public String getAdminAcc() {
    return adminAcc;
  }

  public void setAdminAcc(String adminAcc) {
    this.adminAcc = adminAcc;
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
