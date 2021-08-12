package com.java.test.base.annotation.scheduled;

import lombok.extern.log4j.Log4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author yzm
 * @date 2020/12/14 - 10:48
 */
@Log4j
@Component
public class ScheduledTest {
    /**
     * 不管是以那种形式来实现分布式锁, 总有以下特性
     * 排他性：任意时刻，只能有一个client能获取到锁
     * 容错性：分布式锁服务一般要满足AP，也就是说，只要分布式锁服务集群节点大部分存活，client就可以进行加锁解锁操作
     * 避免死锁：分布式锁一定能得到释放，即使client在释放之前崩溃或者网络不可达
     */
    private final String REDIS_LOCK_NAME = "Scheduled";

    @Resource
    RedissonClient redissonClient;

    @Autowired
    private ScheduledAnnotationBeanPostProcessor postProcessor;

    /**
     * v1
     */
//    @Scheduled(cron = "0/5 * * * * ?")
//    public void closePaymentOrderTaskV1() {
//        log.info("定时任务---------");
//    }


    /**
     * v2
     */
//    @Scheduled(cron = "0 */1 * * * ?")
//    public void closePaymentOrderTaskV2() {
//        log.info("定时任务---------");
//        // 分布式锁超时时间
//        long lockTimeout = 1000L;
//        Long setnxResult = RedisShardedPoolUtil.setnx(REDIS_LOCK_NAME, String.valueOf(System.currentTimeMillis() + lockTimeout));
//        if (setnxResult != null && setnxResult.intValue() == 1) {
//            //如果返回值是1，代表设置成功，获取锁
//            closePaymentOrder(REDIS_LOCK_NAME);
//        } else {
//            log.info("没有获得分布式锁->{REDIS_LOCK_NAME}");
//        }
//        log.info("超时未支付订单定时任务结束");
//    }

    /**
     * V3
     */
//    @Scheduled(cron = "0 */1 * * * ?")
//    public void closePaymentOrderTaskV3() {
//        log.info("超时未支付订单定时任务启动");
//        long lockTimeout = 1000L;
//        Long setnxResult = RedisShardedPoolUtil.setnx(REDIS_LOCK_NAME, String.valueOf(System.currentTimeMillis() + lockTimeout));
//        if (setnxResult != null && setnxResult.intValue() == 1) {
//            closePaymentOrder(REDIS_LOCK_NAME);
//        } else {
//            //未获取到锁，继续判断，判断时间戳，看是否可以重置并获取到锁
//            String lockValueStr = RedisShardedPoolUtil.get(REDIS_LOCK_NAME);
//            if (lockValueStr != null && System.currentTimeMillis() > Long.parseLong(lockValueStr)) {
//                String getSetResult = RedisShardedPoolUtil.getSet(REDIS_LOCK_NAME, String.valueOf(System.currentTimeMillis() + lockTimeout));
//                //再次用当前时间戳getset。
//                // 返回给定的key的旧值，->旧值判断，是否可以获取锁
//                // 当key没有旧值时，即key不存在时，返回nil ->获取锁
//                // 这里我们set了一个新的value值，获取旧的值。
//                if (getSetResult == null || (getSetResult != null && StringUtils.equals(lockValueStr, getSetResult))) {
//                    //真正获取到锁
//                    closePaymentOrder(REDIS_LOCK_NAME);
//                } else {
//                    log.info("没有获取到分布式锁->{REDIS_LOCK_NAME}");
//                }
//            } else {
//                log.info("没有获取到分布式锁->{REDIS_LOCK_NAME}");
//            }
//        }
//        log.info("超时未支付订单定时任务结束");
//    }

//    /**
//     * V4
//     * 小耶哥
//     * 小耶哥生产环境也是使用的这一套
//     */
//    @Scheduled(cron = "0/10 * * * * ?")
//    public void closePaymentOrderTaskV4() {
//        RLock lock = redissonClient.getLock(REDIS_LOCK_NAME);
//        boolean getLock = false;
//        try {
//            if (getLock = lock.tryLock(0, 50, TimeUnit.SECONDS)) {
//                log.info("Redisson获取到分布式锁->{Scheduled},ThreadName->{" + Thread.currentThread().getName() + "}");
//                // TODO 此处直接调用service方法
//                log.info("小耶哥是最帅的, lockName->{Scheduled}, ThreadName->{" + Thread.currentThread().getName() + "}");
//            } else {
//                log.info("Redisson没有获取到分布式锁->{Scheduled},ThreadName->{" + Thread.currentThread().getName() + "}");
//            }
//        } catch (InterruptedException e) {
//            log.error("Redisson分布式锁获取异常", e);
//        } finally {
//            if (!getLock) {
//                return;
//            }
//            lock.unlock();
//            log.info("Redisson分布式锁释放锁");
//        }
//    }

//    /**
//     * 通过 ScheduledAnnotationBeanPostProcessor 终止所有定时任务
//     */
//    public void cancelScheduledTasks() {
//        // 拿到所有的task(带包装)
//        Set<ScheduledTask> tasks = postProcessor.getScheduledTasks();
//        Set<Object> rawTasks = new HashSet<>(tasks.size());
//        for (ScheduledTask task : tasks) {
//            Task t = task.getTask();
//            ScheduledMethodRunnable runnable = (ScheduledMethodRunnable) t.getRunnable();
//            Object taskObject = runnable.getTarget();
//            // 将task所关联的对象放到Set中(就是带@Scheduled方法的类)
//            rawTasks.add(taskObject);
//        }
//        // 调用postProcessBeforeDestruction()方法，将task移除并cancel
//        for (Object obj : rawTasks) {
//            postProcessor.postProcessBeforeDestruction(obj, "scheduledTasks");
//        }
//
//    }

    @Scheduled(cron = "0/10 * * * * ?")
    public void test1() {
        System.out.println(1);
    }

    @Scheduled(cron = "0/10 * * * * ?")
    public void test2() {
        System.out.println(2);
    }

}
