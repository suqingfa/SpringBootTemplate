package application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface GetLogger
{
    default Logger getLogger()
    {
        return LoggerFactory.getLogger(getClass());
    }
}
