package application.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;

@Configuration
@EnableScheduling   // 计划任务
@EnableAsync(proxyTargetClass = true)   // 异步方法调用/线程池
public class SchedulingConfig implements AsyncConfigurer, SchedulingConfigurer
{
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar)
    {
        taskRegistrar.setScheduler(getAsyncExecutor());
    }

    @Override
    // 配置线程池
    public Executor getAsyncExecutor()
    {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(100);
        scheduler.setThreadNamePrefix("task-");
        scheduler.setAwaitTerminationSeconds(60);
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        scheduler.initialize();
        return scheduler;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler()
    {
        return null;
    }
}
