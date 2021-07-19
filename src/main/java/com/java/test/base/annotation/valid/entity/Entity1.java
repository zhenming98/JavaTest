package com.java.test.base.annotation.valid.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author yzm
 * @date 2020/12/4 - 15:25
 */
@Data
public class Entity1 {

    @NotBlank(message = "{record.program.comand}")
    private String command;

}
