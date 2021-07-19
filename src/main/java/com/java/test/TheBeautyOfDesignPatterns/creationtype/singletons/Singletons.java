package com.java.test.TheBeautyOfDesignPatterns.creationtype.singletons;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author yzm
 * @date 2021/7/15 - 9:48
 */
public class Singletons {
    // AtomicLong是一个Java并发库中提供的一个原子变量类型,
    // 它将一些线程不安全需要加锁的复合操作封装为了线程安全的原子操作，
    // 比如下面会用到的incrementAndGet().
    private AtomicLong id = new AtomicLong(0);
    private static final Singletons instance = new Singletons();

    private Singletons() {
    }

    public static Singletons getInstance() {
        return instance;
    }

    public long getId() {
        return id.incrementAndGet();
    }
}

// IdGenerator使用举例
// long id = Singletons.getInstance().getId();
