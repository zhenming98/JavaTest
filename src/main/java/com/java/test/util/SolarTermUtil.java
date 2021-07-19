package com.java.test.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 通式寿星公式：[Y×D+C]-L
 * [  ]里面取整数
 * Y=年数的后2位数；
 * D=0.2422；
 * L=Y/4，小寒、大寒、立春、雨水的L=(Y-1)/4
 * <p>
 * 计算公历2019年的立春日期 [19 × 0.2422 + 3.87] - [(19 - 1) / 4] = 8 - 4 = 4，则2月4日立春
 * 计算公历2020年的大暑日期 [20 × 0.2422 + 22.83] - [20 / 4] = 27 - 5 = 22，   则7月22日大暑
 * 计算公历2008年的小满日期 [8 × 0.2422 + 21.04] - [8 / 4] = 22 - 2 = 20，     因为2008年小满的计算结果加1日，因此5月21小满
 * 计算公历2000年的小寒日期 [0 × 0.2422 + 6.11] - [(0 - 1) / 4] = 6 - 0 = 6，  则1月6日小寒，注：公历2000年的小寒按照20世纪的C值来算
 *
 * @author yzm
 * @date 2021/6/28 - 16:30
 */
public class SolarTermUtil {

    public final static double D_VALUE = 0.2422;

    /**
     * 节气
     */
    public final static String[] SOLAR_TERM_NAME = {
            "小寒", "大寒", "立春", "雨水", "惊蛰", "春分",
            "清明", "谷雨", "立夏", "小满", "芒种", "夏至",
            "小暑", "大暑", "立秋", "处暑", "白露", "秋分",
            "寒露", "霜降", "立冬", "小雪", "大雪", "冬至"};

    /**
     * 21世纪（除2000年）节气 C 值
     */
    public final static double[] C_VALUES = {
            5.4055, 20.12, 3.87, 18.73, 5.63, 20.646,
            4.81, 20.1, 5.52, 21.04, 5.678, 21.37,
            7.108, 22.83, 7.5, 23.13, 7.646, 23.042,
            8.318, 23.438, 7.438, 22.36, 7.18, 21.94
    };

    public static String solarTermDate() throws ParseException {
        return solarTermDate(new Date());
    }

    public static String solarTermDate(Date date) throws ParseException {
        String strDateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

        int y = calculateY(date);
        int l = calculateL(date);

        for (int i = 0; i < C_VALUES.length; i++) {
            int d = (int) Math.floor(y * D_VALUE + C_VALUES[i]) - l;
            d = dealD(SOLAR_TERM_NAME[i], date, d);

            int month = ((int) Math.floor((double) i / 2)) + 1;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);

            String dataValue = year + "-" + month + "-" + d;

            if (sdf.parse(dataValue).equals(sdf.parse(sdf.format(date)))) {
                return SOLAR_TERM_NAME[i];
            }
        }
        return null;
    }

    private static int dealD(String solarTermName, Date date, int d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        switch (solarTermName) {
            case "雨水":
                if (year == 2026) {
                    d -= 1;
                }
                break;
            case "春分":
                if (year == 2084) {
                    d += 1;
                }
                break;
            case "小满":
                if (year == 2008) {
                    d += 1;
                }
                break;
            case "小暑":
                if (year == 2016) {
                    d += 1;
                }
                break;
            case "立秋":
                if (year == 2002) {
                    d += 1;
                }
                break;
            case "霜降":
                if (year == 2089) {
                    d += 1;
                }
                break;
            case "立冬":
                if (year == 2089) {
                    d += 1;
                }
                break;
            case "冬至":
                if (year == 2021) {
                    d -= 1;
                }
                break;
            case "小寒":
                if (year == 2019) {
                    d -= 1;
                }
                break;
            case "大寒":
                if (year == 2082) {
                    d += 1;
                }
                break;
            default:
        }
        return d;
    }

    private static int calculateY(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        return Integer.parseInt(year.substring(2));
    }

    private static int calculateL(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int y = Integer.parseInt(String.valueOf(calendar.get(Calendar.YEAR)).substring(2));
        int m = calendar.get(Calendar.MONTH);
        //小寒、大寒、立春、雨水的L = ( Y - 1 ) / 4
        if (m == 1 || m == 2) {
            return (y - 1) / 4;
        } else {
            return y / 4;
        }
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2022-8-7");
        System.out.println(solarTermDate(date));
    }


}
