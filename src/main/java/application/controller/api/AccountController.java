package application.controller.api;

import application.Application;
import application.entity.User;
import application.model.Output;
import application.model.account.*;
import application.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Base64;

import static application.model.Output.*;

@RestController
@RequestMapping("/api/account")
public class AccountController
{
    @Autowired
    private AccountService accountService;

    @GetMapping("login")
    public Output login()
    {
        return outputNotLogin();
    }

    @PostMapping("jsonLogin")
    public Output jsonLogin(@Valid @RequestBody LoginInput input, Errors errors)
    {
        if (errors.hasErrors())
        {
            return outputParameterError();
        }

        autoLogin(input.getUsername(), input.getPassword());
        return outputOk();
    }

    @PostMapping("loginSuccess")
    public Output loginSuccess()
    {
        return outputOk();
    }

    @PostMapping("updatePassword")
    public Output updatePassword(@Valid @RequestBody UpdatePasswordInput input, Errors errors)
    {
        if (errors.hasErrors())
            return outputParameterError();
        return accountService.updatePassword(input);
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

    @PostMapping("register")
    public Output register(@Valid @RequestBody RegisterInput input, Errors errors)
    {
        if (errors.hasErrors())
            return outputParameterError();
        Output output = accountService.register(input);
        if (output.getInfo() == Output.Info.OK)
            autoLogin(input.getUsername(), input.getPassword());
        return output;
    }

    @GetMapping({"getUserAvatar", "getUserAvatar/{id}"})
    public void getUserAvatar(@PathVariable(value = "id", required = false) String id, ServletResponse response) throws IOException
    {
        if (id == null)
        {
            id = User.getUserId();
        }
        byte[] data = accountService.getUserAvatar(id);
        response.setContentType("image/png");
        response.getOutputStream().write(data);
    }

    @PostMapping("setUserAvatar")
    public Output setUserAvatar(@Valid @RequestBody SetUserAvatarInput input, Errors errors)
    {
        if (errors.hasErrors())
        {
            return outputParameterError();
        }

        byte[] data = Base64.getDecoder().decode(input.getData());
        return accountService.setUserAvatar(data);
    }

    private void autoLogin(String username, String password)
    {
        HttpServletRequest request = Application.getRequest();
        AuthenticationManager authenticationManager = Application.getBean(AuthenticationManager.class);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    }
}
