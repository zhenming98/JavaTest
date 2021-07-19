package com.java.test.base.thread;

/**
 * @author yzm
 * @date 2021/3/29 - 11:11
 */
public class PriorityTest implements Runnable {
    @Override
    public void run() {
        //查看主线程的默认优先级
        System.out.println(Thread.currentThread().getName() + "-->" + Thread.currentThread().getPriority());
    }

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + "-->" + Thread.currentThread().getPriority());
        PriorityTest priority = new PriorityTest();
        Thread thread1 = new Thread(priority);
        Thread thread2 = new Thread(priority);
        Thread thread3 = new Thread(priority);

        //不设置优先级
        thread1.start();
        //先设置优先级再启动
        thread2.setPriority(1);
        thread2.start();

        thread3.setPriority(7);
        thread3.start();
    }
}
