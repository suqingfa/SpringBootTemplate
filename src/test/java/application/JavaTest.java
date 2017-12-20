package application;

import application.util.GenerateRandomSequence;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

@Slf4j
public class JavaTest
{
    @Test
    public void test()
    {
        GenerateRandomSequence randomSequence = new GenerateRandomSequence();
        for (int i = 0; i < 100; i++)
        {
            log.info(randomSequence.getRandomUppercaseNumber());
        }
    }

    @Test
    public void testSpEL()
    {
        SpelExpressionParser parser = new SpelExpressionParser();
        SpelExpression expression = parser.parseRaw("'Hello World'.concat('!')");
        String message = expression.getValue(String.class);
        log.info(message);
    }
}
