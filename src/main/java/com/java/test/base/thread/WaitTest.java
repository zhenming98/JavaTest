package com.java.test.base.thread;

/**
 * @author yzm
 * @date 2021/3/29 - 11:10
 */
public class WaitTest implements Runnable {

    private String name;
    private Object prev;
    private Object self;

    WaitTest(String name, Object prev, Object self) {
        this.name = name;
        this.prev = prev;
        this.self = self;
    }

    @Override
    public void run() {
        int count = 10;
        while (count > 0) {
            synchronized (prev) {
                synchronized (self) {
                    System.out.print(name);
                    count--;
                    self.notify(); //释放自身对象(self)锁，唤醒下一个等待线程
                }
                try {
                    prev.wait(); //释放prev对象锁，终止当前线程，等待循环结束后再次被唤醒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //Object对象
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        WaitTest pa = new WaitTest("A", c, a);
        WaitTest pb = new WaitTest("B", a, b);
        WaitTest pc = new WaitTest("C", b, c);
        new Thread(pa).start();
        //确保按A、B、C顺序执行
        Thread.sleep(1000);
        new Thread(pb).start();
        Thread.sleep(1000);
        new Thread(pc).start();
        Thread.sleep(1000);
    }
}
