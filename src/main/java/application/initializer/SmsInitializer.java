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
public class SmsInitializer implements CommandLineRunner
{
    @Getter
    private static IAcsClient acsClient;
    @Autowired
    private AliyunProperties aliyunProperties;

    @Override
    public void run(String... args) throws Exception
    {
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        String product = "Dysmsapi";
        String domain = "dysmsapi.aliyuncs.com";

        String accessKeyId = aliyunProperties.getAccessKeyId();
        String accessKeySecret = aliyunProperties.getAccessKeySecret();

        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);

        acsClient = new DefaultAcsClient(profile);
    }
}
