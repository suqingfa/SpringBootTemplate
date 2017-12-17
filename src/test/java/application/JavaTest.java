package application;

import application.util.GenerateRandomSequence;
import application.util.GetLogger;
import org.junit.Test;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class JavaTest implements GetLogger
{
    @Test
    public void test()
    {
        GenerateRandomSequence randomSequence = new GenerateRandomSequence();
        for (int i = 0; i < 100; i++)
        {
            getLogger().debug(randomSequence.getRandomUppercaseNumber());
        }
    }

    @Test
    public void testSpEL()
    {
        SpelExpressionParser parser = new SpelExpressionParser();
        SpelExpression expression = parser.parseRaw("'Hello World'.concat('!')");
        String message = expression.getValue(String.class);
        getLogger().warn(message);
    }
}
