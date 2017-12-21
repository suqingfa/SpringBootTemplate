package application.controller.api;

import application.model.Output;
import application.model.common.SendEmailInput;
import application.model.common.SendSmsInput;
import application.service.EmailService;
import application.service.SmsService;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static application.model.Output.outputOk;
import static application.model.Output.outputParameterError;

@RestController
@RequestMapping("/api/common")
public class CommonController
{
    @Autowired
    private SmsService smsService;
    @Autowired
    private EmailService emailService;

    @PostMapping("sendSms")
    public Output sendSms(@Valid SendSmsInput input, Errors errors) throws ClientException
    {
        if (errors.hasErrors())
            return outputParameterError();

        return smsService.sendSms(input);
    }

    @PostMapping("sendEmail")
    public Output sendEmail(@Valid SendEmailInput input, Errors errors) throws ClientException
    {
        if (errors.hasErrors())
            return outputParameterError();

        emailService.send(input, "主题", "内容");

        return outputOk();
    }
}
