package application.config;

import application.config.properties.DemoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
@EnableConfigurationProperties({DemoProperties.class})
public class ApplicationConfig
{
}
