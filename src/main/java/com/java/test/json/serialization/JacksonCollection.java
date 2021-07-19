package com.java.test.json.serialization;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yzm
 * @date 2021/4/25 - 16:24
 */
@RestController
public class JacksonCollection {

    @PostMapping("jackson/test")
    public String jacksonTest(@RequestBody Zoo zoo) {
        System.out.println(zoo);
        return zoo.toString();
    }
}
