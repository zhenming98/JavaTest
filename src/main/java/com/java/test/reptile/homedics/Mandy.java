package com.java.test.reptile.homedics;

import com.java.test.reptile.Excel;
import com.java.test.reptile.Reptile;
import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Mandy
 * @Author yzm
 * @Date 2020/7/31 - 10:27
 * @Email yzm@ogawatech.com.cn
 */
public class Mandy {

    public static String[] products = new String[152];
    public static Reptile reptile = new Reptile();
    public static List<Product> productList = new ArrayList<>();

    public static void getAllProducts() {
        Document document = reptile.getHtmlTextByUrl("https://www.homedics.com/relaxation.html?limit=all");
        Elements name = reptile.getEleByClass(document, "a.product-image");
        String src = null;
        int i = 0;
        for (Element element : name) {
            src = element.attr("href");
            products[i++] = src;
        }
    }

    public static Product getProductInfo(String src) {
        Document document = reptile.getHtmlTextByUrl(src);
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
        //页面
        System.out.println("页面：" + src);
        //图片
        String img = reptile.getEleByClass(document, "img.etalage_thumb_image").attr("src");
        System.out.println("图片：" + img);
        Product product = new Product(name, model, availability, evaluation, score, price, function, src, img);
        return product;
    }

    public static void main(String[] args) throws Exception {
        int i = 1;
        getAllProducts();
        for (String src : products) {
            long a = System.currentTimeMillis();
            productList.add(getProductInfo(src));
            long b = System.currentTimeMillis();
            System.out.println((b - a));
            System.out.println("-----------------------------------------------------" + (i++));
        }
        Excel.export(productList);
        for (i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            reptile.downloadPicture(product.getImg(), "C:\\Users\\Administrator\\Desktop\\1\\" + (i + 1) + ".jpg");
        }
    }

}
