package com.santosh.springtaskexecutor.config;

import com.santosh.springtaskexecutor.thread.RejectedTaskHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class ThreadConfig {
    @Value("${notification.thread.core.pool.size}")
    private int corePoolSize;

    @Value("${notification.thread.max.pool.size}")
    private int maxPoolSize;

    @Value("${notification.thread.queue.capacity}")
    private int queueCapacity;


    @Bean(name = "threadPoolExecutor")
    public AsyncTaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("default_task_executor_thread");
        // add a rejected execution handler
        executor.setRejectedExecutionHandler(new RejectedTaskHandler());

        executor.initialize();
        return executor;
    }

    @Bean(name = "fcmThreadPool")
    public AsyncTaskExecutor getFCMAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(30);
        executor.setMaxPoolSize(30);
        executor.setQueueCapacity(20);
        executor.setThreadNamePrefix("fcm_task_executor_thread");
        // add a rejected execution handler
        executor.setRejectedExecutionHandler(new RejectedTaskHandler());

        executor.initialize();
        return executor;
    }

}
