package com.java.test.TheBeautyOfDesignPatterns.actualCombat.metrics;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yzm
 * @date 2021/5/11 - 16:49
 */
@Getter
@Setter
public class RequestStat {
    private double maxResponseTime;
    private double minResponseTime;
    private double avgResponseTime;
    private double p999ResponseTime;
    private double p99ResponseTime;
    private long count;
    private long tps;
    //...省略getter/setter方法...
}
