package com.java.test;

//import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RestController
@SpringBootApplication
class JavaTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaTestApplication.class, args);
    }

    @GetMapping
//    @SentinelResource(value = "sayHello", blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class})
    public String getString() {
        return "JavaTestApplication" + new Date();
    }

}
