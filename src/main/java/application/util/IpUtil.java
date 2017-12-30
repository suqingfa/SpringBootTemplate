package application.util;

import application.ContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

public final class IpUtil
{
    private IpUtil()
    {
    }

    private static boolean isInvalidIp(String ip)
    {
        return ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip);
    }

    public static String getClientIp(HttpServletRequest request)
    {
        Objects.requireNonNull(request, "request cannot be nul");

        String ip = request.getHeader("x-forwarded-for");
        if (isInvalidIp(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (isInvalidIp(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isInvalidIp(ip))
        {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    public static Optional<String> getClientIp()
    {
        return ContextHolder.getHttpServletRequest()
                .map(IpUtil::getClientIp);
    }
}