package com.example.carpark.dao;

import com.example.carpark.javabean.TbAdmin;
import com.example.carpark.javabean.TbCashier;
import com.example.carpark.javabean.TbReceivable;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FaceDao
{
    //添加管理员人脸
    public int addAdminFace(TbAdmin tbAdmin);

    //通过管理员账户,查询管理员信息
    public TbAdmin findAdminByAccount(String adminAccount);

    //添加收费员人脸
    public int addChargeFace(TbCashier tbCashier);

    //通过收费员账户,查询收费员信息
    public TbCashier findChargeByAccount(String cashierAccount);
}
