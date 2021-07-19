package com.java.test.TheBeautyOfDesignPatterns.actualCombat.metrics.storage;

import com.java.test.TheBeautyOfDesignPatterns.actualCombat.metrics.RequestInfo;
import org.redisson.api.RedissonClient;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author yzm
 * @date 2021/5/11 - 16:35
 */
public class RedisMetricsStorage implements MetricsStorage {

    @Resource
    RedissonClient redissonClient;

    //...省略属性和构造函数等...
    @Override
    public void saveRequestInfo(RequestInfo requestInfo) {
        //...
        redissonClient.getMapCache("requestInfo").put("key", requestInfo, 10, TimeUnit.SECONDS);
    }

    @Override
    public List<RequestInfo> getRequestInfos(String apiName, long startTimestamp, long endTimestamp) {
        //...
        return null;
    }

    @Override
    public Map<String, List<RequestInfo>> getRequestInfos(long startTimestamp, long endTimestamp) {
        //...
        return null;
    }
}
