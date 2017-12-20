package application.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduledDemo
{
    @Scheduled(fixedDelay = 15000, initialDelay = 5000)
    public void executeScheduled()
    {
        Thread current = Thread.currentThread();
        log.warn("ScheduledTest.executeFileDownLoadTask 定时任务1:" + current.getId() + ",name:" + current.getName());
    }
}
