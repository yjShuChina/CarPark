package com.example.carpark.javabean.SYJ;


public class SYJTbadmin {

  private long aid;
  private String aacc;
  private String apass;
  private String sex;
  private String age;
  private String address;
  private String aname;
  private String captcha;
  private long rid;
  private long tid;
  private SYJTbadmin tbadmin;

  public SYJTbadmin()
  {
  }

  public SYJTbadmin(long tid, long rid, SYJTbadmin tbadmin, int aid, String aacc, String apass, String sex, String age, String address, String aname, String captcha)
  {
    this.tid = tid;
    this.rid = rid;
    this.tbadmin = tbadmin;
    this.aid = aid;
    this.aacc = aacc;
    this.apass = apass;
    this.sex = sex;
    this.age = age;
    this.address = address;
    this.aname = aname;
    this.captcha = captcha;
  }

  public long getRid() {
    return rid;
  }

  public void setRid(long rid) {
    this.rid = rid;
  }

  public long getTid() {
    return tid;
  }

  public void setTid(long tid) {
    this.tid = tid;
  }

  public SYJTbadmin getTbadmin() {
    return tbadmin;
  }

  public void setTbadmin(SYJTbadmin tbadmin) {
    this.tbadmin = tbadmin;
  }

  public String getCaptcha()
  {
    return captcha;
  }

  public void setCaptcha(String captcha)
  {
    this.captcha = captcha;
  }

  public long getAid() {
    return aid;
  }

  public void setAid(long aid) {
    this.aid = aid;
  }


  public String getAacc() {
    return aacc;
  }

  public void setAacc(String aacc) {
    this.aacc = aacc;
  }


  public String getApass() {
    return apass;
  }

  public void setApass(String apass) {
    this.apass = apass;
  }


  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }


  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }


  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }


  public String getAname() {
    return aname;
  }

  public void setAname(String aname) {
    this.aname = aname;
  }

  @Override
  public String toString() {
    return "Tbadmin{" +
            "aid=" + aid +
            ", aacc='" + aacc + '\'' +
            ", apass='" + apass + '\'' +
            ", sex='" + sex + '\'' +
            ", age='" + age + '\'' +
            ", address='" + address + '\'' +
            ", aname='" + aname + '\'' +
            ", captcha='" + captcha + '\'' +
            '}';
  }
}
