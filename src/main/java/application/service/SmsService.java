package application.service;

import application.config.properties.AliyunProperties;
import application.initializer.SmsInitializer;
import application.model.Output;
import application.model.common.SendSmsInput;
import application.util.GenerateRandomSequence;
import application.util.SessionVerification;
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

import static application.model.Output.outputOk;
import static application.model.Output.outputParameterError;

@Service
@Slf4j
public class SmsService
{
    @Autowired
    private SessionVerification verification;
    @Autowired
    private AliyunProperties aliyunProperties;

    public Output sendSms(SendSmsInput input) throws ClientException
    {
        String code = send(input.getPhone());
        if (code == null)
        {
            return outputParameterError();
        }
        verification.setSession(input.getPhone(), code);
        return outputOk();
    }

    private String send(String phone) throws ClientException
    {
        GenerateRandomSequence randomSequence = new GenerateRandomSequence();
        String code = randomSequence.getRandomSequence();

        SendSmsRequest request = new SendSmsRequest();
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", aliyunProperties.getSms()
                .getSignName());
        request.putQueryParameter("TemplateCode", aliyunProperties.getSms()
                .getTemplateCode());
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");

        SendSmsResponse response = SmsInitializer.getAcsClient()
                .getAcsResponse(request);
        if (response.getCode() == null || !response.getCode()
                .equals("OK"))
        {
            log.warn("发送验证码失败！手机号:{} 信息:{}", phone, response.getMessage());
            return null;
        }

        return code;
    }

    private static class SendSmsRequest extends RpcAcsRequest<SendSmsResponse>
    {
        private SendSmsRequest()
        {
            super("Dysmsapi", "2017-05-25", "SendSms");
            setMethod(MethodType.POST);
        }

        @Override
        public Class<SendSmsResponse> getResponseClass()
        {
            return SendSmsResponse.class;
        }
    }

    @Getter
    @Setter
    public static class SendSmsResponse extends AcsResponse
    {
        private String requestId;
        private String bizId;
        private String code;
        private String message;

        @Override
        public SendSmsResponse getInstance(UnmarshallerContext context)
        {
            requestId = context.stringValue("SmsService$SendSmsResponse.RequestId");
            bizId = context.stringValue("SmsService$SendSmsResponse.BizId");
            code = context.stringValue("SmsService$SendSmsResponse.Code");
            message = context.stringValue("SmsService$SendSmsResponse.Message");
            return this;
        }
    }
}
