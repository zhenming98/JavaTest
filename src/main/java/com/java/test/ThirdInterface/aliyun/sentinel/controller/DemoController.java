package com.java.test.ThirdInterface.aliyun.sentinel.controller;

import com.java.test.ThirdInterface.aliyun.sentinel.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yzm
 * @date 2021/4/30 - 16:24
 */
@RestController
public class DemoController {

    @Resource
    private TestService service;

    @GetMapping("/sentinel/foo")
    public String apiFoo(@RequestParam(required = false) Long t) {
        if (t == null) {
            t = System.currentTimeMillis();
        }
        service.test();
        return service.hello(t);
    }

    @GetMapping("/sentinel/baz/{name}")
    public String apiBaz(@PathVariable("name") String name) {
        return service.helloAnother(name);
    }
}
