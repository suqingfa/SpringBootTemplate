package application.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "aliyun")
public class AliyunProperties
{
    private String accessKeyId;
    private String accessKeySecret;
    private Sms sms;

    @Data
    public static class Sms
    {
        private String signName;
        private String templateCode;
    }
}
