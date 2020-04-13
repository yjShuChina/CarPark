package com.example.carpark.service.impl;

import com.example.carpark.dao.ManageMonthDao;
import com.example.carpark.javabean.PageBean;
import com.example.carpark.javabean.TbMonthChargeParameter;
import com.example.carpark.service.ManageMonthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class ManageMonthServiceImpl implements ManageMonthService {

    @Resource
    private ManageMonthDao manageMonthDao;

    @Override//月缴参数表查询
    public List<TbMonthChargeParameter> findMonthByPage(HashMap<String, Object> condition) {
        return manageMonthDao.findMonthByPage(condition);
    }

    @Override//月缴参数表分页数量
    public int findMonthCount() {
        return manageMonthDao.findMonthCount();
    }

    @Override//增加月缴参数
    public int addMonth(TbMonthChargeParameter tbMonthChargeParameter) {
        return manageMonthDao.addMonth(tbMonthChargeParameter);
    }

    @Override//删除月缴参数
    public int delMonthById(int mcpId) {
        return manageMonthDao.delMonthById(mcpId);
    }

    @Override//修改月缴参数
    public int alterMonth(TbMonthChargeParameter tbMonthChargeParameter) {
        return manageMonthDao.alterMonth(tbMonthChargeParameter);
    }
}
