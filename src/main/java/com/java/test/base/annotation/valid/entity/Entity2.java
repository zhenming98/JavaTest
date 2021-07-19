package com.java.test.base.annotation.valid.entity;

import lombok.Data;

import javax.validation.Valid;

/**
 * @author yzm
 * @date 2020/12/4 - 15:25
 */
@Data
public class Entity2 {

    @Valid
    private Entity1 entity1;

}
