package application.util;

import application.Application;

import javax.servlet.http.HttpServletRequest;

public class IpUtil
{
    public static String getIp()
    {
        HttpServletRequest request = Application.getRequest();
        if (request == null)
        {
            return "127.0.0.1";
        }
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
}
