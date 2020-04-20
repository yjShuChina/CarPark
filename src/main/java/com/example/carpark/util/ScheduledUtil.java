package com.example.carpark.util;

import com.example.carpark.dao.AdminDao;
import com.example.carpark.javabean.TbUser;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 定时任务
 */
@Component
public class ScheduledUtil {
    @Resource
    private AdminDao adminDao;

    @Scheduled(cron = " * * * * ?")
    public void ScheduledMethod(){
        List<TbUser> userList = adminDao.selectExpiringUsers();//查询所有即将到期的用户
        if(userList.size() > 0){
            System.out.println("即将到期的用户有====>");
            for (TbUser tbUser: userList) {
                System.out.println("["+tbUser.getUserName()+"]、");
            }
        }else {
            System.out.println("当前无即将到期的用户");
        }
    }
}
