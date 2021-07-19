package com.java.test.quartz.simpletest;

import com.java.test.quartz.simpletest.HelloJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author yzm
 * @date 2021/5/25 - 14:12
 */
//@Configuration
public class HelloJobQuartzConfiguration {

    @Bean(name = "j1")
    public JobDetail firstJobDetail() {
        JobDetail jb = JobBuilder.newJob(HelloJob.class)
                .withDescription("this is a ram job") //job的描述
                .withIdentity("ramJob", "ramGroup") //job 的name和group
                .build();
        return jb;
    }

    // 配置触发器1
    @Bean(name = "t1")
    public CronTriggerFactoryBean cronTriggerFactoryBean(JobDetail j1) {
        CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
        factory.setJobDetail(j1);
        // 设置触发时间，从0秒开始，每2秒执行一次。
        factory.setCronExpression("0/2 * * * * ?");
        return factory;
    }

    // 配置Scheduler任务调度，t1与t2是触发器
    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactory(Trigger t1) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        // 延时启动，应用启动1秒后才启动任务调度
        bean.setStartupDelay(1);
        // 注册触发器,可以注册多个，意味着同时调度多个任务。
        bean.setTriggers(t1);
        return bean;
    }
}
