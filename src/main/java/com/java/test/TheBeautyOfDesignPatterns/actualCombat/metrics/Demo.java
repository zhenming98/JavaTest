package com.java.test.TheBeautyOfDesignPatterns.actualCombat.metrics;

import com.java.test.TheBeautyOfDesignPatterns.actualCombat.metrics.reporter.ConsoleReporter;
import com.java.test.TheBeautyOfDesignPatterns.actualCombat.metrics.storage.MetricsStorage;
import com.java.test.TheBeautyOfDesignPatterns.actualCombat.metrics.storage.RedisMetricsStorage;

/**
 * @author yzm
 * @date 2021/5/11 - 16:53
 */

public class Demo {
    public static void main(String[] args) {
        MetricsStorage storage = new RedisMetricsStorage();
        ConsoleReporter consoleReporter = new ConsoleReporter(storage);
        consoleReporter.startRepeatedReport(60, 60);

//        EmailReporter emailReporter = new EmailReporter(storage);
//        emailReporter.addToAddress("wangzheng@xzg.com");
//        emailReporter.startDailyReport();

        MetricsCollector collector = new MetricsCollector(storage);
        collector.recordRequest(new RequestInfo("register", 123, 10234));
        collector.recordRequest(new RequestInfo("register", 223, 11234));
        collector.recordRequest(new RequestInfo("register", 323, 12334));
        collector.recordRequest(new RequestInfo("login", 23, 12434));
        collector.recordRequest(new RequestInfo("login", 1223, 14234));

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
