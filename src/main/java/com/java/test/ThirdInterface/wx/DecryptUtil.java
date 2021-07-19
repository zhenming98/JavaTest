package com.java.test.ThirdInterface.wx;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;

/**
 * @author yzm
 * @date 2021/5/15 - 14:40
 */
public class DecryptUtil {
    /**
     * 算法名称
     */
    final String KEY_ALGORITHM = "AES";
    /**
     * 加解密算法/模式/填充方式
     */
    final String algorithmStr = "AES/CBC/PKCS7Padding";

    private Key key;
    private Cipher cipher;

    public void init(byte[] keyBytes) {

        // 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
        int base = 16;
        if (keyBytes.length % base != 0) {
            int groups = keyBytes.length / base + 1;
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
            keyBytes = temp;
        }
        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        // 转化成JAVA的密钥格式
        key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        try {
            // 初始化cipher
            cipher = Cipher.getInstance(algorithmStr);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 解密方法
     *
     * @param encryptedDataStr 要解密的字符串
     * @param keyBytesStr      解密密钥
     * @param ivStr            解密初始向量
     * @return
     */
    public byte[] decrypt(String encryptedDataStr, String keyBytesStr, String ivStr) {
        byte[] encryptedText = null;
        byte[] encryptedData;
        byte[] sessionkey;
        byte[] iv;

        try {
            sessionkey = Base64.decodeBase64(keyBytesStr);
            encryptedData = Base64.decodeBase64(encryptedDataStr);
            iv = Base64.decodeBase64(ivStr);
            init(sessionkey);
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
            encryptedText = cipher.doFinal(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedText;
    }

    public static void main(String[] args) {
        DecryptUtil d = new DecryptUtil();
        String content = "qbkeYlqX+ag7csA7zVRhjYs1ugniZAhBgZY8GcLz1WjeHf5jYXqCyta3Cu5r4/N7H5Gi2I0THSrORKrE4RlEmNZAnYNC8F2wJjUlJsGoCyhPMcMwE5XqdJqMDJ51JGxAroNQwNsbSfmpQy+KCe4NsqJawGM3hPhpKm6jce+ho6FeKt3ZLR+oF2YKr24odDcKMF/VbWugy82xnMfu1fwlPTPLEEz0ACYC5uoyYkfBkWtn90YNdcfbuhCGpZuxaNF+LKj+xIjFHdQitzSJ52Q8ontf+XWWKkc1G5XnSkcg6xH0e7ZaXMlVJZEKXBnEpmRXtQRa1kd8Kq/UjSHHCerGagXqKHN5YL1qrhjcAK9SgygAxpgph9P0jAL+2uBnygDH89Uzi5uYEZ8zuKHP0bza4Ytsb84U2M+KB/OZ9MfFGHE=";
        String key = "FFOlW1+eYniYi3CS5kO+qQ==";
        String iv = "kZnEGrf7WQCSdT0Fd19AxQ==";
        byte[] result = d.decrypt(content, key, iv);
        System.out.println(new String(result, StandardCharsets.UTF_8));
    }
}
