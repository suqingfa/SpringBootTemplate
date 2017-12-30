package application.util;

import application.Context;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Objects;

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
            {
                break;
            }

            Object setTime = session.getAttribute(type + "_time");
            LocalDateTime effectiveTime = LocalDateTime.now()
                    .minusSeconds(time);
            if (setTime == null || effectiveTime.isAfter((LocalDateTime) setTime))
            {
                break;
            }

            Object sessionCode = session.getAttribute(type + "_code");
            if (sessionCode == null || !sessionCode.equals(code))
            {
                break;
            }

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
        Objects.requireNonNull(key, "key cannot be null");
        Objects.requireNonNull(code, "code cannot be null");
        Objects.requireNonNull(!key.isEmpty() && !code.isEmpty(), "key or code cannot be empty");

        if (session == null)
        {
            session = Context.getSession();
        }

        Objects.requireNonNull(session, "HttpSession cannot be null");
    }
}