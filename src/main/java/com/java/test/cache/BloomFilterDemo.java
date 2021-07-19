package com.java.test.cache;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;

/**
 * 布隆过滤器 不存在一定不存在 存在不一定存在
 *
 * @author yzm
 * @date 2021/4/12 - 13:48
 */
public class BloomFilterDemo {

    public static void main(String[] args) {

        //CharSequence

        //创建一个插入对象为一亿，误报率为0.01%的布隆过滤器
        BloomFilter bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("utf-8")),
                100000000,
                0.0001);

        bloomFilter.put("死");
        bloomFilter.put("磕");
        bloomFilter.put("Redis");

        System.out.println(bloomFilter.mightContain("Redis"));
        System.out.println(bloomFilter.mightContain("死"));
        System.out.println(bloomFilter.mightContain("磕"));
        System.out.println(bloomFilter.mightContain("Java"));

    }


}
