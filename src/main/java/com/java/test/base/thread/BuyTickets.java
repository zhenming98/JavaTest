package com.java.test.base.thread;

/**
 * @author yzm
 * @date 2021/3/29 - 11:12
 */
public class BuyTickets implements Runnable {

    private int tickets = 10;
    boolean flag = true;

    public static void main(String[] args) {
        BuyTickets tickets = new BuyTickets();
        new Thread(tickets, "喜羊羊").start();
        new Thread(tickets, "灰太狼").start();
        new Thread(tickets, "村长").start();
    }

    @Override
    public void run() {
        while (flag) {
            buy();
        }
    }

    /**
     * synchronized 同步方法，锁的是对象本身，或者说是调用这个同步方法的对象，即this。
     */
    private synchronized void buy() {
        //判断是否有票
        if (tickets <= 0) {
            flag = false;
            return;
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "买到第" + tickets-- + "张票");
    }
}
