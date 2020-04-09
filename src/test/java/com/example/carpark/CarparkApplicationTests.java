package com.example.carpark;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@SpringBootTest
class CarparkApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) throws ParseException {
//        Date d = new Date();
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println("今天的日期：" + df.format(d));
//        System.out.println("两天前的日期：" + df.format(new Date(d.getTime() - (long) 2 * 24 * 60 * 60 * 1000)));
//        System.out.println("三天后的日期：" + df.format(new Date(d.getTime() + (long) 3 * 24 * 60 * 60 * 1000)));
//
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println("Today is:" + format.format(Calendar.getInstance().getTime()));

//        Calendar calendar = new GregorianCalendar();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH) + 1;
//        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(Calendar.MINUTE);
//        int second = calendar.get(Calendar.SECOND);
//        int week = calendar.get(Calendar.DAY_OF_WEEK);
//        System.out.println("本月最大天数=" + maxDay);
//        System.out.println("year=" + year + "    month=" + month + "  day=" + day + "  hour=" + hour + "    minute=" + minute + "    second=" + second + "    week=" + week);
//        System.out.println("本月月份=" + month);


//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//        String str="2020-04-01";
//        Date dt=sdf.parse(str);
//        Calendar rightNow = Calendar.getInstance();
//        rightNow.setTime(dt);
////        rightNow.add(Calendar.YEAR,-1);//日期减1年
//        rightNow.add(Calendar.MONTH,3);//日期加3个月
//        rightNow.add(Calendar.DAY_OF_YEAR,-1);//日期减1天
//        Date dt1=rightNow.getTime();
//        String reStr = sdf.format(dt1);
//        System.out.println(reStr);

//        String str="2020-04-09";
//        int time1 = 14;
//        String str1 = timeFactory(str,time1);
//        System.out.println("新时间="+str1);
    }

//    //封装用户VIP到期时间
//    public static String timeFactory(String nowTime,int addMonth) throws ParseException {
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//        Date dt=sdf.parse(nowTime);
//        Calendar rightNow = Calendar.getInstance();
//        rightNow.setTime(dt);
//        rightNow.add(Calendar.MONTH,addMonth);//充值月份
//        rightNow.add(Calendar.DAY_OF_YEAR,-1);//日期减1天
//        Date dt1=rightNow.getTime();
//        String newTime = sdf.format(dt1);
////        System.out.println(newTime);
//        return newTime;
//    }

}


