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

@Component
public class Context implements ApplicationContextAware
{
    private static ApplicationContext context;

    public static <T> T getBean(Class<T> requiredType)
    {
        return context.getBean(requiredType);
    }

    public static Object getBean(String name)
    {
        return context.getBean(name);
    }

    public static HttpServletRequest getRequest()
    {
        RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();

        if (!(attributes instanceof ServletRequestAttributes))
        {
            return null;
        }

        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    public static HttpSession getSession()
    {
        HttpServletRequest request = getRequest();
        if (request == null)
        {
            return null;
        }

        return request.getSession();
    }


    public static String getUserId()
    {
        User user = getUser();
        if (user == null)
        {
            return null;
        }

        return user.getId();
    }

    public static User getUser()
    {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if (principal instanceof User)
        {
            return (User) principal;
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        context = applicationContext;
    }
}