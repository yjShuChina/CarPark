package com.example.carpark.util;

import com.example.carpark.dao.AdminDao;
import com.example.carpark.javabean.TbUser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 定时任务
 */
@Component
public class ScheduledUtil {
    @Resource
    private AdminDao adminDao;

    @Scheduled(cron = "0 0 0 */1 * ?")
    public void ScheduledMethod(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(df.format(new Date())+"    执行定时任务 ---- >[ScheduledUtil]:查询即将到期用户");
        List<TbUser> userList = adminDao.selectExpiringUsers();//查询所有即将到期的用户
        if(userList.size() > 0){
            System.out.print("即将到期的用户有---->");
            for (TbUser tbUser: userList) {
                System.out.print("["+tbUser.getUserTel()+"]、");
            }
            System.out.println();//换行
        }else {
            System.out.println("当前无即将到期的用户");
        }
    }
}
