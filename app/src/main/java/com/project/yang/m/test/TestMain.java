package com.project.yang.m.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @Author: NiYang
 * @Date: 2017/4/8.
 */
public class TestMain {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.YEAR));

        int currentYear = calendar.get(Calendar.YEAR);
        //取当前年的前60年
        List<String> yearList = new ArrayList<>();
        int startYear = currentYear - 60;
        for (int i = 0;i<=60;i++) {
            yearList.add(startYear + i + "年");
        }
        for (String num : yearList) {
            System.out.println(num + "\n");
        }

        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.MONTH, 1);
        System.out.println(calendar.get(Calendar.MONTH));
        System.out.println(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String str = "2016年";
        System.out.println(str.split("年")[0]);

        calendar.set(Calendar.YEAR,2015);
        System.out.println(calendar.get(Calendar.YEAR));

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY, 15);
        System.out.println(calendar1.get(Calendar.HOUR_OF_DAY));
        System.out.println(calendar1.get(Calendar.YEAR) + "年" + (calendar1.get(Calendar.MONTH) + 1) + "月" + calendar1.get(Calendar.DAY_OF_MONTH) + "日" + calendar1.get(Calendar.HOUR_OF_DAY) + "时" + calendar1.get(Calendar.MINUTE) + "分" + calendar1.get(Calendar.SECOND) + "秒");

        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        try {
            Date date = format.parse(calendar1.get(Calendar.YEAR) + "年" + (calendar1.get(Calendar.MONTH) + 1) + "月" + calendar1.get(Calendar.DAY_OF_MONTH) + "日" + calendar1.get(Calendar.HOUR_OF_DAY) + "时" + calendar1.get(Calendar.MINUTE) + "分" + calendar1.get(Calendar.SECOND) + "秒");
            System.out.println(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
