//package com.java.test.kafka.consumer;
//
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.Acknowledgment;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
///**
// * @author Administrator
// */
//@Component
//public class KafkaConsumerProcessor {
//
//    @KafkaListener(topics = "${kafka.consumer.topic}")
//    public void handle(List<ConsumerRecord<String, String>> list, Acknowledgment ack) {
//        System.out.println(list);
//        System.out.println(ack);
//    }
//}
