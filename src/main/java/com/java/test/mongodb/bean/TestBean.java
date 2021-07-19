package com.java.test.mongodb.bean;

import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * @author yzm
 * @date 2021/4/20 - 16:44
 */
@Data
public class TestBean {

    private String username;
    private String password;

    private Map<String, Object> params;

    private Date createTime;

}
