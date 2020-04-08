package com.example.carpark.javabean;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TbLog {

  private Integer log_id;
  private String uname;
  private Date operation_time;
  private String operation;
  private String operation_type;

  public Integer getLog_id() {
    return log_id;
  }

  public void setLog_id(Integer log_id) {
    this.log_id = log_id;
  }

  public String getUname() {
    return uname;
  }

  public void setUname(String uname) {
    this.uname = uname;
  }

  public Date getOperation_time() {
    return operation_time;
  }

  public void setOperation_time(Date operation_time) {
    this.operation_time = operation_time;
  }

  public String getOperation() {
    return operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }

  public String getOperation_type() {
    return operation_type;
  }

  public void setOperation_type(String operation_type) {
    this.operation_type = operation_type;
  }

  @Override
  public String toString() {
    return "TbLog{" +
            "log_id=" + log_id +
            ", uname='" + uname + '\'' +
            ", operation_time=" + operation_time +
            ", operation='" + operation + '\'' +
            ", operation_type='" + operation_type + '\'' +
            '}';
  }
}
