package com.java.test.reptile.ogawa;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.java.test.util.httputil.OkHttpUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yzm
 * @date 2021/5/27 - 9:19
 */
public class JingDong {

    private static final String URL = "https://club.jd.com/comment/productPageComments.action";


    private static String page;
    private static String callback;
    /**
     * 商品号
     */
    private static String productId;
    /**
     * 好评为3 中评为2 差评为1 全部评论为0 追评为5
     */
    private static String score = "0";
    private static String sortType = "5";
    private static String pageSize = "10";
    private static String isShadowSku = "0";
    private static String fold = "1";

    @Test
    public void jingDong() throws Exception {
        Map<String, Object> header = headers();
        Map<String, Object> parameters = params("1", "fetchJSON_comment98", "45049243293");
        String doGet = OkHttpUtil.doGet(URL, header, parameters);
//        String doGet = OkHttpUtil.doGet(URL, parameters);
        System.out.println(doGet);
    }

    private Map<String, Object> params(String page, String callback, String productId) {
        Map<String, Object> map = new HashMap<>();

        map.put("page", page);
        map.put("callback", callback);
        map.put("productId", productId);
        map.put("score", score);
        map.put("sortType", sortType);
        map.put("pageSize", pageSize);
        map.put("isShadowSku", isShadowSku);
        map.put("fold", fold);

        return map;
    }

    private Map<String, Object> headers() {
        Map<String, Object> map = new HashMap<>();

        map.put("Cookie", "__jdv=76161171|baidu|-|organic|not set|1622028870667; __jdu=1622028870666672637570; areaId=16; ipLoc-djd=16-1315-3486-0; shshshfpa=b220ef05-58ae-ac1c-02ed-d4c68a03e205-1622028872; shshshfpb=wsjSgcayw04zDPNOY2fgo%20A%3D%3D; jwotest_product=99; user-key=954cd2ad-c06c-4456-ac18-906a0058513d; __jda=122270672.1622028870666672637570.1622028871.1622028871.1622077945.2; __jdc=122270672; shshshfp=536887841068934557c9c8e31f2f678d; 3AB9D23F7A4B3C9B=Z7DANEWXKEEPP6DM7E6U2NLV5P3TB4XCA2YRG2NFGPV3QRRKPCHNGYSOF6MTFFIVQOZWEC64DNWQVVZNJL2PE2FRGY; JSESSIONID=4D50791DFBB58A64712C43773BE41CEA.s1; shshshsID=518ee3387762ac124311080d22b5dce4_8_1622077996145; __jdb=122270672.8.1622028870666672637570|2.1622077945");
        map.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
        map.put("Referer", "https://item.jd.com/");
//        map.put("Accept", "*/*");
//        map.put("Accept-Encoding", "gzip, deflate, br");
//        map.put("Connection", "keep-alive");

        return map;
    }



}
