package com.java.test.ThirdInterface.aliyun.sentinel.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yzm
 * @date 2021/4/30 - 16:22
 */
@Service
public class TestService {

    @SentinelResource(value = "test", blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class})
    public void test() {
        System.out.println(new Date() + "   Test");
    }

    @SentinelResource(value = "hello", fallback = "helloFallback")
    public String hello(long s) {
        if (s < 0) {
            throw new IllegalArgumentException("invalid arg");
        }
        return String.format("Hello at %d", s);
    }

    @SentinelResource(value = "helloAnother", defaultFallback = "defaultFallback", exceptionsToIgnore = {IllegalStateException.class})
    public String helloAnother(String name) {
        if (name == null || "bad".equals(name)) {
            throw new IllegalArgumentException("oops");
        }
        if ("foo".equals(name)) {
            throw new IllegalStateException("oops");
        }
        return "Hello, " + name;
    }

    public String helloFallback(long s, Throwable ex) {
        // Do some log here.
        ex.printStackTrace();
        return "Oops, error occurred at " + s;
    }

    public String defaultFallback() {
        System.out.println("Go to default fallback");
        return "default_fallback";
    }

//    @PostConstruct
//    public void initFlowQpsRule() {
//        List<FlowRule> rules = new ArrayList<>();
//        FlowRule rule1 = new FlowRule();
//        rule1.setResource("test");
//        // QPS
//        rule1.setCount(10);
//        // QPS限流
//        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
//        rule1.setLimitApp("default");
//        rules.add(rule1);
//
//        FlowRule rule2 = new FlowRule();
//        rule2.setResource("hello");
//        // QPS
//        rule2.setCount(10);
//        // QPS限流
//        rule2.setGrade(RuleConstant.FLOW_GRADE_QPS);
//        rule2.setLimitApp("default");
//        rules.add(rule2);
//        FlowRuleManager.loadRules(rules);
//    }

}
