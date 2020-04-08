package com.example.carpark.javabean;

import org.springframework.stereotype.Component;

@Component
public class TbAdmin {

  private Integer admin_id;//管理员id
  private String admin_account;//管理员账户
  private String admin_pwd;//管理员密码
  private String admin_name;
  private Integer role_id;
  private Integer admin_state;

  public Integer getAdmin_id() {
    return admin_id;
  }

  public void setAdmin_id(Integer admin_id) {
    this.admin_id = admin_id;
  }

  public String getAdmin_account() {
    return admin_account;
  }

  public void setAdmin_account(String admin_account) {
    this.admin_account = admin_account;
  }

  public String getAdmin_pwd() {
    return admin_pwd;
  }

  public void setAdmin_pwd(String admin_pwd) {
    this.admin_pwd = admin_pwd;
  }

  public String getAdmin_name() {
    return admin_name;
  }

  public void setAdmin_name(String admin_name) {
    this.admin_name = admin_name;
  }

  public Integer getRole_id() {
    return role_id;
  }

  public void setRole_id(Integer role_id) {
    this.role_id = role_id;
  }

  public Integer getAdmin_state() {
    return admin_state;
  }

  public void setAdmin_state(Integer admin_state) {
    this.admin_state = admin_state;
  }

  @Override
  public String toString() {
    return "TbAdmin{" +
            "admin_id=" + admin_id +
            ", admin_account='" + admin_account + '\'' +
            ", admin_pwd='" + admin_pwd + '\'' +
            ", admin_name='" + admin_name + '\'' +
            ", role_id=" + role_id +
            ", admin_state=" + admin_state +
            '}';
  }
}
