package application.controller.api;

import application.GetLogger;
import application.model.Output;
import application.model.OutputResult;
import application.model.account.RegisterInput;
import application.model.account.UpdateInput;
import application.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/account")
public class AccountController implements GetLogger, OutputResult
{
    @Autowired
    private AccountService accountService;

    @GetMapping("login")
    public Output login()
    {
        return outputNotLogin();
    }

    @PostMapping("register")
    public Output register(@Valid @RequestBody RegisterInput input, Errors errors)
    {
        if (errors.hasErrors())
            return outputParameterError();
        return accountService.register(input);
    }

    @PostMapping("update")
    public Output update(@Valid @RequestBody UpdateInput input, Errors errors)
    {
        if (errors.hasErrors())
            return outputParameterError();
        return accountService.update(input);
    }
}
