//package com.java.test.quartz.cluster;
//
//import org.quartz.spi.TriggerFiredBundle;
//import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.scheduling.quartz.SpringBeanJobFactory;
//
///**
// * 为 JobFactory 注入SpringBean,否则Job无法使用Spring创建的bean
// *
// * @author yzm
// * @date 2021/5/25 - 15:41
// */
//public class AutoWiredSpringBeanToJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {
//
//    private transient AutowireCapableBeanFactory beanFactory;
//
//    @Override
//    public void setApplicationContext(final ApplicationContext context) {
//        beanFactory = context.getAutowireCapableBeanFactory();
//    }
//
//    @Override
//    protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
//        final Object job = super.createJobInstance(bundle);
//        beanFactory.autowireBean(job);
//        return job;
//    }
//}
