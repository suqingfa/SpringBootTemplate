package application;

import application.entity.User;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

@Component
public final class ContextHolder implements ApplicationContextAware
{
    private static ApplicationContext context;

    public static <T> T getBean(Class<T> requiredType)
    {
        checkContext();
        return context.getBean(requiredType);
    }

    public static Object getBean(String name)
    {
        checkContext();
        return context.getBean(name);
    }

    public static Optional<HttpServletRequest> getHttpServletRequest()
    {
        RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
        return Optional.ofNullable(attributes)
                .filter(x -> x instanceof ServletRequestAttributes)
                .map(x -> ((ServletRequestAttributes) x).getRequest());
    }

    public static Optional<HttpSession> getHttpSession()
    {
        return getHttpServletRequest().map(HttpServletRequest::getSession);
    }

    public static Optional<String> getUserId()
    {
        return getUser().map(User::getId);
    }

    public static Optional<User> getUser()
    {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication();
        return Optional.ofNullable(principal)
                .filter(x -> x instanceof User)
                .map(x -> (User) x);
    }

    private static void checkContext()
    {
        Objects.requireNonNull(context, "ApplicationContext is null");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        context = applicationContext;
        checkContext();
    }
}