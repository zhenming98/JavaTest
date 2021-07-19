package com.java.test.quartz.simpletest;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * @author yzm
 * @date 2021/5/25 - 13:56
 */
public class HelloJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("HelloJob " + new Date());
    }

}
