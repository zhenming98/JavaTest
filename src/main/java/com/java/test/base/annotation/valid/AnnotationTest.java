package com.java.test.base.annotation.valid;

import com.java.test.base.annotation.valid.entity.Entity2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author yzm
 * @date 2020/12/4 - 15:23
 */
@RestController
public class AnnotationTest {

    @PostMapping(value = "annotation/test")
    public String annotationTest(@RequestBody @Valid Entity2 entity2) {
        System.out.println(entity2);
        System.out.println(entity2.getEntity1());
        return "111";
    }

}
