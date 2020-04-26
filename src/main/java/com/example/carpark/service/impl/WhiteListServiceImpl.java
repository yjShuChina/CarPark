package com.example.carpark.service.impl;

import com.example.carpark.aoplog.Log;
import com.example.carpark.dao.ChargeDao;
import com.example.carpark.javabean.PageBean;
import com.example.carpark.javabean.TbWhiteList;
import com.example.carpark.service.WhiteListService;
import com.google.gson.Gson;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/*
 *  白名单管理service类
 * */
@Service
public class WhiteListServiceImpl implements WhiteListService {

    @Resource
    private ChargeDao chargeDao;

    //白名单查看
    @Override
    public String queryWhiteList(int page, int limit) {
        System.out.println("白名单,page = "+page +" limit = " +limit);
        PageBean pageBean = new PageBean();
        int page1 = (page - 1) * limit ;
        RowBounds rowBounds = new RowBounds(page1, limit);

        int count = chargeDao.queryWhiteListCount();

        List<TbWhiteList> tbWhiteLists = chargeDao.queryWhiteList(rowBounds);
        if (tbWhiteLists != null){
            pageBean.setData(tbWhiteLists);
        }
        pageBean.setCount(count);
        pageBean.setCode(0);

        return new Gson().toJson(pageBean);
    }
    //白名单修改
    @Override
    @Log(operationName = "白名单修改",operationType = "updata")
    public int modifyWhiteList(TbWhiteList tbWhiteList) {
        return chargeDao.modifyWhiteList(tbWhiteList);
    }
    //白名单添加
    @Override
    @Log(operationName = "白名单添加",operationType = "insert")
    public int addWhiteList(TbWhiteList tbWhiteList) {
        return chargeDao.addWhiteList(tbWhiteList);
    }
    //白名单删除
    @Override
    @Log(operationName = "白名单删除",operationType = "delete")
    public int delWhiteList(TbWhiteList[] tbWhiteLists) {
        List<TbWhiteList> tbWhiteListList = new ArrayList<>();
        for (int i = 0; i < tbWhiteLists.length; i++) {
            tbWhiteListList.add(tbWhiteLists[i]);
        }
        return chargeDao.delWhiteList(tbWhiteListList);
    }
}
