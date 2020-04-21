package com.example.carpark.util;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonthUtil {
    public static List monthUtil(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal_1=Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, -1);
        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        String firstDay = format.format(cal_1.getTime());
        System.out.println("-----1------firstDay:"+firstDay);
        // Java8  LocalDate
        LocalDate date = LocalDate.parse(firstDay);

        // 该月最后一天
        LocalDate lastDay = date.with(TemporalAdjusters.lastDayOfMonth());
        // 该月的第一个周一
        LocalDate start = date.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));

        List<String> list = new ArrayList<>();

        // 处理每个月的1号不是周一的情况
        if (!firstDay.equals(start)) {

            StringBuilder strbur = new StringBuilder();
            strbur.append(firstDay.toString())
                    .append("~")
                    .append(start.plusDays(-1).toString());
            list.add(strbur.toString());
        }

        while (start.isBefore(lastDay)) {

            StringBuilder strbur = new StringBuilder();
            strbur.append(start.toString());

            LocalDate temp = start.plusDays(6);
            if (temp.isBefore(lastDay)) {

                strbur.append("~")
                        .append(temp.toString());
            } else {

                strbur.append("~")
                        .append(lastDay.toString());
            }

            list.add(strbur.toString());
            start = start.plusWeeks(1);
        }

        System.out.println(list.toString());
        return list;
    }
}
