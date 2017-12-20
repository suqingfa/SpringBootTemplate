package application.service;

import application.Application;
import application.config.properties.SmsProperties;
import application.initializer.SmsInitializer;
import application.model.Output;
import application.model.common.SendSmsInput;
import application.util.GenerateRandomSequence;
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

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

import static application.model.Output.outputOk;
import static application.model.Output.outputParameterError;

@Service
@Slf4j
public class SmsService
{
    private static final String PHONE = "phone";
    private static final String SMS_CODE = "smsCode";
    private static final String SEND_TIME = "sendTime";
    @Autowired
    private HttpSession session;
    @Autowired
    private SmsProperties smsProperties;

    public Output sendSms(SendSmsInput input) throws ClientException
    {
        String code = send(input.getPhone());
        if (code == null)
        {
            return outputParameterError();
        }
        setSmsSession(input.getPhone(), code);
        return outputOk();
    }

    private String send(String phone) throws ClientException
    {
        GenerateRandomSequence randomSequence = Application.getBean(GenerateRandomSequence.class);
        String code = randomSequence.getRandomSequence();

        SendSmsRequest request = new SendSmsRequest();
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", smsProperties.getSignName());
        request.putQueryParameter("TemplateCode", smsProperties.getTemplateCode());
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");

        SendSmsResponse response = SmsInitializer.getAcsClient().getAcsResponse(request);
        if (response.getCode() == null || !response.getCode().equals("OK"))
        {
            log.warn("发送验证码失败！手机号:{} 信息:{}", phone, response.getMessage());
            return null;
        }

        return code;
    }

    private void setSmsSession(String mobile, String code)
    {
        if (session == null)
            return;

        session.setAttribute(PHONE, mobile);
        session.setAttribute(SMS_CODE, code);
        session.setAttribute(SEND_TIME, LocalDateTime.now());
    }

    public boolean verificationSmsSession(String phone, String code)
    {
        if (session == null)
            return false;

        boolean result = true;
        if (phone == null || code == null || !phone.equals(session.getAttribute(PHONE)))
            result = false;


        Object sendTime = session.getAttribute(SEND_TIME);
        // 验证码有效时间 300s
        LocalDateTime effectiveTime = LocalDateTime.now().minusSeconds(300);
        if (sendTime == null || effectiveTime.isAfter((LocalDateTime) sendTime))
            result = false;


        Object smsCode = session.getAttribute(SMS_CODE);
        if (smsCode == null || !smsCode.equals(code))
            result = false;

        session.removeAttribute(SEND_TIME);
        session.removeAttribute(SMS_CODE);
        session.removeAttribute(PHONE);

        return result;
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
