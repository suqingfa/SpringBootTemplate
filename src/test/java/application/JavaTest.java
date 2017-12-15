package application;

import org.junit.Test;

public class JavaTest implements GetLogger
{
    @Test
    public void test()
    {
        getLogger().debug("test");
    }
}
