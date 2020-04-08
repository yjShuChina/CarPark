package com.example.carpark.service.impl;

import com.example.carpark.dao.AdminDao;
import com.example.carpark.javabean.TbAdmin;
import com.example.carpark.javabean.TbCashier;
import com.example.carpark.service.AdminService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员service层
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminDao adminDao;

    @Override
    public String adminLogin(Map<String,Object> map) {
        TbAdmin tbAdmin2 = adminDao.adminLogin(map);
        if(tbAdmin2 != null){
            return "验证成功";
        }
        return "账号或密码错误";
    }

    /**
     *  林堂星——用户管理
     */
    @Override
    @Transactional
    public List<TbCashier> findAll(String username, String star_time, String end_time, int currentPage, int pageSize)
    {
        Map parameters=new HashMap<>();
        parameters. put( "username" , username);
        parameters. put( "currentPage" , (currentPage-1)*pageSize);
        parameters. put( "pageSize" , pageSize);
        List<TbCashier> list=adminDao.findAll(parameters);
        return list;
    }
    @Override
    @Transactional
    public int findCount(String username, String star_time, String end_time)
    {
        Map parameters=new HashMap<>();
        parameters. put( "username" , username);
        int count=adminDao.findCount(parameters);
        return count;
    }
    @Override
    @Transactional
    public int forbiddenState(String stateId)
    {
        int count=0;
        if (stateId!=null){
            Map parameters=new HashMap<>();
            parameters. put( "username" , stateId);
            count=adminDao.forbiddenState(parameters);
        }else {
            count =2;
        }
        return count;
    }

    @Override
    @Transactional
    public int openState(String stateId)
    {
        int count=0;
        if (stateId!=null){
            Map parameters=new HashMap<>();
            parameters. put( "username" , stateId);
            count=adminDao.openState(parameters);
        }else {
            count =2;
        }
        return count;
    }
}
