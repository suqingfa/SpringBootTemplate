package application.controller.api;

import application.entity.User;
import application.model.Output;
import application.model.OutputResult;
import application.model.account.RegisterInput;
import application.model.account.UpdateInput;
import application.service.AccountService;
import application.util.GetLogger;
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

    @GetMapping({"getUserInfo", "getUserInfo/{id}"})
    public Output getUserInfo(@PathVariable(value = "id", required = false) String id)
    {
        if (id == null)
        {
            id = User.getUserId();
        }
        return accountService.getUserInfo(id);
    }
}
