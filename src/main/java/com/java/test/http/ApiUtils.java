package com.java.test.http;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yzm
 * @date 2021/1/7 - 16:20
 */
public class ApiUtils {

    /**
     * TEST
     */
    private static final String TEST_URL = "https://axwx.cozzia.com.cn";
    /**
     * DEV
     */
    private static final String DEV_URL = "https://techdev.cozzia.com.cn";

    /**
     * x-timestamp
     * x-request-id
     * Content-Type
     * <p>
     * x-token
     * Authorization
     * <p>
     * x-signature
     *
     * @return
     */
    private static Map<String, Object> getHeaders() {
        Map<String, Object> headers = new HashMap<>();

        headers.put("x-timestamp", String.valueOf(System.currentTimeMillis()));
        headers.put("x-request-id", String.valueOf(System.currentTimeMillis()));
        headers.put("Content-Type", "application/json");

        return headers;
    }

}
