package com.java.test.cache.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.java.test.cache.guava.bean.EventBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author yzm
 * @date 2021/4/23 - 17:34
 */
@RestController
public class GuavaCacheTest {

    public final static Cache<String, Object> CACHE = CacheBuilder.newBuilder()
            //设置并发级别
            .concurrencyLevel(5)
            //设置缓存容器的初始容量
            .initialCapacity(5000)
            //设置缓存最大容量，超过之后按照LRU最近虽少使用算法来移除缓存项
            .maximumSize(10000)
            //是否需要统计缓存情况,该操作消耗一定的性能,生产环境应该去除
            //.recordStats()
            //设置写缓存后n秒钟过期
            .expireAfterWrite(10, TimeUnit.SECONDS)
            //设置读写缓存后n秒钟过期 类似于expireAfterWrite
            //.expireAfterAccess(17, TimeUnit.SECONDS)
            //清除时动作
            .removalListener(new CacheRemovalListener())
            .build();

    @Scheduled(cron = "0 0/1 * * * ?")
    private void clearCache() {
        CACHE.cleanUp();
    }

    @PostMapping("cache/test")
    public void cacheTeat(@RequestBody EventBean eventBean) throws ExecutionException {

        System.out.println(CACHE.get(eventBean.getId().toString(), () -> {
            return eventBean.getId();
        }));

    }

    public static void main(String[] args) {
        CACHE.put("123", null);
    }
}
