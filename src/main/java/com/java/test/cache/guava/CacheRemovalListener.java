package com.java.test.cache.guava;

import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * @author yzm
 * @date 2021/4/23 - 22:02
 */
public class CacheRemovalListener implements RemovalListener<String, Object> {

    @Override
    public void onRemoval(RemovalNotification<String, Object> notification) {
        System.out.println("清除");
        System.out.println(notification.getKey());
        System.out.println(notification.getValue());
        System.out.println(notification.getCause());
    }
}
