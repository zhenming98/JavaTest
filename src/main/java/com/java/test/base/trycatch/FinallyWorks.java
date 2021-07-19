package com.java.test.base.trycatch;

/**
 * @ClassName FinallyWorks
 * @Author yzm
 * @Date 2020/7/8 - 17:39
 * @Email yzm@ogawatech.com.cn
 */
public class FinallyWorks {
    static int count = 0;

    public static void main(String[] args) {
        while (true) {
            try {
                // Post-increment is zero first time:
                if (count++ == 0) {
                    throw new ThreeException();
                }
                System.out.println("No exception");
            } catch (ThreeException e) {
                System.out.println("ThreeException");
            } finally {
                System.out.println("In finally clause");
                if (count == 2) {
                    break; // out of "while"
                }
            }
        }
    }
}

class ThreeException extends Exception {
}
