package com.codename1.app.backend;

import java.util.concurrent.Executor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class AppBackendServerApplication extends AsyncConfigurerSupport {

    public static void main(String[] args) {
        SpringApplication.run(AppBackendServerApplication.class, args);
    }
    
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("AppBuilder-");
        executor.initialize();
        return executor;
    }
}
