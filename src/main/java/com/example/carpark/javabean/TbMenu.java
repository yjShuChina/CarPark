package com.example.carpark.javabean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public class TbMenu {

  private long menuId;
  private String menuName;
  private String menuUrl;
  private long parentId;
  private long state;
  private List<TbMenu> submenuList;

  public long getMenuId() {
    return menuId;
  }

  public void setMenuId(Integer menuId) {
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

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public long getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public List<TbMenu> getSubmenuList() {
    return submenuList;
  }

  public void setSubmenuList(List<TbMenu> submenuList) {
    this.submenuList = submenuList;
  }

  @Override
  public String toString() {
    return "TbMenu{" +
            "menuId=" + menuId +
            ", menuName='" + menuName + '\'' +
            ", menuUrl='" + menuUrl + '\'' +
            ", parentId=" + parentId +
            ", state=" + state +
            ", submenuList=" + submenuList +
            '}';
  }
}
