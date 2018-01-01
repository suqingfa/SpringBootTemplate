package application.util;

import application.ContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.stream.Stream;

public final class IpUtil
{
    private IpUtil()
    {
    }

    public static Optional<String> getClientIp(@NotNull HttpServletRequest request)
    {
        return Stream.of(
                request.getHeader("x-forwarded-for"),
                request.getHeader("Proxy-Client-IP"),
                request.getHeader("WL-Proxy-Client-IP"),
                request.getRemoteAddr())
                .filter(ip -> ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip))
                .findFirst();
    }

    public static Optional<String> getClientIp()
    {
        Optional<String> result = Optional.empty();
        Optional<HttpServletRequest> request = ContextHolder.getHttpServletRequest();
        if (request.isPresent())
        {
            result = getClientIp(request.get());
        }
        return result;
    }
}