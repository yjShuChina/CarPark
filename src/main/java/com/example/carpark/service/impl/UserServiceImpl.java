package com.example.carpark.service.impl;

import com.example.carpark.dao.UserDao;
import com.example.carpark.javabean.TbUser;
import com.example.carpark.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public TbUser userLogin(TbUser tbUser) {
        return userDao.userLogin(tbUser);
    }
}
