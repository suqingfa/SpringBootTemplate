package application.controller.api;

import application.model.Output;
import application.model.common.SendSmsInput;
import application.service.SmsService;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static application.model.Output.outputParameterError;

@RestController
@RequestMapping("/api/common")
public class CommonController
{
    @Autowired
    private SmsService smsService;

    @PostMapping("sendSms")
    public Output sendSms(@Valid @RequestBody SendSmsInput input, Errors errors) throws ClientException
    {
        if (errors.hasErrors())
            return outputParameterError();

        return smsService.sendSms(input);
    }
}
