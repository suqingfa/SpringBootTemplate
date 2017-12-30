package application.util;

import application.Context;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class IpUtil
{
    private IpUtil()
    {
    }

    public static String getClientIp(HttpServletRequest request)
    {
        Objects.requireNonNull(request, "request cannot be nul");

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }

        if (ip.equals("0:0:0:0:0:0:0:1") || ip.equals("::1"))
        {
            ip = "127.0.0.1";
        }

        return ip;
    }

    public static String getClientIp()
    {
        return getClientIp(Context.getRequest());
    }
}