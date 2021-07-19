package com.java.test.base.thread;

import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ThreadTest
 * @Author yzm
 * @Date 2020/8/19 - 16:48
 * @Email yzm@ogawatech.com.cn
 */
@RestController
public class ThreadTest extends Thread {

//    @Override
//    public void run() {
//        //run方法线程体，该线程要执行的操作
//        for (int i = 0; i < 500; i++) {
//            System.out.println("----------------");
//        }
//    }
//
//    public static void main(String[] args) {
//        //创建线程对象并调用start方法
//        new ThreadTest().start();
//        for (int i = 0; i < 500; i++) {
//            System.out.println("*****************");
//        }
//
//    }


    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println(getName() + " " + i);
        }
    }

    public static void main(String args[]) {
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
        new ThreadTest().start();
        new ThreadTest().start();
    }

}

