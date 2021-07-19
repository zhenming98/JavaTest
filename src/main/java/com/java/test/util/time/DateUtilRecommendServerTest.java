package com.java.test.util.time;

import org.junit.Test;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @ClassName DateUtilRecommendServerTest
 * @Author yzm
 * @Date 2020/8/4 - 17:49
 * @Email yzm@ogawatech.com.cn
 */
public class DateUtilRecommendServerTest {

    @Test
    public void test1() {
        Date date = new Date();
        String time = DateUtilRecommendServer.dateToStr(date, "HH:mm", "GMT+9");
        System.out.println(time);

        LocalTime nowTime = LocalTime.now(ZoneId.of("GMT+9"));
        System.out.println(nowTime);

        date = new Date();
        time = DateUtilRecommendServer.dateToStr(date, "HH:mm", "GMT+8");
        System.out.println(time);
    }

}
