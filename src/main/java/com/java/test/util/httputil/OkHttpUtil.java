package com.java.test.util.httputil;

import com.google.common.collect.Maps;
import emptyhead.framework.core.util.GsonUtil;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * @author yzm
 * @date 2021/5/15 - 8:59
 */
public class OkHttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);
    private static final OkHttpClient CLIENT = new OkHttpClient();


    /**
     * get 请求
     *
     * @param url 请求URL
     * @return
     * @throws Exception
     */
    public static String doGet(String url) throws Exception {
        return doGet(url, Maps.newHashMap());
    }

    /**
     * get 请求
     *
     * @param url   请求URL
     * @param query 携带参数参数
     * @return
     * @throws Exception
     */
    public static String doGet(String url, Map<String, Object> query) throws Exception {
        return doGet(url, Maps.newHashMap(), query);
    }

    /**
     * get 请求
     *
     * @param url    url
     * @param header 请求头参数
     * @param query  参数
     * @return
     */
    public static String doGet(String url, Map<String, Object> header, Map<String, Object> query) throws Exception {

        Request request = new Request.Builder()
                .url(buildUrl(url, query))
                .headers(buildHeaders(header))
                .build();

        return doHttpRequest(request);
    }


    /**
     * post 请求， 请求参数 并且 携带文件上传
     *
     * @param url          链接
     * @param header       头部
     * @param parameter    参数
     * @param file         文件
     * @param fileFormName 远程接口接收 file 的参数
     * @return 结果
     * @throws Exception 故障
     */
    public static String doPost(String url, Map<String, Object> header, Map<String, Object> parameter, File file, String fileFormName) throws Exception {

        Headers headers = buildHeaders(header);
        MultipartBody multipartBody = buildMultipartBody(parameter, null, file, fileFormName, null);


        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .post(multipartBody)
                .build();

        return doHttpRequest(request);
    }

    /**
     * post 请求， 请求参数 并且 携带文件上传二进制流
     *
     * @param url
     * @param header
     * @param parameter
     * @param fileName     文件名
     * @param fileByte     文件的二进制流
     * @param fileFormName 远程接口接收 file 的参数
     * @return
     * @throws Exception
     */
    public static String doPost(String url, Map<String, Object> header, Map<String, Object> parameter, String fileName, byte[] fileByte, String fileFormName) throws Exception {

        Headers headers = buildHeaders(header);
        MultipartBody multipartBody = buildMultipartBody(parameter, fileByte, null, fileFormName, fileName);

        // 创建一个 request
        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .post(multipartBody)
                .build();

        return doHttpRequest(request);
    }


    /**
     * post请求  参数JSON格式
     *
     * @param url
     * @param json JSON数据
     * @return
     * @throws IOException
     */
    public static String doPost(String url, String json) throws IOException {
        return doPost(url, Maps.newHashMap(), json);
    }

    /**
     * post 请求
     *
     * @param url
     * @param parameter 参数
     * @return
     * @throws Exception
     */
    public static String doPost(String url, Map<String, Object> parameter) throws Exception {
        return doPost(url, Maps.newHashMap(), parameter, null, null);
    }

    /**
     * post 请求
     *
     * @param url
     * @param header    请求头
     * @param parameter 参数
     * @return
     * @throws Exception
     */
    public static String doPost(String url, Map<String, Object> header, Map<String, Object> parameter) throws Exception {
        return doPost(url, header, parameter, null, null);
    }

    /**
     * post请求  参数JSON格式
     *
     * @param url
     * @param header 请求头
     * @param json   JSON数据
     * @return
     * @throws IOException
     */
    public static String doPost(String url, Map<String, Object> header, String json) throws IOException {
        // application/octet-stream
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json"), json);

        Request request = new Request.Builder()
                .url(url)
                .headers(buildHeaders(header))
                .post(requestBody)
                .build();

        return doHttpRequest(request);
    }



    /**
     * post 请求  携带文件上传
     *
     * @param url
     * @param file
     * @return
     * @throws Exception
     */
    public static String doPost(String url, File file, String fileFormName) throws Exception {
        return doPost(url, Maps.newHashMap(), Maps.newHashMap(), file, fileFormName);
    }




    /**
     * 请求
     *
     * @param request request
     * @return String
     * @throws IOException IOException
     */
    private static String doHttpRequest(Request request) throws IOException {
        logger.info("url: " + request.url().toString());
        try (Response execute = CLIENT.newCall(request).execute()) {
            logger.info(" ---1 http code --- :{}", GsonUtil.toJson(execute.code()));
            return execute.body().string();
        }
    }

    private static HttpUrl buildUrl(String url, Map<String, Object> parameter) {
        HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
        Iterator<Map.Entry<String, Object>> queryIterator = parameter.entrySet().iterator();
        queryIterator.forEachRemaining(e -> builder.addQueryParameter(e.getKey(), (String) e.getValue()));
        return builder.build();
    }

    private static Headers buildHeaders(Map<String, Object> header) {
        // 创建一个 Headers.Builder
        Headers.Builder headerBuilder = new Headers.Builder();
        // 装载请求头参数
        Iterator<Map.Entry<String, Object>> headerIterator = header.entrySet().iterator();
        headerIterator.forEachRemaining(e -> headerBuilder.add(e.getKey(), (String) e.getValue()));
        return headerBuilder.build();
    }

    private static MultipartBody buildMultipartBody(Map<String, Object> parameter, byte[] fileByte, File file, String fileFormName, String fileName) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        // 状态请求参数
        Iterator<Map.Entry<String, Object>> queryIterator = parameter.entrySet().iterator();
        queryIterator.forEachRemaining(e -> builder.addFormDataPart(e.getKey(), (String) e.getValue()));

        if (fileByte != null) {
            if (fileByte.length > 0) {
                // application/octet-stream
                builder.addFormDataPart(
                        StringUtils.isNotBlank(fileFormName) ? fileFormName : "file",
                        fileName,
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                fileByte));
            }
        }

        if (null != file) {
            // application/octet-stream
            builder.addFormDataPart(
                    StringUtils.isNotBlank(fileFormName) ? fileFormName : "file",
                    file.getName(),
                    RequestBody.create(MediaType.parse("application/octet-stream"),
                            file));
        }

        return builder.build();
    }

}
