package com.example.carpark.javabean;

public class TbParkCarInfo {

  private long pciId;
  private String carNumber;
  private long carIdentity;
  private String parkSpaceId;


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


  public long getCarIdentity() {
    return carIdentity;
  }

  public void setCarIdentity(long carIdentity) {
    this.carIdentity = carIdentity;
  }


  public String getParkSpaceId() {
    return parkSpaceId;
  }

  public void setParkSpaceId(String parkSpaceId) {
    this.parkSpaceId = parkSpaceId;
  }

}
