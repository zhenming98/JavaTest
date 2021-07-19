package com.java.test.base.annotation.value;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author yzm
 * @date 2020/12/16 - 10:53
 */
@Component
public class ValueTest {

    public static String valueTestStr;

    @Value("${value.test}")
    public void setValueTestStr(String valueTestStr) {
        ValueTest.valueTestStr = valueTestStr;
    }
}
