package com.java.test.util.time;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author yzm
 */
public class DateUtil {

    private final static String SAMPLE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取年龄
     *
     * @param birthday 日期
     * @return 年龄
     */
    public static int getAge(Date birthday) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthday);
        if (now.before(birth)) {
            return 0;
        } else {
            int age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
            if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                age += 1;
            }
            return age;
        }
    }

    /**
     * 获取 date所在月 第一天 零点
     * @param date 时间
     * @return date
     */
    public static Date getMonthFirstDate(Date date) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        cale.set(Calendar.HOUR_OF_DAY, 0);
        cale.set(Calendar.MINUTE, 0);
        cale.set(Calendar.SECOND, 0);
        return cale.getTime();
    }

    /**
     * 获取 date所在月 最后一天 零点
     * @param date 时间
     * @return date
     */
    public static Date getMonthEndDate(Date date) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DATE));
        cale.set(Calendar.HOUR_OF_DAY, 23);
        cale.set(Calendar.MINUTE, 59);
        cale.set(Calendar.SECOND, 59);
        return cale.getTime();
    }

    /**
     * 今天零点时间
     * @return date
     */
    public static Date getDayFirst() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        //设置时为0点
        cal.set(Calendar.HOUR_OF_DAY, 0);
        //设置分钟为0分
        cal.set(Calendar.MINUTE, 0);
        //设置秒为0秒
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 天数加减
     * @param past 正数为以后 past 天 负数相反
     * @return date
     */
    public static Date getDayAfter(int past) {
        return getDayAfter(new Date(), past);
    }

    /**
     * 天数加减
     *
     * @param date 日期
     * @param past 正数为以后 past 天 负数相反
     * @return date
     */
    public static Date getDayAfter(Date date, int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, past);
        return calendar.getTime();
    }

    /**
     * 月份加减
     *
     * @param past 正数为以后 past 个月 负数相反
     * @return Date
     */
    public static Date getMonthAfter(int past) {
        return getMonthAfter(new Date(), past);
    }

    /**
     * 月份加减
     * @param date date
     * @param past 正数为以后 past 个月 负数相反
     * @return Date
     */
    public static Date getMonthAfter(Date date, int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, past);
        return calendar.getTime();
    }

    /**
     * date to str
     * @param date date
     * @return string
     */
    public static String dateToStr(Date date) {
        return dateToStr(date, SAMPLE_FORMAT);
    }

    /**
     * date to str
     * @param date date
     * @param format 格式
     * @return string
     */
    public static String dateToStr(Date date, String format) {
        return dateToStr(date, format, null);
    }

    /**
     * date to str
     * @param date date
     * @param format 格式
     * @param timeZone 时区
     * @return string
     */
    public static String dateToStr(Date date, String format, String timeZone) {
        if (StringUtils.isBlank(format)) {
            format = SAMPLE_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (StringUtils.isNotBlank(timeZone)) {
            sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        }
        return sdf.format(date);
    }

    /**
     * str to date
     * @param date string date
     * @return date
     */
    public static Date strToDate(String date) {
        return strToDate(date, SAMPLE_FORMAT);
    }

    /**
     * str to date
     * @param date string date
     * @param format string format
     * @return date
     */
    public static Date strToDate(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date);
        } catch (ParseException ignored) {

        }
        return null;
    }

}
