package com.java.test.base.thread;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yzm
 * @date 2021/8/9 - 14:16
 */
public class ScheduledExecutorTest {

    private static final ScheduledExecutorService scheduledExecutorService =
            new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().namingPattern("test").daemon(true).build());

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }

        long startTime = System.currentTimeMillis();

//        for (Integer integer : list) {
//            System.out.println(integer);
//        }

        for (Integer integer : list) {
            scheduledExecutorService.schedule(() -> System.out.println(integer), 0, TimeUnit.SECONDS);
        }

        long end = System.currentTimeMillis();
        System.out.println("end - startTime : " + (end - startTime));
    }

}
