package application.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "aliyun.sms")
public class SmsProperties
{
	private String accessKeyId;
	private String accessKeySecret;
	private String signName;
	private String templateCode;
}
