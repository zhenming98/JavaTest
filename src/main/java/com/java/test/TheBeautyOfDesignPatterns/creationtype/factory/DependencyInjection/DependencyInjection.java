package com.java.test.TheBeautyOfDesignPatterns.creationtype.factory.DependencyInjection;

import com.google.common.annotations.VisibleForTesting;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yzm
 * @date 2021/7/15 - 11:00
 */
public class DependencyInjection {
    /*
     * 依赖注入框架，或者叫依赖注入容器（Dependency Injection Container），简称 DI 容器。
     * 在今天的讲解中，我会带你一块搞清楚这样几个问题：
     * DI 容器跟我们讲的工厂模式又有何区别和联系？
     * DI 容器的核心功能有哪些
     * 以及如何实现一个简单的 DI 容器
     *
     * DI 容器底层最基本的设计思路就是基于工厂模式的。
     * DI 容器相当于一个大的工厂类，负责在程序启动的时候，根据配置（要创建哪些类对象，每个类对象的创建需要依赖哪些其他类对象）事先创建好对象。
     * 当应用程序需要使用某个类对象的时候，直接从容器中获取即可。正是因为它持有一堆对象，所以这个框架才被称为“容器”。
     *
     * DI 容器负责的事情要比单纯的工厂模式要多。比如，它还包括配置的解析、对象生命周期的管理。
     * 总结一下，一个简单的 DI 容器的核心功能一般有三个：配置解析、对象创建和对象生命周期管理。
     */


//    配置文件beans.xml
//    <beans>
//        <bean id="rateLimiter" class="com.xzg.RateLimiter">
//           <constructor-arg ref="redisCounter"/>
//        </bean>
//
//        <bean id="redisCounter" class="com.xzg.redisCounter" scope="singleton" lazy-init="true">
//          <constructor-arg type="String" value="127.0.0.1">
//          <constructor-arg type="int" value=1234>
//        </bean>
//    </beans>


//    public class Demo {
//        public static void main(String[] args) {
//            ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
//            RateLimiter rateLimiter = (RateLimiter) applicationContext.getBean("rateLimiter");
//            rateLimiter.test();
//            //...
//        }
//    }


//    public interface ApplicationContext {
//        Object getBean(String beanId);
//    }
//
//    public class ClassPathXmlApplicationContext implements ApplicationContext {
//        private BeansFactory beansFactory;
//        private BeanConfigParser beanConfigParser;
//
//        public ClassPathXmlApplicationContext(String configLocation) {
//            this.beansFactory = new BeansFactory();
//            this.beanConfigParser = new XmlBeanConfigParser();
//            loadBeanDefinitions(configLocation);
//        }
//        private void loadBeanDefinitions(String configLocation) {
//            InputStream in = null;
//            try {
//                in = this.getClass().getResourceAsStream("/" + configLocation);
//                if (in == null) {
//                    throw new RuntimeException("Can not find config file: " + configLocation);
//                }
//                List<BeanDefinition> beanDefinitions = beanConfigParser.parse(in);
//                beansFactory.addBeanDefinitions(beanDefinitions);
//            } finally {
//                if (in != null) {
//                    try {
//                        in.close();
//                    } catch (IOException e) {
//                        // TODO: log error
//                    }
//                }
//            }
//        }
//        @Override
//        public Object getBean(String beanId) {
//            return beansFactory.getBean(beanId);
//        }
//    }

//    配置文件解析
//    public interface BeanConfigParser {
//        List<BeanDefinition> parse(InputStream inputStream);
//        List<BeanDefinition> parse(String configContent);
//    }
//
//    public class XmlBeanConfigParser implements BeanConfigParser {
//
//        @Override
//        public List<BeanDefinition> parse(InputStream inputStream) {
//            String content = null;
//            // TODO:...
//            return parse(content);
//        }
//
//        @Override
//        public List<BeanDefinition> parse(String configContent) {
//            List<BeanDefinition> beanDefinitions = new ArrayList<>();
//            // TODO:...
//            return beanDefinitions;
//        }
//
//    }
//
//    public class BeanDefinition {
//        private String id;
//        private String className;
//        private List<ConstructorArg> constructorArgs = new ArrayList<>();
//        private Scope scope = Scope.SINGLETON;
//        private boolean lazyInit = false;
//        // 省略必要的getter/setter/constructors
//
//        public boolean isSingleton() {
//            return scope.equals(Scope.SINGLETON);
//        }
//
//
//        public static enum Scope {
//            SINGLETON,
//            PROTOTYPE
//        }
//
//        public static class ConstructorArg {
//            private boolean isRef;
//            private Class type;
//            private Object arg;
//            // 省略必要的getter/setter/constructors
//        }
//    }


//    public class BeansFactory {
//        private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();
//        private ConcurrentHashMap<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>();
//
//        public void addBeanDefinitions(List<BeanDefinition> beanDefinitionList) {
//            for (BeanDefinition beanDefinition : beanDefinitionList) {
//                this.beanDefinitions.putIfAbsent(beanDefinition.getId(), beanDefinition);
//            }
//            for (BeanDefinition beanDefinition : beanDefinitionList) {
//                if (beanDefinition.isLazyInit() == false && beanDefinition.isSingleton()) {
//                    createBean(beanDefinition);
//                }
//            }
//        }
//
//        public Object getBean(String beanId) {
//            BeanDefinition beanDefinition = beanDefinitions.get(beanId);
//            if (beanDefinition == null) {
//                throw new NoSuchBeanDefinitionException("Bean is not defined: " + beanId);
//            }
//            return createBean(beanDefinition);
//        }
//
//        @VisibleForTesting
//        protected Object createBean(BeanDefinition beanDefinition) {
//            if (beanDefinition.isSingleton() && singletonObjects.contains(beanDefinition.getId())) {
//                return singletonObjects.get(beanDefinition.getId());
//            }
//
//            Object bean = null;
//            try {
//                Class beanClass = Class.forName(beanDefinition.getClassName());
//                List<BeanDefinition.ConstructorArg> args = beanDefinition.getConstructorArgs();
//                if (args.isEmpty()) {
//                    bean = beanClass.newInstance();
//                } else {
//                    Class[] argClasses = new Class[args.size()];
//                    Object[] argObjects = new Object[args.size()];
//                    for (int i = 0; i < args.size(); ++i) {
//                        BeanDefinition.ConstructorArg arg = args.get(i);
//                        if (!arg.getIsRef()) {
//                            argClasses[i] = arg.getType();
//                            argObjects[i] = arg.getArg();
//                        } else {
//                            BeanDefinition refBeanDefinition = beanDefinitions.get(arg.getArg());
//                            if (refBeanDefinition == null) {
//                                throw new NoSuchBeanDefinitionException("Bean is not defined: " + arg.getArg());
//                            }
//                            argClasses[i] = Class.forName(refBeanDefinition.getClassName());
//                            argObjects[i] = createBean(refBeanDefinition);
//                        }
//                    }
//                    bean = beanClass.getConstructor(argClasses).newInstance(argObjects);
//                }
//            } catch (ClassNotFoundException | IllegalAccessException
//                    | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
//                throw new BeanCreationFailureException("", e);
//            }
//
//            if (bean != null && beanDefinition.isSingleton()) {
//                singletonObjects.putIfAbsent(beanDefinition.getId(), bean);
//                return singletonObjects.get(beanDefinition.getId());
//            }
//            return bean;
//        }
//    }
}
