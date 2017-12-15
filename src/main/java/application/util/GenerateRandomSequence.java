package application.util;

import lombok.Setter;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class GenerateRandomSequence
{
    private static final SecureRandom random = new SecureRandom();

    @Setter
    private int length = 6;

    private String getRandom(int length, RandomSequenceType type)
    {
        StringBuilder stringBuilder = new StringBuilder(length);
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        for (byte b : bytes)
        {
            b &= 127;
            stringBuilder.append(byteToChar(b, type));
        }
        return stringBuilder.toString();
    }

    private char byteToChar(byte b, RandomSequenceType type)
    {
        switch (type)
        {
            case NUMBER:
                b %= 10;
                b += '0';
                break;
            case UPPERCASE:
                b %= 26;
                b += 'A';
                break;
            case UPPERCASE_NUMBER:
                b %= 36;
                if (b < 10)
                {
                    b += '0';
                }
                else
                {
                    b += 'A' - 10;
                }
                break;
        }
        return (char) b;
    }

    public String getRandomNumber()
    {
        return getRandom(length, RandomSequenceType.NUMBER);
    }

    public String getRandomUppercase()
    {
        return getRandom(length, RandomSequenceType.UPPERCASE);
    }

    public String getRandomUppercaseNumber()
    {
        return getRandom(length, RandomSequenceType.UPPERCASE_NUMBER);
    }

    enum RandomSequenceType
    {
        NUMBER,
        UPPERCASE,
        UPPERCASE_NUMBER
    }
}
