//package com.java.test.kafka.consumer;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
///**
// * @author Administrator
// */
//@Component
//@ConfigurationProperties(prefix = "kafka.consumer")
//public class KafkaConsumerProperties {
//
//    private String zookeeperConnect;
//    private String servers;
//    private boolean autoCommit;
//    private int pollTimeout;
//    private String autoOffsetReset;
//    private int concurrency;
//    private String groupId;
//    private Integer maxPollRecords;
//    private Integer sessionTimeout;
//    private Integer heartbeatInterval;
//
//    public String getZookeeperConnect() {
//        return zookeeperConnect;
//    }
//
//    public void setZookeeperConnect(String zookeeperConnect) {
//        this.zookeeperConnect = zookeeperConnect;
//    }
//
//    public String getServers() {
//        return servers;
//    }
//
//    public void setServers(String servers) {
//        this.servers = servers;
//    }
//
//    public boolean getAutoCommit() {
//        return autoCommit;
//    }
//
//    public void setAutoCommit(boolean autoCommit) {
//        this.autoCommit = autoCommit;
//    }
//
//    public int getPollTimeout() {
//        return pollTimeout;
//    }
//
//    public void setPollTimeout(int pollTimeout) {
//        this.pollTimeout = pollTimeout;
//    }
//
//    public String getAutoOffsetReset() {
//        return autoOffsetReset;
//    }
//
//    public void setAutoOffsetReset(String autoOffsetReset) {
//        this.autoOffsetReset = autoOffsetReset;
//    }
//
//    public int getConcurrency() {
//        return concurrency;
//    }
//
//    public void setConcurrency(int concurrency) {
//        this.concurrency = concurrency;
//    }
//
//    public String getGroupId() {
//        return groupId;
//    }
//
//    public void setGroupId(String groupId) {
//        this.groupId = groupId;
//    }
//
//    public boolean isAutoCommit() {
//        return autoCommit;
//    }
//
//    public Integer getMaxPollRecords() {
//        return maxPollRecords;
//    }
//
//    public void setMaxPollRecords(Integer maxPollRecords) {
//        this.maxPollRecords = maxPollRecords;
//    }
//
//    public Integer getSessionTimeout() {
//        return sessionTimeout;
//    }
//
//    public void setSessionTimeout(Integer sessionTimeout) {
//        this.sessionTimeout = sessionTimeout;
//    }
//
//    public Integer getHeartbeatInterval() {
//        return heartbeatInterval;
//    }
//
//    public void setHeartbeatInterval(Integer heartbeatInterval) {
//        this.heartbeatInterval = heartbeatInterval;
//    }
//}
