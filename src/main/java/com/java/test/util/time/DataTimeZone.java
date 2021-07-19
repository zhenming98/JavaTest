package com.java.test.util.time;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @ClassName TimeZone
 * @Author yzm
 * @Date 2020/7/23 - 11:05
 * @Email yzm@ogawatech.com.cn
 */
public class DataTimeZone {

    public static void main(String[] args) throws ParseException {

//        Date nowData = new Date();
//        System.out.println("------------------------现在时区" + TimeZone.getDefault().getID());
//        System.out.println("------------------------北京时间" + nowData);
//        System.out.println("-----------------------------------------------------------------------------------------");
//
        test("America/New_York");
        test("GMT-4");
//
//        nowData = new Date();
//        System.out.println("------------------------现在时区" + TimeZone.getDefault().getID());
//        System.out.println("------------------------北京时间" + nowData);

//        TimeZone.setDefault(TimeZone.getTimeZone("EDT"));
//        Calendar calendar = Calendar.getInstance();
//        Date date = calendar.getTime();
//        System.out.println(date);

    }

    public static void test(String timeZone) {
        System.out.println("-------------      -----" + timeZone + " " + isExist(timeZone));
        switch (getDateArea(timeZone)) {
            case 1:
                System.out.println("------------------------属于: 5---------------8");
                break;
            case 2:
                System.out.println("------------------------属于: 12-------------14");
                break;
            case 3:
                System.out.println("------------------------属于: 20--------------5");
                break;
            case 4:
                System.out.println("------------------------其他时间");
                break;
            default:
                break;
        }
        System.out.println("-----------------------------------------------------------------------------------------");
    }

    public static boolean isExist(String timeZone) {
        String[] ids = TimeZone.getAvailableIDs();
//        for (String s:ids){
//            System.out.print(s + "  ");
//        }
//        System.out.println(Arrays.asList(ids));
        boolean isExist = Arrays.asList(ids).contains(timeZone);
        return isExist;
    }

    public static int getDateArea(String timeZone) {
        if (StringUtils.isBlank(timeZone)) {
            timeZone = "GMT+8:00";
        }

        String defaultTimeZone = TimeZone.getDefault().getID();

        long date = System.currentTimeMillis();

        TimeZone userTimeZone = TimeZone.getTimeZone(timeZone);
        if ("GMT".equals(userTimeZone) && !"GMT".equals(timeZone) && !"GMT0".equals(timeZone) && !"GMT+0:00".equals(timeZone) && !"GMT-0:00".equals(timeZone)) {
            // 用户时区格式错误 使用服务器默认时区
            TimeZone.setDefault(TimeZone.getTimeZone(defaultTimeZone));
        } else {
            TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
        }

        Date nowDate = new Date(date);

        System.out.println("------------------------所在时区：" + timeZone);
        System.out.println("------------------------所在时间：" + nowDate);

        Calendar now = Calendar.getInstance();
        now.setTime(nowDate);
        now.set(Calendar.MINUTE, 59);
        now.set(Calendar.SECOND, 59);

        now.set(Calendar.HOUR_OF_DAY, 4);
        Date fiveHours = now.getTime();

        now.set(Calendar.HOUR_OF_DAY, 8);
        Date nineHours = now.getTime();

        now.set(Calendar.HOUR_OF_DAY, 11);
        Date twelveHours = now.getTime();

        now.set(Calendar.HOUR_OF_DAY, 13);
        Date fourteenHours = now.getTime();

        now.set(Calendar.HOUR_OF_DAY, 19);
        Date twentyHours = now.getTime();

        TimeZone.setDefault(TimeZone.getTimeZone(defaultTimeZone));

        if (nowDate.before(nineHours) && nowDate.after(fiveHours)) {
            return 1;
        } else if (nowDate.before(fourteenHours) && nowDate.after(twelveHours)) {
            return 2;
        } else if (nowDate.after(twentyHours) || nowDate.before(fiveHours)) {
            return 3;
        } else {
            return 4;
        }
    }

}
