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

    @Setter
    protected RandomSequenceType type = RandomSequenceType.NUMBER;

    private String getRandomSequenceProcess()
    {
        StringBuilder stringBuilder = new StringBuilder(length);
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        for (byte b : bytes)
        {
            stringBuilder.append(byteToChar(b));
        }
        return stringBuilder.toString();
    }

    protected char byteToChar(byte b)
    {
        b &= 127;
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

    public String getRandomSequence()
    {
        return getRandomSequenceProcess();
    }

    enum RandomSequenceType
    {
        NUMBER,
        UPPERCASE,
        UPPERCASE_NUMBER
    }
}
