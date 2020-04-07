package com.example.carpark.service.impl;

import com.example.carpark.dao.AdminDao;
import com.example.carpark.javabean.TbAdmin;
import com.example.carpark.service.AdminService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
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
}
