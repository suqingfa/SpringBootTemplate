package application.util;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
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
        if (session == null)
        {
            return;
        }

        session.setAttribute(type, key);
        session.setAttribute(type + "_code", code);
        session.setAttribute(type + "_time", LocalDateTime.now());
    }

    public boolean verificationSession(String key, String code)
    {
        if (session == null)
        {
            return false;
        }

        boolean result = true;
        if (!key.equals(session.getAttribute(type)))
            result = false;

        Object setTime = session.getAttribute(type + "_time");
        LocalDateTime effectiveTime = LocalDateTime.now().minusSeconds(time);
        if (setTime == null || effectiveTime.isAfter((LocalDateTime) setTime))
            result = false;

        Object sessionCode = session.getAttribute(type + "_code");
        if (sessionCode == null || !sessionCode.equals(code))
            result = false;

        session.removeAttribute(type);
        session.removeAttribute(type + "_code");
        session.removeAttribute(type + "_time");

        return result;
    }
}
