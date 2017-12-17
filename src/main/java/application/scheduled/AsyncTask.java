package application.scheduled;

import application.util.GetLogger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class AsyncTask implements GetLogger
{
    @Async
    public Future<String> doTask() throws InterruptedException
    {
        getLogger().warn("Task1 started.");
        Thread.sleep(1000);
        return new AsyncResult<>("Task1 accomplished!");
    }
}
