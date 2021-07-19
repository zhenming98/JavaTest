package com.java.test.base.thread;

/**
 * @author yzm
 * @date 2021/7/14 - 13:54
 */

class ZhangSan {
    public void say() {
        System.out.println("张三对李四说：“你给我画，我就把书给你。”");
    }

    public void get() {
        System.out.println("张三得到画了。");
    }
};

class LiSi {
    public void say() {
        System.out.println("李四对张三说：“你给我书，我就把画给你”");
    }

    public void get() {
        System.out.println("李四得到书了。");
    }
};

public class DeadLock1 implements Runnable {

    private static ZhangSan zs = new ZhangSan();
    private static LiSi ls = new LiSi();

    /**
     * 声明标志位，判断那个先说话
     */
    boolean flag = false;

    @Override
    public void run() {
        if (flag) {
            // 同步张三
            synchronized (zs) {
                zs.say();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (ls) {
                    zs.get();
                }
            }
        } else {
            synchronized (ls) {
                ls.say();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (zs) {
                    ls.get();
                }
            }
        }
    }

    public static void main(String[] args) {
        // 控制张三
        DeadLock1 t1 = new DeadLock1();
        // 控制李四
        DeadLock1 t2 = new DeadLock1();
        t1.flag = true;
        t2.flag = false;
        Thread thA = new Thread(t1);
        Thread thB = new Thread(t2);
        thA.start();
        thB.start();
    }
};
