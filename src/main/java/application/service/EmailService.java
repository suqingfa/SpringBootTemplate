package application.service;

import application.config.properties.AliyunProperties;
import application.initializer.SmsInitializer;
import application.model.common.SendEmailInput;
import com.aliyuncs.AcsResponse;
import com.aliyuncs.RpcAcsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.transform.UnmarshallerContext;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService
{
    @Autowired
    private AliyunProperties aliyunProperties;

    public void send(SendEmailInput input, String subject, String htmlBody) throws ClientException
    {
        SendRequest request = new SendRequest();
        request.putQueryParameter("AccountName", aliyunProperties.getEmail().getAccountName());
        request.putQueryParameter("FromAlias", aliyunProperties.getEmail().getFromAlias());
        request.putQueryParameter("AddressType", 1);
        request.putQueryParameter("TagName", aliyunProperties.getEmail().getTagName());
        request.putQueryParameter("ReplyToAddress", true);
        request.putQueryParameter("ToAddress", input.getEmail());
        request.putQueryParameter("Subject", subject);
        request.putQueryParameter("HtmlBody", htmlBody);

        SendResponse response = SmsInitializer.getAcsClient().getAcsResponse(request);
    }

    private static class SendRequest extends RpcAcsRequest<SendResponse>
    {
        private SendRequest()
        {
            super("Dm", "2015-11-23", "SingleSendMail");
            setMethod(MethodType.POST);
        }

        @Override
        public Class<SendResponse> getResponseClass()
        {
            return SendResponse.class;
        }
    }

    @Getter
    @Setter
    public static class SendResponse extends AcsResponse
    {
        private String requestId;
        private String envId;

        @Override
        public SendResponse getInstance(UnmarshallerContext context)
        {
            requestId = context.stringValue("EmailService$SendResponse.RequestId");
            envId = context.stringValue("EmailService$SendResponse.EnvId");
            return this;
        }
    }
}
