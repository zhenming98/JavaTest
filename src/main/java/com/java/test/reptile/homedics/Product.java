package com.java.test.reptile.homedics;

import lombok.Data;

/**
 * homedics
 *
 * @ClassName Product
 * @Author yzm
 * @Date 2020/7/31 - 11:33
 * @Email yzm@ogawatech.com.cn
 */
@Data
public class Product {

    private String name;
    private String model;
    private String availability;
    private String evaluation;
    private String score;
    private String price;
    private String function;
    private String src;
    private String img;

    public Product() {

    }

    public Product(String name, String model, String availability, String evaluation, String score, String price, String function, String src, String img) {
        this.name = name;
        this.model = model;
        this.availability = availability;
        this.evaluation = evaluation;
        this.score = score;
        this.price = price;
        this.function = function;
        this.src = src;
        this.img = img;
    }

}
