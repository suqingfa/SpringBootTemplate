package application.util;

import lombok.Setter;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class GenerateRandomSequence
{
    public static final int TYPE_NUMBER = 0;
    public static final int TYPE_UPPERCASE = 1;
    public static final int TYPE_UPPERCASE_NUMBER = 2;

    private static final SecureRandom random = new SecureRandom();

    @Setter
    protected int type = TYPE_NUMBER;
    @Setter
    private int length = 6;

    protected char byteToChar(byte b)
    {
        b &= 127;
        switch (type)
        {
            case TYPE_NUMBER:
                b %= 10;
                b += '0';
                break;
            case TYPE_UPPERCASE:
                b %= 26;
                b += 'A';
                break;
            case TYPE_UPPERCASE_NUMBER:
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

    public final String getRandomSequence()
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
}
