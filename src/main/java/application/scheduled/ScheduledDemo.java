package application.scheduled;

import application.util.GetLogger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledDemo implements GetLogger
{
    @Scheduled(fixedDelay = 15000, initialDelay = 5000)
    public void executeScheduled()
    {
        Thread current = Thread.currentThread();
        getLogger().warn("ScheduledTest.executeFileDownLoadTask 定时任务1:" + current.getId() + ",name:" + current.getName());
    }
}
