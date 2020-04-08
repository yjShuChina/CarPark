package com.example.carpark.javabean;

import org.springframework.stereotype.Component;

@Component
public class TbMenu {

  private Integer menu_id;
  private String menu_name;
  private String menu_url;
  private Integer parent_id;

  public Integer getMenu_id() {
    return menu_id;
  }

  public void setMenu_id(Integer menu_id) {
    this.menu_id = menu_id;
  }

  public String getMenu_name() {
    return menu_name;
  }

  public void setMenu_name(String menu_name) {
    this.menu_name = menu_name;
  }

  public String getMenu_url() {
    return menu_url;
  }

  public void setMenu_url(String menu_url) {
    this.menu_url = menu_url;
  }

  public Integer getParent_id() {
    return parent_id;
  }

  public void setParent_id(Integer parent_id) {
    this.parent_id = parent_id;
  }

  @Override
  public String toString() {
    return "TbMenu{" +
            "menu_id=" + menu_id +
            ", menu_name='" + menu_name + '\'' +
            ", menu_url='" + menu_url + '\'' +
            ", parent_id=" + parent_id +
            '}';
  }
}
