package com.java.test.util.decrtpt;

/**
 * @Author yzm
 * @Date 2021/5/16 - 10:44
 */
public interface Decrypt {

    /**
     * 加密
     *
     * @param str 代加密文本
     * @return String
     */
    String encryption(String str);

    /**
     * 解密
     *
     * @return 解密后的文本
     */
    String decrypt();

}
