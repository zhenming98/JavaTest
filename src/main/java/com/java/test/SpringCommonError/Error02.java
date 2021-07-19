//package com.java.test.SpringCommonError;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
///**
// * @author yzm
// * @date 2021/4/23 - 14:02
// */
//@Component
//public class Error02 {
//
//    /*
//
//        /org/springframework/beans/factory/support/AbstractAutowireCapableBeanFactory.java
//    *   // Need to determine the constructor...
//		Constructor<?>[] ctors = determineConstructorsFromBeanPostProcessors(beanClass, beanName);
//    *
//    *   return autowireConstructor(beanName, mbd, ctors, args);
//    *
//    */
//
//    private String serviceName;
//
//    public Error02(String serviceName1) {
//        this.serviceName = serviceName1;
//    }
//
//    @Bean
//    public String serviceName1() {
//        return "aaa";
//    }
//}
