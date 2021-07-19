package com.java.test.base.thread;

/**
 * @author yzm
 * @date 2021/3/29 - 10:49
 */
public class RunnableTest implements Runnable {
//    @Override
//    public void run() {
//        //run方法线程体
//        for (int i = 0; i < 200; i++) {
//            System.out.println("-----------------");
//        }
//    }
//
//    public static void main(String[] args) {
//        //创建runnable接口的实现类对象
//        RunnableTest testThread01 = new RunnableTest();
//        //创建线程对象，通过线程对象来开启线程
//        new Thread(testThread01).start();
//
//        for (int i = 0; i < 1000; i++) {
//            System.out.println("**********************************************");
//        }
//    }

    private int ticketNums = 10;

    @Override
    public void run() {
        while (ticketNums > 0) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "拿到了第" + ticketNums-- + "票");
        }
    }

    public static void main(String[] args) {
        RunnableTest ticket = new RunnableTest();
        new Thread(ticket, "喜洋洋").start();
        new Thread(ticket, "灰太狼").start();
        new Thread(ticket, "机器猫").start();
    }
}
