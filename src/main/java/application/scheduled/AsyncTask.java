package application.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
@Slf4j
public class AsyncTask
{
    @Async
    public Future<String> doTask() throws InterruptedException
    {
        log.warn("Task1 started.");
        Thread.sleep(1000);
        return new AsyncResult<>("Task1 accomplished!");
    }
}
