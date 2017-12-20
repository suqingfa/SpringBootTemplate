package application.initializer;

import application.config.properties.AliyunProperties;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EmailInitializer implements CommandLineRunner
{
    @Autowired
    private AliyunProperties aliyunProperties;

    @Override
    public void run(String... args)
    {
        String accessKeyId = aliyunProperties.getAccessKeyId();
        String accessKeySecret = aliyunProperties.getAccessKeySecret();

        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);

        acsClient = new DefaultAcsClient(profile);
    }

    @Getter
    private static IAcsClient acsClient;
}
