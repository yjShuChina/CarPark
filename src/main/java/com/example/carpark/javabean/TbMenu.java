package com.example.carpark.javabean;


import java.util.List;

public class TbMenu {

  private long menuId;
  private String menuName;
  private String menuUrl;
  private long parentId;
  private long state;
  private List<TbMenu> submenuList;


  public long getState() {
    return state;
  }

  public void setState(long state) {
    this.state = state;
  }

  public List<TbMenu> getSubmenuList() {
    return submenuList;
  }

  public void setSubmenuList(List<TbMenu> submenuList) {
    this.submenuList = submenuList;
  }

  public long getMenuId() {
    return menuId;
  }

  public void setMenuId(long menuId) {
    this.menuId = menuId;
  }


  public String getMenuName() {
    return menuName;
  }

  public void setMenuName(String menuName) {
    this.menuName = menuName;
  }


  public String getMenuUrl() {
    return menuUrl;
  }

  public void setMenuUrl(String menuUrl) {
    this.menuUrl = menuUrl;
  }


  public long getParentId() {
    return parentId;
  }

  public void setParentId(long parentId) {
    this.parentId = parentId;
  }

}
