package application.config;

import application.config.properties.AliyunProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
@EnableConfigurationProperties({AliyunProperties.class})
public class ApplicationConfig
{
}
