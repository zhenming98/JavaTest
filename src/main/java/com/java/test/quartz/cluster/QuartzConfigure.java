//package com.java.test.quartz.cluster;
//
//import java.io.IOException;
//import java.util.Properties;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
//import org.quartz.JobDetail;
//import org.quartz.Trigger;
//import org.quartz.spi.JobFactory;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.config.PropertiesFactoryBean;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
//import org.springframework.scheduling.quartz.JobDetailFactoryBean;
//import org.springframework.scheduling.quartz.SchedulerFactoryBean;
//
///**
// * @author yzm
// * @date 2021/5/25 - 15:39
// */
//@Configuration
//public class QuartzConfigure {
//    // 配置文件路径
//    private static final String QUARTZ_CONFIG = "/application.yml";
////    private static final String QUARTZ_CONFIG = "/quartz.properties";
//
////    @Resource
////    @Qualifier(value = "dataSource")
////    private DataSource dataSource;
//
//    @Value("${quartz.cronExpression}")
//    private String cronExpression;
//
//
//    /**
//     * JobFactory与 schedulerFactoryBean 中的JobFactory相互依赖,注意bean的名称
//     * 在这里为JobFactory注入了Spring上下文
//     *
//     * @param applicationContext
//     * @return
//     */
//    @Bean
//    public JobFactory buttonJobFactory(ApplicationContext applicationContext) {
//        AutoWiredSpringBeanToJobFactory jobFactory = new AutoWiredSpringBeanToJobFactory();
//        jobFactory.setApplicationContext(applicationContext);
//        return jobFactory;
//    }
//
//
//    /**
//     * 从quartz.properties文件中读取Quartz配置属性
//     *
//     * @return
//     * @throws IOException
//     */
//    @Bean
//    public Properties quartzProperties() throws IOException {
//        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
//        propertiesFactoryBean.setLocation(new ClassPathResource(QUARTZ_CONFIG));
//        propertiesFactoryBean.afterPropertiesSet();
//        Properties object = propertiesFactoryBean.getObject();
//        return object;
//    }
//
//    @Bean
//    public SchedulerFactoryBean schedulerFactoryBean(JobFactory buttonJobFactory, Trigger... cronJobTrigger) throws IOException {
//        SchedulerFactoryBean factory = new SchedulerFactoryBean();
//        factory.setJobFactory(buttonJobFactory);
//        factory.setOverwriteExistingJobs(true);
//        // 设置自行启动
//        factory.setAutoStartup(true);
//        factory.setQuartzProperties(quartzProperties());
//        factory.setTriggers(cronJobTrigger);
//        return factory;
//    }
//
////    @Bean
////    public SchedulerFactoryBean schedulerFactoryBean(JobFactory buttonJobFactory, Properties quartzProper, Trigger... cronJobTrigger) throws IOException {
////        SchedulerFactoryBean factory = new SchedulerFactoryBean();
////        factory.setJobFactory(buttonJobFactory);
////        factory.setOverwriteExistingJobs(true);
////        // 设置自行启动
////        factory.setAutoStartup(true);
////        factory.setQuartzProperties(quartzProper);
////        factory.setTriggers(cronJobTrigger);
////        return factory;
////    }
//
//    /**
//     * 配置JobDetailFactory
//     * JobDetailFactoryBean与CronTriggerFactoryBean相互依赖,注意bean的名称
//     *
//     * @return
//     */
//    @Bean
//    public JobDetailFactoryBean buttonobDetail() {
//        //集群模式下必须使用JobDetailFactoryBean，MethodInvokingJobDetailFactoryBean 类中的 methodInvoking 方法，是不支持序列化的
//        JobDetailFactoryBean jobDetail = new JobDetailFactoryBean();
//        jobDetail.setDurability(true);
//        jobDetail.setRequestsRecovery(true);
//        jobDetail.setJobClass(ButtonTimerJob.class);
//        return jobDetail;
//    }
//
//    /**
//     * 配置具体执行规则
//     *
//     * @param buttonobDetail
//     * @return
//     */
//    @Bean
//    public CronTriggerFactoryBean cronJobTrigger(JobDetail buttonobDetail) {
//        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
//        tigger.setJobDetail(buttonobDetail);
//        //延迟启动
//        tigger.setStartDelay(2000);
//        //从application.yml文件读取
//        tigger.setCronExpression(cronExpression);
//        return tigger;
//    }
//}
