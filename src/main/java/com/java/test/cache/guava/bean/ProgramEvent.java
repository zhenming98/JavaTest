package com.java.test.cache.guava.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author yzm
 * @date 2021/4/24 - 11:22
 */

@Getter
@Setter
@ToString
public class ProgramEvent {
    private String uuid;
    private String programName;
    private String time;
}
