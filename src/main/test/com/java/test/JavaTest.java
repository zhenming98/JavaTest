package com.java.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yzm
 * @date 2021/5/16 - 11:55
 */
public class JavaTest {

    @Test
    public void test1() {
        String s1 = "123";
        String s2 = s1;
        //true
        System.out.println(s1.equals(s2));
        s1 = "456";
        //false
        System.out.println(s1.equals(s2));
    }

    @Test
    public void test2() {
        String userName = "13960278000";
        Pattern p = Pattern.compile("^(1[3-9]\\d{9}$)");
        Matcher m = p.matcher(userName);
        if (m.matches()) {
            System.out.println(true);
            return;
        }
        System.out.println(false);
    }

    @Test
    public void test3() {

        int day = 10;
        double count = 100.0;

        int sum = 0;
        int sum2 = 0;

        for (int i = 0; i < 10000; i++) {
            Double[] doubles1 = new Double[365];
            Double[] doubles2 = new Double[365];
            doubles2[0] = 50.0;

            Double[] doubles3 = new Double[365];
            doubles3[0] = 50.0;

            Double d = 50.0;
            Double d1 = 50.0;
            Random r = new Random();
            for (int j = 0; j < doubles1.length; j++) {
                doubles1[j] = r.nextDouble() * 4.0 / 100 - 0.02;
            }
            for (int x = 1; x < doubles2.length; x++) {
//                if (x % day == 0) {
                if (doubles1[x - 1] < 0) {
                    d += count;
                    doubles2[x - 1] = doubles2[x - 1] + count;
                }
                doubles2[x] = doubles2[x - 1] * (doubles1[x - 1] + 1);
            }
            for (int x = 1; x < doubles3.length; x++) {
                if (x % day == 0) {
                    d1 += count;
                    doubles3[x - 1] = doubles3[x - 1] + count;
                }
                doubles3[x] = doubles3[x - 1] * (doubles1[x - 1] + 1);
            }
//            System.out.println(d + "-------------" + doubles2[364] + " ************ " + (doubles2[364] - d) / d * 100);
            if ((doubles2[364] - d) / d * 100 > 0) {
                sum++;
            }
            if ((doubles3[364] - d) / d * 100 > 0) {
                sum2++;
            }
        }
        System.out.println(sum);
        System.out.println(sum2);

    }

    @Test
    public void test4() {
        List<Integer> test = new ArrayList<>();

        test.add(1);
        test.add(2);
        test.add(3);
        test.add(4);
        test.add(5);
        test.add(6);
        test.add(7);
        test.add(8);
        test.add(9);
        test.add(10);

        for (int i = 0; i < test.size(); i++) {
            if (test.get(i) % 2 == 0) {
                test.remove(i);
                break;
            }
        }

        System.out.println(test);
    }

    @Test
    public void test5() {
        int old = 20;
        int newInt = old + (old >> 1);
        System.out.println((old >> 1));
    }

    @Test
    public void test6() {
        /*
        OGA85982012010195
        OGA85982101150030
        OGA85982102200129
        OG7598X1903300030
        OGA85982101150207
        OGA85982005050027
        OGA85982012100055
        OGA85982005200008
        OGA85982103150135
         */
        String sn = "OGA8598210115030";
        System.out.println(sn);
        if (StringUtils.isNotBlank(sn) && sn.length() == 16) {
            StringBuilder stringBuilder = new StringBuilder(sn);
            stringBuilder.insert(13, '0');
            sn = stringBuilder.toString();
        }
        System.out.println(sn);
    }

    /**
     * 获取y年第n个节气的日期，从0开始算第一个
     */
    @Test
    public void sTermDate() {
        String[] solarTerm = {"小寒", "大寒", "立春", "雨水", "惊蛰", "春分",
                "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑", "大暑", "立秋", "处暑", "白露",
                "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至"};
        List<String> solarTermList = Arrays.asList(solarTerm);
        int[] sTermInfo = {0, 21208, 42467, 63836, 85337, 107014,
                128867, 150921, 173149, 195551, 218072, 240693, 263343, 285989,
                308563, 331033, 353350, 375494, 397447, 419210, 440795, 462224,
                483532, 504758};

        int y = 2021;
        String s = "芒种";
        int n = solarTermList.indexOf(s);

        Calendar cal = Calendar.getInstance();
        cal.set(1900, 0, 6, 2, 5, 0);
        long temp = cal.getTime().getTime();
        cal.setTime(new Date((long) ((31556925974.7 * (y - 1900) + sTermInfo[n] * 60000L) + temp)));


        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        System.out.println(sdf.format(cal.getTime()));

    }

    @Test
    public void test8() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        System.out.println(Integer.parseInt(year.substring(2)));
    }

    @Test
    public void test9() {
        Double d = null;
        System.out.println(d > 10);
    }

    @Test
    public void test10() {
        String s1 = "123";
//        s1 = s1 + 1;
        s1 += 1;
        System.out.println(s1);

        double d = 5 / 2;
        System.out.println(d);
    }


    @Test
    public void test11(){
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 6; i++) {
            System.out.print((random.nextInt(6) + 1) + "   ");
        }
    }

}
