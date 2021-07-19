package com.java.test.base.interfaces;

/**
 * @author yzm
 * @date 2021/1/5 - 17:58
 */
public class Test {

    public static void main(String[] args) {
        InterFace interFace1 = new A11("123");
        InterFace interFace2 = new A22("12345");

        interFace1.sout();
        interFace2.sout();
    }

}
