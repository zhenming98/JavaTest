package com.java.test.cache.redis;

import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.event.EntryCreatedListener;
import org.redisson.api.map.event.EntryExpiredListener;
import org.redisson.api.map.event.EntryRemovedListener;
import org.redisson.api.map.event.EntryUpdatedListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author yzm
 * @date 2021/4/29 - 9:24
 */
@RestController
public class RedisTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisTest.class);

    @Resource
    RedissonClient redissonClient;

    @Resource
    RMapCache<String, Object> EVENT_CACHE;

    @GetMapping(value = "redis/test1")
    public void test1() {
        EVENT_CACHE.put("123", "123", 60, TimeUnit.SECONDS);
        System.out.println(EVENT_CACHE.get("123"));
        EVENT_CACHE.put("123", "456", 60, TimeUnit.SECONDS);
        System.out.println(EVENT_CACHE.get("123"));
    }

    @GetMapping(value = "redis/test2")
    public void test2(String key) {
        System.out.println(EVENT_CACHE.get(key));
    }

    @GetMapping(value = "redis/test3")
    public void test3(String key) {
        System.out.println(EVENT_CACHE.get(key));
        EVENT_CACHE.remove(key);
    }

    @Bean(name = "EVENT_CACHE")
    public RMapCache<String, Object> eventCache() {
        RMapCache<String, Object> eventCache = redissonClient.getMapCache("DC_EVENT");

        int listenerCreatedId = eventCache.addListener((EntryCreatedListener<String, Object>) event -> {
            LOGGER.info("新增：{}", event);
            LOGGER.info("key：{}", event.getKey());
            LOGGER.info("value：{}", event.getValue());
        });

        int listenerUpdatedId = eventCache.addListener((EntryUpdatedListener<String, Object>) event -> {
            LOGGER.info("更新：{}", event);
            event.getKey();
            event.getValue();
            event.getOldValue();
        });

        int listenerExpiredId = eventCache.addListener((EntryExpiredListener<String, Object>) event -> {
            LOGGER.info("过期：{}", event);
            event.getKey();
            event.getValue();
        });

        int listenerRemovedId = eventCache.addListener((EntryRemovedListener<String, Object>) event -> {
            LOGGER.info("删除：{}", event);
            event.getKey();
            event.getValue();
        });

        return eventCache;
    }
}
