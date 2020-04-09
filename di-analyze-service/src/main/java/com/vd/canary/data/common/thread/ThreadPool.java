package com.vd.canary.data.common.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
public class ThreadPool {
    /*private int corePoolSize = 10;//线程池维护线程的最少数量

    private int maxPoolSize = 30;//线程池维护线程的最大数量

    private int queueCapacity = 10;//缓存队列

    private int keepAlive = 60;//允许的空闲时间

    private int timeout = 10;// 等待线程池任务执行结束超时时间*/

    @Bean
    public Executor myExecutor() {
        ExecutorService executor = new ThreadPoolExecutor(30, 60, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(300));
        /* 另外一种线程池
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("mqExecutor-");
        //rejection-policy：当pool已经达到max size的时候，如何处理新任务
        //CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); //对拒绝task的处理策略
        executor.setKeepAliveSeconds(keepAlive);
        executor.setAwaitTerminationSeconds(timeout);
        executor.initialize();*/
        return executor;
    }

}
