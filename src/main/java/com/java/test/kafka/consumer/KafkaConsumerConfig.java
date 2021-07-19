//package com.java.test.kafka.consumer;
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.config.KafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.listener.AbstractMessageListenerContainer;
//import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author yzm
// * @date 2020/12/8 - 10:26
// */
//@Configuration
//@EnableKafka
//public class KafkaConsumerConfig {
//    @Autowired
//    KafkaConsumerProperties consumerProperties;
//
//    @Bean
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        factory.setConcurrency(consumerProperties.getConcurrency());
//        factory.getContainerProperties().setPollTimeout(consumerProperties.getPollTimeout());
//        factory.setBatchListener(true);
//        factory.getContainerProperties().setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL);
//        return factory;
//    }
//
//    public ConsumerFactory<String, String> consumerFactory() {
//        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
//    }
//
//    public Map<String, Object> consumerConfigs() {
//        Map<String, Object> propsMap = new HashMap<>();
//        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerProperties.getServers());
//        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, consumerProperties.getAutoCommit());
//        propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, consumerProperties.getSessionTimeout());
//        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumerProperties.getAutoOffsetReset());
//        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, consumerProperties.getGroupId());
//        propsMap.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, consumerProperties.getMaxPollRecords());
//        propsMap.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, consumerProperties.getHeartbeatInterval());
//        return propsMap;
//    }
//}
