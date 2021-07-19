package com.java.test.util;

import com.java.test.util.decrtpt.Sm4CBC;
import org.junit.Test;

/**
 * @author yzm
 * @date 2021/6/18 - 11:28
 */
public class Sm4CBCTest {

    @Test
    public void generateKeyTest() throws Exception {
        System.out.println(Sm4CBC.generateKey());
        System.out.println(Sm4CBC.generateKeyString());
    }

}
