package com.example.carpark.service;

import com.example.carpark.javabean.TbAdmin;
import com.example.carpark.javabean.TbCashier;
import com.example.carpark.javabean.TbTemporaryCarRecord;

import java.util.List;

public interface FaceService
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