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
    private Email email;

    @Data
    public static class Sms
    {
        private String signName;
        private String templateCode;
    }

    @Data
    public static class Email
    {
        private String accountName;
        private String fromAlias;
        private String tagName;
    }
}
