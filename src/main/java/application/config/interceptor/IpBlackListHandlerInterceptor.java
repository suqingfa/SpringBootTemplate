package application.config.interceptor;

import application.ContextHolder;
import application.util.IpUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class IpBlackListHandlerInterceptor implements HandlerInterceptor
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        Optional<String> ipOptional = IpUtil.getClientIp(request);
        return ipOptional
                .map(ip ->
                {
                    StringRedisTemplate redisTemplate = ContextHolder.getBean(StringRedisTemplate.class);
                    ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

                    String value = valueOperations.get("IP" + ip);

                    valueOperations.set("IP" + ip, "value", 1000, TimeUnit.MILLISECONDS);

                    return value == null;
                })
                .orElse(false);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
    {
    }
}

