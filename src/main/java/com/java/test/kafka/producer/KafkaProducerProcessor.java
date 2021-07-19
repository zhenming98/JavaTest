//package com.java.test.kafka.producer;
//
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
//
///**
// * @author yzm
// * @date 2020/12/9 - 9:37
// */
//@RestController
//@RequestMapping(value = "kafka/send")
//@Component
//public class KafkaProducerProcessor {
//
//    @Resource
//    KafkaTemplate<String, String> kafkaTemplate;
//
//    @GetMapping
//    public String send(@RequestParam("msg") String msg) {
//        //使用kafka模板发送信息
//        kafkaTemplate.send("test", msg);
//        return "success";
//    }
//
//}
