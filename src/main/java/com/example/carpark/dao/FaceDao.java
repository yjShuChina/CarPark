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

    //管理员人脸登陆验证
    public List<TbAdmin> adminLoginFace();

    //添加收费员人脸
    public int addChargeFace(TbCashier tbCashier);

    //收费员人脸登陆验证
    public List<TbCashier> chargeLoginFace();

    //通过收费员账户,查询收费员信息
    public TbCashier findChargeByAccount(String cashierAccount);
}
