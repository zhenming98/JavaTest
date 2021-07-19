package com.java.test.reptile.homedics;

import com.java.test.reptile.Excel;
import com.java.test.reptile.Reptile;
import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ReptileTset
 * @Author yzm
 * @Date 2020/7/31 - 8:53
 * @Email yzm@ogawatech.com.cn
 */
public class ReptileTest {

    Reptile reptile = new Reptile();
    public static List<Product> productList = new ArrayList<>();

    @Test
    public void getHtmlTextByUrlTest() {
        System.out.println(reptile.getHtmlTextByUrl("https://item.jd.com/30771078478.html"));
    }

    @Test
    public void getEleByClassTest() {
        Document document = reptile.getHtmlTextByUrl("https://www.homedics.com/protection/cotalcomfort-smoke-personal-ultrasonic-humidifier.html");
        //图片
        String img = reptile.getEleByClass(document, "img.etalage_thumb_image").attr("src");
        System.out.println("图片：" + img);
        //名称 型号
        String name = reptile.getEleByClass(document, "div.product-name").text();
        String model = name.substring(name.lastIndexOf("Model"), name.length());
        name = name.substring(0, name.lastIndexOf("Model"));
        System.out.println("名字：" + name);
        System.out.println("型号：" + model);
        //评分
        String score = reptile.getEleByClass(document, "div#bvseo-aggregateRatingSection").text();
        String evaluation = "";
        if (StringUtils.isEmpty(score)) {
            evaluation = "";
        } else {
            evaluation = score.substring(score.lastIndexOf("by") + 2, score.length());
            score = score.substring(score.lastIndexOf("rated") + 5, score.lastIndexOf("out"));
        }
        System.out.println("评价：" + evaluation);
        System.out.println("评分：" + score);
        //可用
        String availability = reptile.getEleByClass(document, "p.availability").first().text();
        System.out.println("可用：" + availability);
        //价格
        String price = reptile.getEleByClass(document, "span.price").first().text();
        System.out.println("价格：" + price);
        //功能
        String function = reptile.getEleByClass(document, "div.std").first().text();
        System.out.println("功能：" + function);
    }

    @Test
    public void getEleByClassTest1() {
        Document document = reptile.getHtmlTextByUrl("https://item.jd.com/30771078478.html");
//        Elements name = reptile.getEleByClass(document, "a.product-image");
//        int i = 0;
//        for (Element element : name) {
//            System.out.println(element.attr("href"));
//            i++;
//        }
//        System.out.println(i);

        Elements valuation = reptile.getEleByClass(document, "div.mt");
        int j = 0;
        for (Element element : valuation) {
            System.out.println(element.text());
            j++;
        }
        System.out.println(j);
    }

    @Test
    public void exportTest() throws Exception {
        String src = "https://www.homedics.com/protection/cotalcomfort-smoke-personal-ultrasonic-humidifier.html";
        Document document = reptile.getHtmlTextByUrl(src);
        //名称 型号
        String name = reptile.getEleByClass(document, "div.product-name").text();
        String model = name.substring(name.lastIndexOf("Model"), name.length());
        name = name.substring(0, name.lastIndexOf("Model"));
        System.out.println("名字：" + name);
        System.out.println("型号：" + model);
        //评分
        String score = reptile.getEleByClass(document, "div#bvseo-aggregateRatingSection").text();
        String evaluation = score.substring(score.lastIndexOf("by") + 2, score.length());
        score = score.substring(score.lastIndexOf("rated") + 5, score.lastIndexOf("out"));
        System.out.println("评价：" + evaluation);
        System.out.println("评分：" + score);
        //可用
        String availability = reptile.getEleByClass(document, "p.availability").first().text();
        System.out.println("可用：" + availability);
        //价格
        String price = reptile.getEleByClass(document, "span.price").first().text();
        System.out.println("价格：" + price);
        //功能
        String function = reptile.getEleByClass(document, "div.std").first().text();
        System.out.println("功能：" + function);
        //页面
        System.out.println("页面：" + src);
        //图片
        String img = reptile.getEleByClass(document, "img.etalage_thumb_image").attr("src");
        System.out.println("图片：" + img);
        Product product = new Product(name, model, availability, evaluation, score, price, function, src, img);
        productList.add(product);
        Excel.export(productList);
    }

}
