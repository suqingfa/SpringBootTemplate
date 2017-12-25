package application.util;

import application.Application;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Component
public class SessionVerification
{
    @Autowired
    private HttpSession session;

    @Setter
    private String type = "phone";
    @Setter
    private int time = 300;

    public void setSession(String key, String code)
    {
        check(key, code);

        session.setAttribute(type, key);
        session.setAttribute(type + "_code", code);
        session.setAttribute(type + "_time", LocalDateTime.now());
    }

    public boolean verificationSession(String key, String code)
    {
        check(key, code);

        boolean result = false;
        do
        {
            if (!key.equals(session.getAttribute(type)))
                break;

            Object setTime = session.getAttribute(type + "_time");
            LocalDateTime effectiveTime = LocalDateTime.now().minusSeconds(time);
            if (setTime == null || effectiveTime.isAfter((LocalDateTime) setTime))
                break;

            Object sessionCode = session.getAttribute(type + "_code");
            if (sessionCode == null || !sessionCode.equals(code))
                break;

            result = true;
        }
        while (false);

        session.removeAttribute(type);
        session.removeAttribute(type + "_code");
        session.removeAttribute(type + "_time");

        return result;
    }

    private void check(String key, String code)
    {
        Assert.notNull(key, "key cannot be null");
        Assert.notNull(code, "code cannot be null");
        Assert.isTrue(!key.isEmpty() && !code.isEmpty(), "key || code cannot be empty");

        if (session == null)
        {
            HttpServletRequest request = Application.getRequest();
            if (request != null)
                session = request.getSession();
        }

        Assert.notNull(session, "HttpSession cannot be null");
    }
}
