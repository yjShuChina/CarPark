package com.example.carpark.service.impl;

import com.example.carpark.dao.AlipayDao;
import com.example.carpark.dao.FaceDao;
import com.example.carpark.javabean.TbAdmin;
import com.example.carpark.javabean.TbCashier;
import com.example.carpark.service.FaceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FaceServiceImpl implements FaceService {

    @Resource
    private FaceDao faceDao;

    @Override
    public int addAdminFace(TbAdmin tbAdmin) {
        return faceDao.addAdminFace(tbAdmin);
    }

    @Override
    public List<TbAdmin> adminLoginFace() {
        return faceDao.adminLoginFace();
    }

    @Override
    public int addChargeFace(TbCashier tbCashier) {
        return faceDao.addChargeFace(tbCashier);
    }

    @Override
    public List<TbCashier> chargeLoginFace() {
        return faceDao.chargeLoginFace();
    }

    @Override
    public TbCashier findChargeByAccount(String cashierAccount) {
        return faceDao.findChargeByAccount(cashierAccount);
    }
}
