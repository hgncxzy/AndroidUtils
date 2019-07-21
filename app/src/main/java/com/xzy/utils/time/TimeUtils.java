package com.xzy.utils.time;

import android.annotation.SuppressLint;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * 关于时间的工具类。
 * @author xzy
 */
@SuppressWarnings("unused")
@SuppressLint("SimpleDateFormat")
public class TimeUtils {

    private static final long ONE_MINUTE_MILLIONS = 60 * 1000;
    private static final long ONE_HOUR_MILLIONS = 60 * ONE_MINUTE_MILLIONS;

    private static SimpleDateFormat sdf = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    /**
     * 得到剩余天数
     *
     * @param endTime 结束时间
     * @return 剩余天数
     */
    public static int getDayLast(String endTime) {
        try {
            long nowTime = new Date().getTime();
            long lastTime = Objects.requireNonNull(new SimpleDateFormat("yyyy-MM-dd")
                    .parse(endTime)).getTime();

            long distance = lastTime - nowTime;
            if (distance <= 0) {
                // 如果是小于或等于0，则为0
                return 0;
            }
            double rate = distance / (1.0f * 24 * 3600 * 1000);
            return (int) (rate + 0.5f);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }


    /**
     * 获取短时间格式
     *
     * @return 短时间字符串
     */
    public static String getShortTime(long millis) {
        Date date = new Date(millis);
        Date curDate = new Date();
        String str;
        long durTime = curDate.getTime() - date.getTime();
        int dayStatus = calculateDayStatus(date, new Date());
        if (durTime <= 10 * ONE_MINUTE_MILLIONS) {
            str = "刚刚";
        } else if (durTime < ONE_HOUR_MILLIONS) {
            str = durTime / ONE_MINUTE_MILLIONS + "分钟前";
        } else if (dayStatus == 0) {
            str = durTime / ONE_HOUR_MILLIONS + "小时前";
        } else if (dayStatus == -1) {
            str = "昨天" + DateFormat.format("HH:mm", date);
        } else if (isSameYear(date, curDate) && dayStatus < -1) {
            str = DateFormat.format("MM-dd", date).toString();
        } else {
            str = DateFormat.format("yyyy-MM", date).toString();
        }
        return str;
    }

    /**
     * 判断是否是同一年
     *
     * @param targetTime  日期
     * @param compareTime 日期
     * @return boolean ，返回 true 表示是同一年，返回 false 表示不是同一年
     */
    public static boolean isSameYear(Date targetTime, Date compareTime) {
        Calendar tarCalendar = Calendar.getInstance();
        tarCalendar.setTime(targetTime);
        int tarYear = tarCalendar.get(Calendar.YEAR);

        Calendar compareCalendar = Calendar.getInstance();
        compareCalendar.setTime(compareTime);
        int comYear = compareCalendar.get(Calendar.YEAR);

        return tarYear == comYear;
    }

    /**
     * 判断是否处于今天还是昨天，0表示今天，-1表示昨天，小于-1则是昨天以前
     *
     * @param targetTime  日期
     * @param compareTime 日期
     * @return int  0表示今天，-1表示昨天，小于-1则是昨天以前
     */
    public static int calculateDayStatus(Date targetTime, Date compareTime) {
        Calendar tarCalendar = Calendar.getInstance();
        tarCalendar.setTime(targetTime);
        int tarDayOfYear = tarCalendar.get(Calendar.DAY_OF_YEAR);

        Calendar compareCalendar = Calendar.getInstance();
        compareCalendar.setTime(compareTime);
        int comDayOfYear = compareCalendar.get(Calendar.DAY_OF_YEAR);

        return tarDayOfYear - comDayOfYear;
    }

    /**
     * 将秒数转换成00:00的字符串，如 118秒 -> 01:58
     *
     * @param time 传入的秒数
     * @return 00:00 的字符串
     */
    public static String secToTime(int time) {
        String timeStr;
        int hour;
        int minute;
        int second;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":"
                        + unitFormat(second);
            }
        }
        return timeStr;
    }

    /**
     * 单位转换
     *
     * @param i 需要转转的数字
     * @return 转换后的字符串
     */
    public static String unitFormat(int i) {
        String retStr;
        if (i >= 0 && i < 10)
            retStr = "0" + i;
        else
            retStr = "" + i;
        return retStr;
    }


    /**
     * 获取当前月1号  返回格式yyyy-MM-dd (eg: 2007-06-01)
     *
     * @return String
     */
    public static String getMonthBegin() {
        String yearMonth = new SimpleDateFormat(
                "yyyy-MM").format(new Date());
        return yearMonth + "-01";
    }


    /**
     * 与当前时间对比
     *
     * @param time 传入的时间
     * @return long 和当前时间的时间差
     */
    public static long compareTime(String time) {
        long timeLong = 0;
        long curTimeLong = 0;

        try {
            timeLong = sdf.parse(time).getTime();
            curTimeLong = Objects.requireNonNull(sdf.parse(getCurrentTimeString()))
                    .getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 当前时间减去传入的时间
        return curTimeLong - timeLong;
    }

    /**
     * 返回yyyy-MM-dd HH:mm:ss类型的时间字符串
     */
    public static String getCurrentTimeString() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")//
                .format(new Date());
    }

    /**
     * @param milliseconds 时间值
     * @param isShowDetail 是否需要显示具体时间段 + 时分和星期 + 时分
     * @return String
     */
    public static String getTimeShowString(long milliseconds, boolean isShowDetail) {
        String dataString;
        String timeStringBy24;

        Date currentTime = new Date(milliseconds);
        Date today = new Date();
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        Date todaybegin = todayStart.getTime();
        Date yesterdaybegin = new Date(todaybegin.getTime() - 3600 * 24 * 1000);
        Date preyesterday = new Date(
                yesterdaybegin.getTime() - 3600 * 24 * 1000);

        if (!currentTime.before(todaybegin)) {
            dataString = "今天";
        } else if (!currentTime.before(yesterdaybegin)) {
            dataString = "昨天";
        } else if (!currentTime.before(preyesterday)) {
            dataString = "前天";
        } else if (isSameWeekDates(currentTime, today)) {
            dataString = getWeekOfDate(currentTime);
        } else {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd",
                    Locale.getDefault());
            dataString = dateFormatter.format(currentTime);
        }

        SimpleDateFormat timeFormatter24 = new SimpleDateFormat("HH:mm",
                Locale.getDefault());
        timeStringBy24 = timeFormatter24.format(currentTime);

        if (isShowDetail) {//显示具体的时间
            //在聊天界面显示时间，如果是今天则显示当前时间段加上时和分  如上午 9:58
            if (!currentTime.before(todaybegin)) {
                //如果是今天
                return getTodayTimeBucket(currentTime);//根据时间段分为凌晨 上午 下午等
            } else {
                //如果是昨天 则是 昨天 9：58 如果是同在一个星期，前天之前的时间则显示 星期一 9：58
                return dataString + " " + timeStringBy24;
            }
        } else {
            //在会话记录界面不需要展示很具体的时间
            if (!currentTime.before(todaybegin)) {//如果是今天
                return timeStringBy24;//直接返回时和分 如 9:58
            } else {
                return dataString;//如果不是今天，不需要展示时和分 如 昨天 前天 星期一
            }
        }
    }


    /**
     * 判断两个日期是否在同一周
     *
     * @param date1 日期 1
     * @param date2 日期 2
     * @return boolean 相同则返回 true，相反则返回 false
     */
    public static boolean isSameWeekDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (0 == subYear) {
            return cal1.get(Calendar.WEEK_OF_YEAR) == cal2
                    .get(Calendar.WEEK_OF_YEAR);
        } else if (1 == subYear && Calendar.DECEMBER == cal2.get(Calendar.MONTH)) {
            // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
            return cal1.get(Calendar.WEEK_OF_YEAR) == cal2
                    .get(Calendar.WEEK_OF_YEAR);
        } else if (-1 == subYear && Calendar.DECEMBER == cal1.get(Calendar.MONTH)) {
            return cal1.get(Calendar.WEEK_OF_YEAR) == cal2
                    .get(Calendar.WEEK_OF_YEAR);
        }
        return false;
    }

    /**
     * 根据日期获得星期
     *
     * @param date 传入的日期
     * @return String 星期字符串
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDaysName = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
                "星期六"};
        // String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
    }

    /**
     * 根据不同时间段，显示不同时间
     *
     * @param date 传入的日期
     * @return String 时间字符串
     */
    public static String getTodayTimeBucket(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat timeFormatter0to11 = new SimpleDateFormat("KK:mm",
                Locale.getDefault());
        SimpleDateFormat timeFormatter1to12 = new SimpleDateFormat("hh:mm",
                Locale.getDefault());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour < 5) {
            return "凌晨 " + timeFormatter0to11.format(date);
        } else if (hour >= 5 && hour < 12) {
            return "上午 " + timeFormatter0to11.format(date);
        } else if (hour >= 12 && hour < 18) {
            return "下午 " + timeFormatter1to12.format(date);
        } else if (hour >= 18 && hour < 24) {
            return "晚上 " + timeFormatter1to12.format(date);
        }
        return "";
    }

    /**
     * 获取当前时间 yyyy-MM-dd格式
     *
     * @return String
     */
    public static String getCurrentTimeYMD() {
        return new SimpleDateFormat("yyyy-MM-dd")//
                .format(new Date());
    }

    /**
     * 将yyyy-MM-dd的字符串转换成Date对象
     *
     * @param time 日期字符串
     * @return Date
     */
    public static Date getDateByYMD(String time) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
