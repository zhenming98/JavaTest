//package com.java.test.kafka.producer;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
///**
// * @author yzm
// * @date 2020/12/9 - 10:04
// */
//@Component
//@ConfigurationProperties(prefix = "kafka.producer")
//public class KafkaProducerProperties {
//    private String servers;
//    private String retries;
//    private String batchSize;
//    private String linger;
//    private String bufferMemory;
//
//    public String getServers() {
//        return servers;
//    }
//
//    public void setServers(String servers) {
//        this.servers = servers;
//    }
//
//    public String getRetries() {
//        return retries;
//    }
//
//    public void setRetries(String retries) {
//        this.retries = retries;
//    }
//
//    public String getBatchSize() {
//        return batchSize;
//    }
//
//    public void setBatchSize(String batchSize) {
//        this.batchSize = batchSize;
//    }
//
//    public String getLinger() {
//        return linger;
//    }
//
//    public void setLinger(String linger) {
//        this.linger = linger;
//    }
//
//    public String getBufferMemory() {
//        return bufferMemory;
//    }
//
//    public void setBufferMemory(String bufferMemory) {
//        this.bufferMemory = bufferMemory;
//    }
//}
