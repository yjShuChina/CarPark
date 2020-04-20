package com.example.carpark.javabean;


public class TbLog {

  private long logId;
  private String uname;
  private String operationTime;
  private String operation;
  private String operationType;


  public long getLogId() {
    return logId;
  }

  public void setLogId(long logId) {
    this.logId = logId;
  }


  public String getUname() {
    return uname;
  }

  public void setUname(String uname) {
    this.uname = uname;
  }


  public String getOperationTime()
  {
    return operationTime;
  }

  public void setOperationTime(String operationTime)
  {
    this.operationTime = operationTime;
  }

  public String getOperation() {
    return operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }


  public String getOperationType() {
    return operationType;
  }

  public void setOperationType(String operationType) {
    this.operationType = operationType;
  }

}
