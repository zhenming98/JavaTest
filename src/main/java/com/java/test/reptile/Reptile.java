package com.java.test.reptile;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Reptile
 * @Author yzm
 * @Date 2020/7/31 - 8:49
 * @Email yzm@ogawatech.com.cn
 */
public class Reptile {

    /**
     * 根据 url 从网络获取网页文本
     */
    public Document getHtmlTextByUrl(String url) {
        Document doc = null;
        try {
//            int i = (int) (Math.random() * 1000);
//            while (i != 0) {
//                i--;
//            }
            doc = Jsoup.connect(url).data("query", "Java")
                    .userAgent("Mozilla").cookie("auth", "token")
                    .timeout(300000).post();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                doc = Jsoup.connect(url).timeout(5000000).get();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return doc;
    }

    /**
     * 根据元素属性获取某个元素内的elements列表
     */
    public Elements getEleByClass(Document doc, String className) {
        Elements elements = null;
        //这里把我们获取到的html文本doc，和工具class名
        elements = doc.select(className);
        //此处返回的就是所有的集合
        return elements;
    }


    /**
     * 根据 url 下载图片
     */
    public void downloadPicture(String pictureUrl, String path) {
        URL url = null;

        try {
            url = new URL(pictureUrl);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());

            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("url 地址错误");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void downloadPictures(List<String> urlList, String path, boolean isUrlName) throws Exception {
        String fileUrl = null;
        if (isUrlName) {
            for (int i = 0; i < urlList.size(); i++) {
                String url = urlList.get(i);
                fileUrl = path + "\\" + url.substring(url.lastIndexOf("/"));
                downloadPicture(url, fileUrl);
            }
        } else {
            for (int i = 0; i < urlList.size(); i++) {
                String url = urlList.get(i);
                fileUrl = path + "\\" + i + url.substring(url.lastIndexOf("."));
                downloadPicture(url, fileUrl);
            }
        }
    }

    @Test
    public void downloadPicturesTest() throws Exception {
        List<String> urlList = new ArrayList<>();
        urlList.add("http://f.video.weibocdn.com/rm88bcMilx07FdUEwhrG0104120aJaIY0E040.mp4?label=mp4_ld&template=640x360.25.0&trans_finger=40a32e8439c5409a63ccf853562a60ef&ori=0&ps=1CwnkDw1GXwCQx&Expires=1596256389&ssig=3yFNjo2WJ3&KID=unistore,video");
        urlList.add("https://cdn.homedics.com/media/catalog/product/cache/1/thumbnail/1000x/17f82f742ffe127f42dca9de82fb58b1/b/0/b07yx6wclh.pt02_3.jpg");

        String path = "C:\\Users\\Administrator\\Desktop\\1";

        downloadPictures(urlList, path, false);
    }
}
