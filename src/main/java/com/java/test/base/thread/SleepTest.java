package com.java.test.base.thread;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yzm
 * @date 2021/3/29 - 11:01
 */
public class SleepTest {
    public static void main(String[] args) {
        // 获取系统时间
        Date time = new Date(System.currentTimeMillis());
        while (true) {
            try {
                //休眠两秒
                Thread.sleep(2000);
                //日期格式化，输出时间
                System.out.println(new SimpleDateFormat("HH:mm:ss").format(time));
                // 更新时间
                time = new Date(System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
