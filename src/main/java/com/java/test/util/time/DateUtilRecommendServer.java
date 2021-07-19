package com.java.test.util.time;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author zhang
 */
public class DateUtilRecommendServer {
    private DateUtilRecommendServer() {
    }

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

    public static String dateToStr(Date date, String format, String timeZone) {

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));

        return sdf.format(date);
    }

    public static String getNowDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

}
