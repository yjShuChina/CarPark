package com.example.carpark.javabean;


public class TbParkCarInfo {

  private long pciId;
  private String carNumber;
  private String carIdentity;
  private String parkSpaceId;
  private java.sql.Timestamp carTime;


  public long getPciId() {
    return pciId;
  }

  public void setPciId(long pciId) {
    this.pciId = pciId;
  }


  public String getCarNumber() {
    return carNumber;
  }

  public void setCarNumber(String carNumber) {
    this.carNumber = carNumber;
  }

  public String getCarIdentity()
  {
    return carIdentity;
  }

  public void setCarIdentity(String carIdentity)
  {
    this.carIdentity = carIdentity;
  }

  public String getParkSpaceId() {
    return parkSpaceId;
  }

  public void setParkSpaceId(String parkSpaceId) {
    this.parkSpaceId = parkSpaceId;
  }


  public java.sql.Timestamp getCarTime() {
    return carTime;
  }

  public void setCarTime(java.sql.Timestamp carTime) {
    this.carTime = carTime;
  }

}
