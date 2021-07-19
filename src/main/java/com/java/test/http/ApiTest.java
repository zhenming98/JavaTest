package com.java.test.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yzm
 * @date 2021/1/7 - 14:04
 */
public class ApiTest {

    private static final Logger logger = LoggerFactory.getLogger(ApiTest.class);

    /**
     * TEST
     */
    private static final String BASE_URL = "https://axwx.cozzia.com.cn";

    /**
     * DEV
     */
//    private static final String BASE_URL = "https://techdev.cozzia.com.cn";
    public static void main(String[] args) {
        try {
            uacLogin();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    public static void uacLogin() throws Exception {
        String url = "/api/uac/v1/user/login";

        Map<String, Object> headers = new HashMap(4);
        headers.put("Authorization", "app_test001");
        headers.put("x-app-token", "9F6Pn4c8TZOoUTezlkX3MNNrRW51EZXO");
        headers.put("Content-Type", "application/json");
        headers.put("x-timestamp", String.valueOf(System.currentTimeMillis()));
        headers.put("x-request-id", String.valueOf(System.currentTimeMillis()));

        Map params = new HashMap(3);
        params.put("loginType", "quickLogin");
        params.put("userName", "17750657271");
        params.put("captcha", "");

//        HttpClientResult result = HttpClientUtil.doPost(BASE_URL + url, headers, params);
//        String s = OkHttpRequestUtils.doPost(BASE_URL + url, headers, new JSONObject(params).toJSONString());
//        String s = OkHttpUtils.doPost(BASE_URL + url, headers, null, new JSONObject(params).toJSONString());
        System.out.println();
    }

}
