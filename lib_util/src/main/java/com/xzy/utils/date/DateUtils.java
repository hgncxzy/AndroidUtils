package com.xzy.utils.date;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 常用单位转换的辅助类。
 */
@SuppressWarnings("ALL")
@SuppressLint("SimpleDateFormat")
public class DateUtils {

    private static final String TAG = "DateHelper";

    // 返回日期时间格式化对象
    public static SimpleDateFormat datetimeFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 返回日期时间格式化对象
     *
     * @return 日期时间格式化对象
     */
    public static SimpleDateFormat noSecondFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }

    /**
     * 从字符串中解析日期时间
     *
     * @param s 时间字符串
     * @return date 字符串对应的日期
     */
    public static Date datetimeFromString(String s) {
        try {
            return datetimeFormat().parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    /**
     * 返回短时间字符串格式 yyyy-MM-dd
     *
     * @param date 日期
     * @return 短时间字符串
     */
    public static String getStringDateShort(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 将日期时间格式化为字符串
     *
     * @param date 日期
     * @return 日期字符串
     */
    public static String datetimeToString(Date date) {
        return date == null ? "" : datetimeFormat().format(date);
    }

    /**
     * 将日期时间格式化为字符串(不含秒)
     *
     * @param date 日期
     * @return 不含秒的日期字符串
     */
    public static String datetimeToNoSecond(Date date) {
        return date == null ? "" : noSecondFormat().format(date);
    }

    /**
     * 日期格式转换yyyy-MM-dd'T'HH:mm:ss.SSS  (yyyy-MM-dd'T'HH:mm:ss.SSS Z) TO  yyyy-MM-dd HH:mm:ss
     *
     * @param oldDateStr 原日期字符串
     * @return 新格式的日期字符串
     */
    public static Date dealDateFormat(String oldDateStr) {
        //yyyy-MM-dd'T'HH:mm:ss.SSS
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        Date date = null;
        try {
            date = format.parse(oldDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * GMT 时间格式转换
     *
     * @return String 制定格式的时间字符串
     */
    public static String dateTimeFromGMT() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                new Locale("CHINESE", "CHINA"));
        formatter.applyPattern("yyyy年MM月dd日 HH:mm");
        return formatter.format(new Date());
    }

    /**
     * 将时间间隔转换为可读信息
     *
     * @param date    传入的时间
     * @param nowTime 现在的时间
     * @return 可读信息的字符串
     */
    public static String humanInterval(Date date, Date nowTime) {
        if (date == null) return "";
        // long diff = nowTime - date.getTime();
        long diff = nowTime.getTime() - date.getTime();

        diff /= 1000;
        if (diff < 60) return "刚刚";
        if (diff < 3600) return diff / 60 + "分钟前";
        if (diff > 3600 && diff < 3600 * 24) return diff / 3600 + "小时前";
        if (diff < 3600 * 24 * 7 && diff > 3600 * 24) return diff / (3600 * 24) + "天前";
        return getStringDateShort(date);
    }

    /**
     * 将时间间隔转换为可读信息
     *
     * @param date 传入的时间
     * @return String 可读信息的字符串
     */
    public static String humanInterval(Date date) {
        return humanInterval(date, new Date());
    }


    /**
     * 判断是否在同一分钟内
     *
     * @param one 时间1
     * @param tow 时间2
     * @return boolean
     */
    public static boolean inSameMinute(Date one, Date tow) {
        if ((one == null) || (tow == null)) return false;
        return one.getTime() / 60000 == tow.getTime() / 60000;
    }

    /**
     * 判断是否在同一分钟内
     *
     * @param one 毫秒1
     * @param tow 毫秒2
     * @return boolean
     */
    public static boolean inSameMinuteByLong(long one, long tow) {
        return one / 60000 == tow / 60000;
    }

    /**
     * 最小日期 1900-01-01
     *
     * @return 最小的日期
     */
    public static Date minDate() {
        Date result = new Date();
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(1900, 1, 1);
        result.setTime(cal.getTimeInMillis());
        return result;
    }

    /**
     * 获取当前时间
     *
     * @return 当前时间
     */
    public static String getNowTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        return dateFormat.format(new Date());
    }

    /**
     * 以周日开始获取本周开始的时间戳
     *
     * @param calendar 日历对象
     * @return 以周日开始获取本周开始的时间戳
     */
    public static String getWeekStartTime(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd",
                Locale.getDefault());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 本周结束的时间戳
     *
     * @param calendar 日历对象
     * @return 本周结束的时间戳
     */
    public static String getWeekEndTime(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd",
                Locale.getDefault());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * start
     * 本周开始时间戳 - 以星期一为本周的第一天
     *
     * @return 本周开始时间戳
     */
    public static String getWeekStartTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd",
                Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        cal.add(Calendar.DATE, -day_of_week + 1);
        return simpleDateFormat.format(cal.getTime());
    }


    /**
     * end
     * 本周结束时间戳 - 以星期一为本周的第一天
     *
     * @return 本周结束时间戳
     */
    public static String getWeekEndTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd",
                Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        cal.add(Calendar.DATE, -day_of_week + 7);
        return simpleDateFormat.format(cal.getTime());
    }

    /**
     * 以周日开始获取本周开始的时间戳
     *
     * @param calendar 日历对象
     * @return 以周日开始获取本周开始的时间戳
     */
    public static String getWeekStartTimeNoYear(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd",
                Locale.getDefault());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 本周结束的时间戳
     *
     * @param calendar 日历对象
     * @return 本周结束后的时间戳
     */
    public static String getWeekEndTimeNoYear(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd",
                Locale.getDefault());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        return simpleDateFormat.format(calendar.getTime());
    }
}