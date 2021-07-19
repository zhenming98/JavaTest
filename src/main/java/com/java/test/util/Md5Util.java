package com.java.test.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Random;

/**
 * @Author yzm
 * @Date 2020/7/25 - 10:53
 */
public class Md5Util {

    public static void main(String[] args) throws Exception {
        String salt = Md5Util.getStringRandom(4);
        String password1 = md5("yzmtest" + salt);
        String password2 = getNetMd5("yzmtest" + salt);
        System.out.println("盐：" + salt + "       MD5加密后：      " + password1);
        System.out.println("盐：" + salt + "       getNetMD5加密后：" + password2);
    }

    /**
     * MD5加密 大写
     */
    public static String md5(String s) throws Exception {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(s.getBytes(StandardCharsets.UTF_8));
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toUpperCase();
        } catch (Exception e) {
            throw new Exception("MD5加密失败");
        }
    }

    /**
     * 生成随机数字和字母,
     */
    public static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();
        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    public static String getNetMd5(String s) throws Exception {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes(StandardCharsets.UTF_16LE));
            byte[] result = md.digest();
            StringBuffer sb = new StringBuffer(32);
            for (byte b : result) {
                int val = b & 0xff;
                if (val < 0xf) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(val));
            }
            return sb.toString().toLowerCase();
        } catch (Exception e) {
            throw new Exception("md5加密出现错误");
        }
    }
}
