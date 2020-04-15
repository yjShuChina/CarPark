package com.example.carpark.service.impl;

import com.example.carpark.dao.MonthDao;
import com.example.carpark.dao.RevenueDao;
import com.example.carpark.javabean.CountData;
import com.example.carpark.javabean.ResultDate;
import com.example.carpark.javabean.TbMonthChargeParameter;
import com.example.carpark.javabean.TbRevenue;
import com.example.carpark.service.RevenueService;
import com.example.carpark.util.ApplicationContextHelper;
import com.example.carpark.util.MonthUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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

    @Override
    public Integer deleteRevenueById(Integer revenueId) {
        return revenueDao.deleteByPrimaryKey(revenueId);
    }

    @Override
    public TbRevenue findRevenueById(Integer revenueId) {
        return revenueDao.selectByPrimaryKey(revenueId);
    }

    @Override
    public String updateRevenue(TbRevenue tbRevenue) {
        return revenueDao.updateByPrimaryKey(tbRevenue) > 0 ? "success":"error";
    }

    @Override
    public Map<String, Object> queryNearlySevenDays() {
        Map<String,Object> map = new HashMap<>();
        map.put("phone",revenueDao.queryNearlySevenDays("phone"));
        map.put("auto",revenueDao.queryNearlySevenDays("auto"));
        map.put("manual",revenueDao.queryNearlySevenDays("manual"));
        return map;
    }

    @Override
    public Map<String, Object> queryNearlyMonth() {
        List<String> list = MonthUtil.monthUtil();
        Map<String,Object> map = new HashMap<>(),map2 = new HashMap<>();
        map.put("incomeType","phone");
        List<CountData> list2 = new ArrayList<>(),list3 = new ArrayList<>(),list4 = new ArrayList<>();
        for (int i = 0;i < list.size();i ++) {
            map.put("start",list.get(i).substring(0,list.get(i).indexOf("~")).trim());
            map.put("end",list.get(i).substring(list.get(i).indexOf("~")+1).trim());
            CountData countData = ApplicationContextHelper.getBean(CountData.class);
            BigDecimal counts = revenueDao.queryNearlyMonth(map);
            countData.setCounts(counts == null ? new BigDecimal(0) : counts);
            countData.setDatas(list.get(i));
            list2.add(countData);
        }
        map2.put("phone",list2);
        map.put("incomeType","auto");
        for (int i = 0;i < list.size();i ++) {
            map.put("start",list.get(i).substring(0,list.get(i).indexOf("~")).trim());
            map.put("end",list.get(i).substring(list.get(i).indexOf("~")+1).trim());
            CountData countData = ApplicationContextHelper.getBean(CountData.class);
            BigDecimal counts = revenueDao.queryNearlyMonth(map);
            countData.setCounts(counts == null ? new BigDecimal(0) : counts);
            countData.setDatas(list.get(i));
            list3.add(countData);
        }
        map2.put("auto",list3);
        map.put("incomeType","manual");
        for (int i = 0;i < list.size();i ++) {
            map.put("start",list.get(i).substring(0,list.get(i).indexOf("~")).trim());
            map.put("end",list.get(i).substring(list.get(i).indexOf("~")+1).trim());
            CountData countData = ApplicationContextHelper.getBean(CountData.class);
            BigDecimal counts = revenueDao.queryNearlyMonth(map);
            countData.setCounts(counts == null ? new BigDecimal(0) : counts);
            countData.setDatas(list.get(i));
            list4.add(countData);
        }
        map2.put("manual",list4);
        return map2;
    }

    /**
     * 查询当年不同渠道收入
     * @return
     */
    @Override
    public Map<String, Object> queryCurYearBySeason() {
        Map<String,Object> map = new HashMap<>();
        map.put("phone",revenueDao.queryCurYearBySeason("phone"));
        map.put("auto",revenueDao.queryCurYearBySeason("auto"));
        map.put("manual",revenueDao.queryCurYearBySeason("manual"));
        return map;
    }

    /**
     * 查询当年不同渠道收入
     * @return
     */
    @Override
    public Map<String, Object> queryCurYearByMonth() {
        Map<String,Object> map = new HashMap<>();
        map.put("phone",revenueDao.queryCurYearByMonth("phone"));
        map.put("auto",revenueDao.queryCurYearByMonth("auto"));
        map.put("manual",revenueDao.queryCurYearByMonth("manual"));
        return map;
    }

    /**
     * 查询不同月缴产品收入情况
     * @return
     */
    @Override
    public Map<String, Object> queryMonthRevenue() {
        List<TbMonthChargeParameter> lis = monthDao.findMonthCharge();
        List<String> stringList = new ArrayList<>();
        List<Map> valueList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        for (TbMonthChargeParameter t:lis) {
            CountData countDatas = revenueDao.queryMonthRevenue((int) t.getMonth());
            countDatas.setDatas(countDatas.getDatas()+"月");
            stringList.add(countDatas.getDatas());
            Map<String,Object> map2 = new HashMap<>();
            map2.put("value",countDatas.getCounts());
            map2.put("name",countDatas.getDatas());
            valueList.add(map2);
        }
        map.put("nameArr",stringList);
        map.put("valueArr",valueList);
        return map;
    }
}
