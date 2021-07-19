package com.java.test.reptile.ogawa;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * @author yzm
 * @date 2021/5/27 - 9:47
 */
public class JingDong1 {
    public static void main(String[] args) throws Exception {

        int pages = 1;
        String productId = "66714981469";
        String productName = "RT8900 ";
        String callback = "fetchJSON_comment98";

        for (int i = 0; i < pages; i++) {
            BufferedWriter bw1 = new BufferedWriter(new FileWriter("C:\\Users\\Administrator\\Desktop\\spider\\comment\\" + productName + "第" + (i + 1) + "页评论内容" + ".txt"));
            BufferedWriter bw2 = new BufferedWriter(new FileWriter("第" + (i + 1) + "页评论图片连接" + ".txt"));
            //1.用Jsoup解析网页
            String url = "https://club.jd.com/comment/productPageComments.action?callback=" + callback + "&productId=" + productId + "&score=0&sortType=6&page=" + i + "&pageSize=10&isShadowSku=0&fold=1";
            CloseableHttpResponse indexRes = sendGet(url);
            //获取json内容，将其转换为字符串
            String indexHtml = EntityUtils.toString(indexRes.getEntity(), "UTF-8");
            //截取成json字符串
            if (StringUtils.isBlank(indexHtml)) {
                System.out.println("--------------------------------------------------------------------------");
                return;
            }
            String json2 = indexHtml.substring(indexHtml.indexOf('(') + 1, indexHtml.lastIndexOf(')'));
            //获取评论
            JSONArray array = JSON.parseObject(json2).getJSONArray("comments");
            for (Object item : array) {
                //获取评论中的内容
                System.out.println(item);
                System.out.println(JSON.parseObject(item.toString()).getString("content") + "          " + JSON.parseObject(item.toString()).getString("creationTime"));
                bw1.write(JSON.parseObject(item.toString()).getString("content") + "          " + JSON.parseObject(item.toString()).getString("creationTime"));
                bw1.newLine();
                bw1.newLine();
//                JSONArray array1 = JSON.parseObject(item.toString()).getJSONArray("images");
//                System.out.println(array1.size());
//                for (Object item1 : array1) {
//                    String s = JSON.parseObject(item1.toString()).getString("imgUrl");
//                    System.out.println(s);
//                    Download("http:" + s);
//                    bw2.write(JSON.parseObject(item1.toString()).getString("imgUrl") + "\n");
//                }
            }
            bw1.close();
            bw2.close();
        }
    }

    //发送get请求,获取响应结果
    public static CloseableHttpResponse sendGet(String url) throws IOException {
        //创建httpClient客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建请求对象,发送请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", "User-Agent: Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Maxthon 2.0)");
        httpGet.setHeader("Cookie", "__jdu=1622028870666672637570; areaId=16; ipLoc-djd=16-1315-3486-0; shshshfpa=b220ef05-58ae-ac1c-02ed-d4c68a03e205-1622028872; shshshfpb=wsjSgcayw04zDPNOY2fgo%20A%3D%3D; jwotest_product=99; user-key=954cd2ad-c06c-4456-ac18-906a0058513d; __jdc=122270672; wq_logid=1622081169.1444613592; wxa_level=1; retina=0; cid=9; wqmnx1=MDEyNjM1M3BpbWNydDIxNGxfc2dGSW45V1ZhPWVfY2RhdGRhYXRtbjM3dHJwMXovKG9UMG54QVd0LkssZWtoLy4uU2kuNWY3bjI0MllPT1UhSCU%3D; jxsid=16220811698180378085; webp=1; __jda=122270672.1622028870666672637570.1622028871.1622080972.1622081169.4; mba_muid=1622028870666672637570; visitkey=37679216979822913; __wga=1622081170467.1622081170467.1622081170467.1622081170467.1.1; __jdv=122270672%7Candroidapp%7Ct_335139774%7Cappshare%7CCopyURL%7C1622081170471; PPRD_P=UUID.1622028870666672637570-LOGID.1622081170477.1933422105; jxsid_s_t=1622081170578; jxsid_s_u=https%3A//item.m.jd.com/product/10023356157724.html; __jd_ref_cls=MDownLoadFloat_AppArouseA1; fingerprint=3a432c878b85f616b802be6249d2f5e9; equipmentId=Z7DANEWXKEEPP6DM7E6U2NLV5P3TB4XCA2YRG2NFGPV3QRRKPCHNGYSOF6MTFFIVQOZWEC64DNWQVVZNJL2PE2FRGY; deviceVersion=90.0.4430.212; deviceName=Chrome; deviceOSVersion=; deviceOS=; sc_width=1920; sk_history=10023356157724%2C; shshshfp=536887841068934557c9c8e31f2f678d; JSESSIONID=A9D4EDFB57741509B9D617BC8830E60D.s1; 3AB9D23F7A4B3C9B=Z7DANEWXKEEPP6DM7E6U2NLV5P3TB4XCA2YRG2NFGPV3QRRKPCHNGYSOF6MTFFIVQOZWEC64DNWQVVZNJL2PE2FRGY; shshshsID=f482354ae730a66b8234456361703fa1_13_1622083169346; __jdb=122270672.11.1622028870666672637570|4.1622081169");
        httpGet.setHeader("Connection", "keep-alive");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        return response;
    }

    //下载图片
    private static void Download(String listImgSrc) throws Exception {
        try {
            String url = listImgSrc;
            String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
            java.net.URL uri = new URL(url);
            InputStream in = uri.openStream();
            FileOutputStream fo = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\spider\\img\\" + imageName));//文件输出流
            byte[] buf = new byte[1024];
            int length = 0;
            System.out.println("开始下载:" + url);
            while ((length = in.read(buf, 0, buf.length)) != -1) {
                fo.write(buf, 0, length);
            }
            in.close();
            fo.close();
            System.out.println(imageName + "下载完成");
            Date overdate = new Date();
        } catch (Exception e) {
            System.out.println("下载失败");
        }
    }
}
