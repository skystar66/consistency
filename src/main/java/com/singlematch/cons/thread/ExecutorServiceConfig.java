package com.singlematch.cons.thread;

import com.singlematch.cons.thread.handler.CustomHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ExecutorServiceConfig {





    @Bean(destroyMethod = "shutdown")
    public ExecutorService executorService() {
        int coreSize = Runtime.getRuntime().availableProcessors() * 2;
        return new ThreadPoolExecutor(50, 200, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), new CustomHandler()) {
            @Override
            public void shutdown() {
                System.out.println(">>>>>>>>>> 调用shutdown");
                super.shutdown();
                try {
                    this.awaitTermination(10, TimeUnit.MINUTES);
                } catch (InterruptedException ignored) {
                }
            }
        };
    }







}
