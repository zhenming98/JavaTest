package com.java.test.TheBeautyOfDesignPatterns.actualCombat.metrics;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yzm
 * @date 2021/5/11 - 16:33
 */
@Getter
@Setter
public class RequestInfo {
    private String apiName;
    private double responseTime;
    private long timestamp;
    //...省略constructor/getter/setter方法...

    public RequestInfo() {

    }

    public RequestInfo(String apiName, double responseTime, long timestamp) {
        this.apiName = apiName;
        this.responseTime = responseTime;
        this.timestamp = timestamp;
    }
}
