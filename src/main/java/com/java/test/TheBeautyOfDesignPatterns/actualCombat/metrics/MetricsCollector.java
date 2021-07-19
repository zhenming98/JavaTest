package com.java.test.TheBeautyOfDesignPatterns.actualCombat.metrics;

import com.java.test.TheBeautyOfDesignPatterns.actualCombat.metrics.storage.MetricsStorage;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yzm
 * @date 2021/5/11 - 16:32
 */

public class MetricsCollector {

    //基于接口而非实现编程
    private MetricsStorage metricsStorage;

    //依赖注入
    public MetricsCollector(MetricsStorage metricsStorage) {
        this.metricsStorage = metricsStorage;
    }

    //用一个函数代替了最小原型中的两个函数
    public void recordRequest(RequestInfo requestInfo) {
        if (requestInfo == null || StringUtils.isBlank(requestInfo.getApiName())) {
            return;
        }
        metricsStorage.saveRequestInfo(requestInfo);
    }
}
