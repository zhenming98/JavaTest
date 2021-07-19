package com.java.test.util.decrtpt;

/**
 * @author yzm
 * @date 2021/5/15 - 12:00
 */

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * AES 是一种可逆加密算法，对用户的敏感信息加密处理
 * 对原始数据进行AES加密后，在进行Base64编码转化；
 */
public class AesCBC {
    /**
     * 加密用的Key 可以用26个字母和数字组成
     * 此处使用AES-128-CBC加密模式，key需要为16位。
     */
    private static String sKey = "sklhdflsjfsdgdeg";
    private static String ivParameter = "cfbsdfgsdfxccvd1";
    private static AesCBC instance = null;

    private AesCBC() {

    }

    public static AesCBC getInstance() {
        if (instance == null) {
            instance = new AesCBC();
        }
        return instance;
    }

    /**
     * 加密
     * @param sSrc
     * @param encodingFormat
     * @param sKey
     * @param ivParameter
     * @return
     * @throws Exception
     */
    public String encrypt(String sSrc, String encodingFormat, String sKey, String ivParameter) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        //使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes(encodingFormat));
        //此处使用BASE64做转码。
        return new BASE64Encoder().encode(encrypted);
    }

    /**
     * 解密
     * @param sSrc
     * @param encodingFormat
     * @param sKey
     * @param ivParameter
     * @return
     */
    public String decrypt(String sSrc, String encodingFormat, String sKey, String ivParameter) {
        try {
            byte[] raw = sKey.getBytes(StandardCharsets.US_ASCII);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            //先用base64解密
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, encodingFormat);
        } catch (Exception ex) {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        // 需要加密的字串
        String cSrc = "我来自中国";
        System.out.println(cSrc);
        // 加密
        long lStart = System.currentTimeMillis();
        String enString = AesCBC.getInstance().encrypt(cSrc, "utf-8", sKey, ivParameter);
        System.out.println("加密后的字串是：" + enString);

        long lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("加密耗时：" + lUseTime + "毫秒");
        // 解密
        lStart = System.currentTimeMillis();
        String DeString = AesCBC.getInstance().decrypt(enString, "utf-8", sKey, ivParameter);
        System.out.println("解密后的字串是：" + DeString);
        lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("解密耗时：" + lUseTime + "毫秒");
    }
}

