package application;

import application.config.properties.AliyunProperties;
import application.entity.User;
import application.repository.UserRepository;
import application.scheduled.AsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
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
        StringRedisTemplate redisTemplate = ContextHolder.getBean(StringRedisTemplate.class);
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
        AliyunProperties properties = ContextHolder.getBean(AliyunProperties.class);
        log.info(properties.toString());
    }

    @Test
    public void testAsync() throws Exception
    {
        AsyncTask task = ContextHolder.getBean(AsyncTask.class);
        Future<String> result = task.doTask();
        log.info(result.get());
    }

    @Test
    public void testJapExample()
    {
        UserRepository userRepository = ContextHolder.getBean(UserRepository.class);

        User user = new User();
        user.setUsername("username");
        Example<User> userExample = Example.of(user);

        user = userRepository.findOne(userExample);
        if (user != null)
        {
            log.warn(user.toString());
        }
    }
}
