package com.java.test.base.thread;

/**
 * @author yzm
 * @date 2021/3/29 - 10:58
 */
public class StopTest implements Runnable {
    /**
     * 设置一个标志位
     */
    private boolean flag = true;

    @Override
    public void run() {
        int i = 0;
        while (flag) {
            System.out.println("run Thread " + i++);
        }
    }

    /**
     * 自己设置一个stop方法来转换标志位，停止线程
     */
    public void stop() {
        this.flag = false;
    }

    public static void main(String[] args) {
        StopTest stop = new StopTest();
        new Thread(stop).start();
        for (int i = 0; i < 1000; i++) {
            System.out.println("main线程 " + i);
            if (i == 900) {
                //调用stop方法切换标志位，让线程停止
                stop.stop();
                System.out.println("子线程停止");
            }
        }
    }
}
