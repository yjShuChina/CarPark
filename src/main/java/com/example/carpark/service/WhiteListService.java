package com.example.carpark.service;

import com.example.carpark.javabean.TbChargerParameter;
import com.example.carpark.javabean.TbWhiteList;

//白名单设置
public interface WhiteListService {

    //白名单查看
    String queryWhiteList(int page,int limit);

    //白名单修改
    int modifyWhiteList(TbWhiteList tbWhiteList);

    //白名单添加
    int addWhiteList(TbWhiteList tbWhiteList);

    //白名单删除
    int delWhiteList(TbWhiteList[] tbWhiteLists);
}
