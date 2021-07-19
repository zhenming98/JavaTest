package com.java.test.cache.guava.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @author yzm
 * @date 2021/4/24 - 11:21
 */
@Getter
@Setter
@ToString
public class EventBean {
    private Long id;
    private String type;
    private Map<String, String> params;
}
