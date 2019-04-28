package com.pacia.ptms.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类
 * Created by p on 2018/05/24.
 */

public class DateUtils {
    private static final String TAG = "DateUtils";

    /**
     * 获得当前时间戳
     *
     * @return
     */
    public static long getCurrentTimeMillis() {
        long time = System.currentTimeMillis();
        return time;
    }

    /**
     * 获取当前日期
     *
     * @param pattern
     * @return
     */
    public static String getNowDate(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String nowDate = sdf.format(new Date());
        return nowDate;
    }

    public static String getNowDate() {
        return getNowDate("yyyy-MM-dd");
    }

    /**
     * 日期转换为时间戳
     *
     * @param sdate
     * @return
     */
    public static long dateToTimeStamp(String sdate) {
        return dateToTimeStamp(sdate, false);
    }

    public static long dateToTimeStamp(String sdate, boolean isSpec) {
        SimpleDateFormat sdf;
        if (isSpec) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }
        Date date;
        try {
            date = sdf.parse(sdate);
            return date.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long yearToTimeStamp(String sdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date;
        try {
            date = sdf.parse(sdate);
            return date.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将时间戳转换为时间
     *
     * @param parent
     * @return
     */
    public static String timeStampToDate(String timeStr, String parent) {
        SimpleDateFormat sdf = new SimpleDateFormat(parent);
        Date date;
        try {
            long time = Long.valueOf(timeStr);
            date = new Date(time);
            return sdf.format(date);
        } catch (Exception e) {
            return timeStr;
        }
    }

    public static String timeStampToDate(String time) {
        return timeStampToDate(time, "yyyy-MM-dd");
    }

    /**
     * 当前年
     *
     * @return
     */
    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 当前月
     *
     * @return
     */
    public static int getMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 当前日
     *
     * @return
     */
    public static int getDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 根据月份计算总天数
     *
     * @param month
     * @return
     */
    public static int getTotalDays(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 设置年月日
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String setDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        calendar.set(year, month - 1, day, 0, 0);
        return sdf.format(calendar.getTime());
    }

    public static String setOnDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        calendar.set(year, month - 1, day);
        return sdf.format(calendar.getTime());
    }

    /**
     * 判断日期大小
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isDateBigger(String str1, String str2) {
        boolean isBigger;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (dt1.getTime() >= dt2.getTime()) {
            isBigger = true;
        } else {
            isBigger = false;
        }
        return isBigger;
    }

    //获取两个日期之间的所有日期
    public static List<String> betweenDate(String date1, String date2) {
        List<String> list = new ArrayList<>();
        list.add(date1);
        if (!date1.equals(date2)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date d1 = sdf.parse(date1);
                Date d2 = sdf.parse(date2);
                Calendar c = Calendar.getInstance();
                c.setTime(d1);
                System.out.println(sdf.format(c.getTime()));//打出第一天的
                do {
                    c.add(Calendar.DATE, 1);//日期加1
                    list.add(sdf.format(c.getTime()));
                    System.out.println(sdf.format(c.getTime()));
                }
                while (!c.getTime().equals(d2));//直到和第二个日期相等，跳出循环
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}