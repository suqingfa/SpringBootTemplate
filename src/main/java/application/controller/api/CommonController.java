package application.controller.api;

import application.model.Output;
import application.model.common.SendEmailInput;
import application.model.common.SendSmsInput;
import application.service.EmailService;
import application.service.SmsService;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static application.model.Output.outputOk;

@RestController
@RequestMapping("/api/common")
public class CommonController
{
    @Autowired
    private SmsService smsService;
    @Autowired
    private EmailService emailService;

    @PostMapping("sendSms")
    public Output sendSms(@Valid SendSmsInput input) throws ClientException
    {
        return smsService.sendSms(input);
    }

    @PostMapping("sendEmail")
    public Output sendEmail(@Valid SendEmailInput input) throws ClientException
    {
        emailService.send(input, "主题", "内容");

        return outputOk();
    }
}
