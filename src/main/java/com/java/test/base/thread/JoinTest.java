package com.java.test.base.thread;

/**
 * @author yzm
 * @date 2021/3/29 - 11:08
 */
public class JoinTest implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("vip线程" + i);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        JoinTest join = new JoinTest();
        Thread thread = new Thread(join);
        thread.start();
        //主线程
        for (int i = 0; i < 10; i++) {
            if (i == 4) {
                thread.join(); //让vip线程插队
            }
            System.out.println("main" + i);
        }
    }
}
