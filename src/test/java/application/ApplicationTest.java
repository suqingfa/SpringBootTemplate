package application;

import application.config.properties.DemoProperties;
import application.scheduled.AsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest
{
    @Test
    public void test()
    {
        log.debug("test");
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
        log.info(properties.getKey());
    }

    @Test
    public void testAsync() throws Exception
    {
        AsyncTask task = Application.getBean(AsyncTask.class);
        Future<String> result = task.doTask();
        log.info(result.get());
    }
}
