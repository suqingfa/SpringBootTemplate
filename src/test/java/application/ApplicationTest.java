package application;

import application.config.properties.DemoProperties;
import application.entity.Authority;
import application.scheduled.AsyncInsert;
import application.scheduled.AsyncTask;
import application.util.GetLogger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest implements GetLogger
{
    @Test
    public void test()
    {
        getLogger().debug("test");
    }

    @Test
    public void testRedis()
    {
        StringRedisTemplate redisTemplate = Application.getBean(StringRedisTemplate.class);
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        if (valueOperations.get("ttl") == null)
        {
            valueOperations.set("ttl", "value", 100, TimeUnit.SECONDS);
        }
        Assert.assertEquals(valueOperations.get("ttl"), "value");
    }

    @Test
    public void testProperties()
    {
        DemoProperties properties = Application.getBean(DemoProperties.class);
        getLogger().warn(properties.getKey());
    }

    @Test
    public void testAsync() throws Exception
    {
        AsyncTask task = Application.getBean(AsyncTask.class);
        Future<String> result = task.doTask();
        getLogger().warn(result.get());
    }


    @Test
    public void testTime()
    {
        AsyncInsert task = Application.getBean(AsyncInsert.class);
        int count = 1000;
        Future<Authority>[] futures = new Future[count];
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < count; i++)
        {
            futures[i] = task.doInsertAuthority();
        }
        while (true)
        {
            int done = 0;
            for (Future<Authority> future : futures)
            {
                if (future.isDone() || future.isCancelled())
                    done++;
            }
            if (done == count)
                break;
        }
        stopWatch.stop();
        getLogger().warn(stopWatch.toString());
    }
}
