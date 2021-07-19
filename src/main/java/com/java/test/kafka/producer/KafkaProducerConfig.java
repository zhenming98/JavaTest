//package com.java.test.kafka.producer;
//
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//
//import javax.annotation.Resource;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author yzm
// * @date 2020/12/9 - 10:05
// */
//@EnableKafka
//@Configuration
//public class KafkaProducerConfig {
//
//    @Resource
//    KafkaProducerProperties producerProperties;
//
//    public Map<String, Object> producerConfigs() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerProperties.getServers());
//        props.put(ProducerConfig.RETRIES_CONFIG, producerProperties.getRetries());
//        props.put(ProducerConfig.BATCH_SIZE_CONFIG, producerProperties.getBatchSize());
//        props.put(ProducerConfig.LINGER_MS_CONFIG, producerProperties.getLinger());
//        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, producerProperties.getBufferMemory());
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        return props;
//    }
//
//    public ProducerFactory<String, String> producerFactory() {
//        return new DefaultKafkaProducerFactory<>(producerConfigs());
//    }
//
//    @Bean
//    public KafkaTemplate<String, String> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//}
