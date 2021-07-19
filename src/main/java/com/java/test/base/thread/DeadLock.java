package com.java.test.base.thread;

/**
 * @author yzm
 * @date 2021/3/29 - 11:15
 */
public class DeadLock extends Thread {
    DeadLock(int choice) {
        this.choice = choice;
    }

    static A a = new A();
    static B b = new B();

    int choice;

    public static void main(String[] args) {
        //创建线程
        DeadLock deadLock1 = new DeadLock(0);
        DeadLock deadLock2 = new DeadLock(1);
        deadLock1.start();
        deadLock2.start();
    }

    @Override
    public void run() {
        if (choice == 0) {
            synchronized (a) {
                //获得a的锁
                System.out.println(Thread.currentThread().getName() + "if...lockA");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b) {
                    //一秒后获得b的锁
                    System.out.println(Thread.currentThread().getName() + "if...LockB");
                }
            }
        } else {
            synchronized (b) {
                //获得b的锁
                System.out.println(Thread.currentThread().getName() + "else..LockB");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a) {
                    //一秒后获得a的锁
                    System.out.println(Thread.currentThread().getName() + "else..LockA");
                }
            }
        }
    }

}

class A {

}

class B {

}

