package com.example.carpark.javabean;


import java.math.BigDecimal;

public class TbParkSpace {

  private String parkSpaceId;
  private long parkSpaceState;
  private BigDecimal x;
  private BigDecimal y;


  public String getParkSpaceId() {
    return parkSpaceId;
  }

  public void setParkSpaceId(String parkSpaceId) {
    this.parkSpaceId = parkSpaceId;
  }


  public long getParkSpaceState() {
    return parkSpaceState;
  }

  public void setParkSpaceState(long parkSpaceState) {
    this.parkSpaceState = parkSpaceState;
  }

  public BigDecimal getX()
  {
    return x;
  }

  public void setX(BigDecimal x)
  {
    this.x = x;
  }

  public BigDecimal getY()
  {
    return y;
  }

  public void setY(BigDecimal y)
  {
    this.y = y;
  }
}
