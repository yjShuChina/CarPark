package com.example.carpark.service.impl;

import com.example.carpark.dao.MonthDao;
import com.example.carpark.dao.RevenueDao;
import com.example.carpark.javabean.ResultDate;
import com.example.carpark.javabean.TbMonthChargeParameter;
import com.example.carpark.javabean.TbRevenue;
import com.example.carpark.service.RevenueService;
import com.example.carpark.util.ApplicationContextHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class RevenueServiceImpl implements RevenueService {

    @Resource
    private RevenueDao revenueDao;
    @Resource
    private MonthDao monthDao;

    /**
     * 分页查询收支明细表
     * @param map
     * @return
     */
    @Override
    public ResultDate<TbRevenue> findRevenueByPage(Map<String, Object> map) {
        ResultDate<TbRevenue> rd = ApplicationContextHelper.getBean(ResultDate.class);
        rd.setCode(0);
        rd.setData(revenueDao.findRevenueByPage(map));
        rd.setCount(revenueDao.findRevenueCount(map));
        rd.setMsg("");
        System.out.println(rd.toString());
        return rd;
    }

    /**
     * 查询月缴产品
     * @return
     */
    @Override
    public List<TbMonthChargeParameter> findAllMonthParameter() {
        return monthDao.findMonthCharge();
    }

    /**
     * 插入收支明细表
     * @param tbRevenue
     * @return
     */
    @Override
    public String addRevenue(TbRevenue tbRevenue) {
        return revenueDao.insert(tbRevenue) >0 ? "success":"error";
    }

    /**
     * 根据月份查询月缴产品的价格
     * @param month
     * @return
     */
    @Override
    public BigDecimal selectPriceByMonth(Integer month) {
        return new BigDecimal(monthDao.selectPriceByMonth(month));
    }
}
