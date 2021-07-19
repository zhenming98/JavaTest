package com.java.test.base.thread;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yzm
 * @date 2021/3/29 - 10:04
 */
public class ThreadTest1 {
    private static int count = 1;

    @GetMapping(value = "/ThreadTest")
    public void threadTest() {
        Runnable runnable = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("---------任务执行---------" + System.currentTimeMillis() + "---------" + threadName + "---------" + count++);
        };


        for (int i = 0; i < 100; i++) {
            ScheduledExecutorService scheduledExecutorService;
            // ScheduledThreadPoolExecutor 应该定义为全局 不能在方法中 new
            scheduledExecutorService = new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().namingPattern("test").daemon(true).build());
            System.out.println("1秒后开始执行计划线程池服务..-------------------" + new Date());
            scheduledExecutorService.schedule(runnable, 2, TimeUnit.SECONDS);
        }
    }
}
