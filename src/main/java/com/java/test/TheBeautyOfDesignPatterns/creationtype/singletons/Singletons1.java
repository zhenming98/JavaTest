package com.java.test.TheBeautyOfDesignPatterns.creationtype.singletons;

/**
 * @author yzm
 * @date 2021/7/14 - 10:01
 */
public class Singletons1 {

    private static Singletons1 instance = null;
    private final int paramA;
    private final int paramB;

    private Singletons1(int paramA, int paramB) {
        this.paramA = paramA;
        this.paramB = paramB;
    }

    public static Singletons1 getInstance() {
        if (instance == null) {
            throw new RuntimeException("Run init() first.");
        }
        return instance;
    }

    public synchronized static Singletons1 init(int paramA, int paramB) {
        if (instance != null){
            throw new RuntimeException("Singleton has been created!");
        }
        instance = new Singletons1(paramA, paramB);
        return instance;
    }
}
// 先init，再使用
//Singleton.init(10, 50);
//Singleton singleton = Singleton.getInstance();
