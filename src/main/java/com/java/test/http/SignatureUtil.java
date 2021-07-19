package com.java.test.http;


import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

/**
 * @author yzm
 * @date 2021/1/8 - 14:06
 */
public class SignatureUtil {
    private final static String SIGN_TYPE_RSA = "RSA";
    private final static String SIGN_ALGORITHMS = "SHA1WithRSA";
    private final static String CHARSETTING = "UTF-8";

    /**
     * 获取私钥PKCS8格式（需base64）
     *
     * @param algorithm
     * @param priKey
     * @return PrivateKey
     * @throws Exception
     */
    public static PrivateKey getPrivateKeyFromPKCS8(String algorithm, String priKey) throws Exception {
        if (algorithm == null || "".equals(algorithm) || priKey == null || "".equals(priKey)) {
            return null;
        }
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        byte[] encodedKey = Base64.decodeBase64(priKey.getBytes());
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }

    /**
     * 通过证书获取公钥（需BASE64，X509为通用证书标准）
     *
     * @param algorithm
     * @param pubKey
     * @return PublicKey
     * @throws Exception
     */
    public static PublicKey getPublicKeyFromX509(String algorithm, String pubKey) throws Exception {

        if (algorithm == null || "".equals(algorithm) || pubKey == null || "".equals(pubKey))
            return null;

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        StringWriter writer = new StringWriter();
        StreamUtil.io(new InputStreamReader(new ByteArrayInputStream(pubKey.getBytes())), writer);

        byte[] encodeByte = writer.toString().getBytes();
        encodeByte = Base64.decodeBase64(pubKey.getBytes());

        return keyFactory.generatePublic(new X509EncodedKeySpec(encodeByte));
    }

    /**
     * 使用私钥对字符进行签名
     *
     * @param plain  内容体
     * @param priKey 私钥
     * @return String
     * @throws Exception Exception
     */
    public static String sign(String plain, String priKey) throws Exception {
        if (plain == null || "".equals(plain) || priKey == null || "".equals(priKey)) {
            return null;
        }
        PrivateKey privatekey = getPrivateKeyFromPKCS8(SIGN_TYPE_RSA, priKey);
        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
        signature.initSign(privatekey);
        signature.update(plain.getBytes(CHARSETTING));
        byte[] signed = signature.sign();
        return new String(Base64.encodeBase64(signed));
    }

    /**
     * 将内容体、签名信息、及对方公钥进行验签
     *
     * @param plain  内容体
     * @param sign   签名信息
     * @param pubkey 对方公钥
     * @return boolean
     * @throws Exception
     */
    public static boolean verify(String plain, String sign, String pubkey) throws Exception {
        if (plain == null || "".equals(plain) || sign == null || "".equals(sign) || pubkey == null || "".equals(pubkey)) {
            return false;
        }
        PublicKey publicKey = getPublicKeyFromX509(SIGN_TYPE_RSA, pubkey);
        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
        signature.initVerify(publicKey);
        signature.update(plain.getBytes(CHARSETTING));

        return signature.verify(Base64.decodeBase64(sign.getBytes()));
    }

    /**
     * 测试
     *
     * @param args
     * @throws Exception
     */
    @Test
    public static void main(String[] args) throws Exception {
        //**  私钥密钥此处简单演示，应做成可配置    **/
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMwJf6SDuNirQKl6/pgC3WQDV10mP2Mwwivqzcb0BH4jE8wIeaOGQ5eBw8ncuz1xAFLA6RE0U7I/RxBehIGCKSMYFYfv1Mzd0y3UIXqkJUCyNgrJTu3vlEvydG3PVOeVn0jb6F55TJj1cO/Es14nwVhskjPk/Ft7ftrqoEBmgoidAgMBAAECgYAg1taIb/rsRIPfwz/+z1c6pZ8GCwXgvRRDZUNBZjzi4FprWGHbg9yVIfmVH8WzGeDncM0SS828vpp9c/j3ry9XgRDh70e2LKovEy9rXNenLyNjdQGCaH9WEcNaMrAwW/p2+a1DOZjtRc01yuLW/jNIlI4Sy8LmZ5bRqcp3bcjDsQJBAOgkUqBc+/rFoSxPs9+HoOU0NDpuKAQyUgTJEvlyYpzQ/ZixOy9gOygm0iCAddKgnzJMi4W9o0YlT5o3lX7aKUsCQQDhAbxxHWYv1aM98RJKOMHnvSO6Jvnn2HLdCL8qrMKdGADMexJsrJy9mXCkeYlEFWUQLAtZYQiHvbNL18trroy3AkBZC83SC7jweayYZb5WqRzzrrG2FBkveunxQfwQSWtAQf50+s78Hkqy3SlPJFeNwuUuEySV2aduudMuEdI7hY2/AkArE80DDvDYaZtWKYgp45HkDwb/BaVEqODcxmbrAaZEsyq7+zf8zFM5zV2Ob6JDAaGWpggKNZSPgFcKRycv13wjAkEAxO1AqU/hwfyZ8hSaROhqtnRrM05zQpDSPhSvHB1nv+qMw5pvJEK/YGDxm3zeEzef/vQhti8IFSo86cF9WMxWxw==";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMCX+kg7jYq0Cpev6YAt1kA1ddJj9jMMIr6s3G9AR+IxPMCHmjhkOXgcPJ3Ls9cQBSwOkRNFOyP0cQXoSBgikjGBWH79TM3dMt1CF6pCVAsjYKyU7t75RL8nRtz1TnlZ9I2+heeUyY9XDvxLNeJ8FYbJIz5Pxbe37a6qBAZoKInQIDAQAB";
        String plain = "plain：\"PLAIN\":{\"BODY\":{\"returnCode\":\"AAAAAAA\",\"status\":\"00\",\"returnMsg\":\"交易成功\",\"mobileNo\":\"15555524587\"},\"HEAD\":{\"spdbJnlNo\":\"997907074816\",\"timeStamp\":\"1435152316796\",\"transId\":\"MiguRepay\",\"jnlNo\":\"Y000Y021120140605\",\"version\":\"1.0\"}}";
        System.out.println(plain);
        String sign = sign(plain, privateKey);
        //签名信息
        System.out.println(sign);

        //签名信息
        //String sign = "BYyaHBgXhAZcjW0VUW1Cx7IpACMCkdmLkF5WkkgVEJboNtDzbQ0hRJ6v6xYDCrHKwTTigq9VpVKnyWAdvYkXlQyTs5vK0wx9aPlLaPFj6e8PZfd3+GM+Azwt15vgoaLs6GxcAZJ7FQMVkRqZWRv1MNorMh0rPLNwbdZgVF3m1+g=";

        //验签结果（接收方使用对方公钥验签）
//        System.out.println(verify(plain, sign, publicKey));
//
//        组装报文BODY内的内容
//        Map<String, String> dataMap = new TreeMap();
//        dataMap.put("param1", "value1");
//        dataMap.put("param2", "value2");
//        dataMap.put("param3", "value3");
        //生成最终报文（Json）
//        String body = JsonHelper.preparePostData("1", "1", "subOrgId", dataMap);
//        System.out.println(body);
    }

}
