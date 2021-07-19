package com.java.test.base.thread;

/**
 * @author yzm
 * @date 2021/3/29 - 11:03
 */
public class YieldTest implements Runnable {

    @Override
    public void run() {

        for (int i = 0; i < 50; i++) {
            System.out.println(Thread.currentThread().getName() + "线程开始执行" + i);
            if (i == 25) {
                Thread.yield();
            }
        }
        //礼让
//        Thread.yield();
//        System.out.println(Thread.currentThread().getName() + "线程停止执行");
    }

    public static void main(String[] args) {
        YieldTest yield = new YieldTest();
        new Thread(yield, "a").start();
        new Thread(yield, "b").start();
    }
}
