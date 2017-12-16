package application;

import application.util.GenerateRandomSequence;
import application.util.GetLogger;
import org.junit.Test;

public class JavaTest implements GetLogger
{
    @Test
    public void test()
    {
        GenerateRandomSequence randomSequence  = new GenerateRandomSequence();
        for (int i = 0; i < 100; i++)
        {
            getLogger().debug(randomSequence.getRandomUppercaseNumber());
        }
    }
}
