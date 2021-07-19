package com.java.test.TheBeautyOfDesignPatterns.actualCombat.metrics.storage;

import com.java.test.TheBeautyOfDesignPatterns.actualCombat.metrics.RequestInfo;

import java.util.List;
import java.util.Map;

/**
 * @author yzm
 * @date 2021/5/11 - 16:35
 */
public interface MetricsStorage {

    void saveRequestInfo(RequestInfo requestInfo);

    List<RequestInfo> getRequestInfos(String apiName, long startTimeInMillis, long endTimeInMillis);

    Map<String, List<RequestInfo>> getRequestInfos(long startTimeInMillis, long endTimeInMillis);

}


