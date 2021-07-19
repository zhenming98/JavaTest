//package com.java.test.quartz.cluster;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//import java.util.Properties;
//
///**
// * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/
// *
// * @author yzm
// * @date 2021/5/26 - 15:12
// */
//@Getter
//@Setter
//@Component
//@ConfigurationProperties(prefix = "platform.quartz")
//public class QuartzProperties {
//    /**
//     * 调度标识名 集群中每一个实例都必须使用相同的名称
//     * 默认 QuartzScheduler
//     */
//    private String schedulerInstanceName;
//    /**
//     * ID设置为自动获取 每一个必须不同
//     */
//    private String schedulerInstanceId;
//    /**
//     * 主线程是否应该是守护线程
//     */
//    private boolean schedulerMakeSchedulerThreadDaemon;
//
//    /**
//     * 线程池的实现类（一般使用SimpleThreadPool即可满足需求）
//     */
//    private String threadPoolClass;
//    /**
//     * 指定在线程池里面创建的线程是否是守护线程
//     */
//    private boolean threadPoolMakeThreadsDaemons;
//    /**
//     * 指定线程数，至少为1（无默认值）
//     */
//    private int threadPoolThreadCount;
//    /**
//     * 设置线程的优先级（最大为java.lang.Thread.MAX_PRIORITY 10，最小为Thread.MIN_PRIORITY 1，默认为5）
//     */
//    private int threadPoolThreadPriority;
//
//    /**
//     * 数据保存方式为数据库持久化
//     */
//    private String jobStoreClass;
//    /**
//     * 数据库代理类，一般org.quartz.impl.jdbcjobstore.StdJDBCDelegate可以满足大部分数据库
//     */
//    private String jobStoreDriverDelegateClass;
//    /**
//     * 表的前缀，默认QRTZ_
//     */
//    private String jobStoreTablePrefix;
//    /**
//     * 是否加入集群
//     */
//    private boolean jobStoreIsClustered;
//    /**
//     * 信息保存时间（毫秒） 默认值60秒
//     */
//    private int jobStoreMisfireThreshold;
//    /**
//     * 数据源自定义名
//     */
//    private String jobStoreDataSource;
//
//    private String dataSourceDriver;
//    private String dataSourceUrl;
//    private String dataSourceUser;
//    private String dataSourcePassword;
//    private int dataSourceMaxConnections;
//
//    @Bean
//    public Properties quartzProper() {
//        Properties properties = new Properties();
//
//        properties.setProperty("org.quartz.scheduler.instanceName", schedulerInstanceName);
//        properties.setProperty("org.quartz.scheduler.instanceId", schedulerInstanceId);
//        properties.setProperty("org.quartz.scheduler.makeSchedulerThreadDaemon", String.valueOf(schedulerMakeSchedulerThreadDaemon));
//
//        properties.setProperty("org.quartz.threadPool.class", threadPoolClass);
//        properties.setProperty("org.quartz.threadPool.makeThreadsDaemons", String.valueOf(threadPoolMakeThreadsDaemons));
//        properties.setProperty("org.quartz.threadPool.threadCount", String.valueOf(threadPoolThreadCount));
//        properties.setProperty("org.quartz.threadPool.threadPriority", String.valueOf(threadPoolThreadPriority));
//
//        properties.setProperty("org.quartz.jobStore.class", jobStoreClass);
//        properties.setProperty("org.quartz.jobStore.driverDelegateClass", jobStoreDriverDelegateClass);
//        properties.setProperty("org.quartz.jobStore.tablePrefix", jobStoreTablePrefix);
//        properties.setProperty("org.quartz.jobStore.isClustered", String.valueOf(jobStoreIsClustered));
//        properties.setProperty("org.quartz.jobStore.misfireThreshold", String.valueOf(jobStoreMisfireThreshold));
//        properties.setProperty("org.quartz.jobStore.dataSource", jobStoreDataSource);
//
//        properties.setProperty("org.quartz.dataSource." + jobStoreDataSource + ".driver", dataSourceDriver);
//        properties.setProperty("org.quartz.dataSource." + jobStoreDataSource + ".URL", dataSourceUrl);
//        properties.setProperty("org.quartz.dataSource." + jobStoreDataSource + ".user", dataSourceUser);
//        properties.setProperty("org.quartz.dataSource." + jobStoreDataSource + ".password", dataSourcePassword);
//        properties.setProperty("org.quartz.dataSource." + jobStoreDataSource + ".maxConnections", String.valueOf(dataSourceMaxConnections));
//
//        return properties;
//    }
//
//
//}
