package application.controller.api;

import application.Application;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

import static application.model.Output.outputNotLogin;
import static application.model.Output.outputOk;

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

    @PostMapping("loginSuccess")
    public Output loginSuccess()
    {
        return outputOk();
    }

    @PostMapping("updatePassword")
    public Output updatePassword(@Valid UpdatePasswordInput input)
    {
        return accountService.updatePassword(input);
    }

    @GetMapping("getUserInfo")
    public Output getUserInfo(@Valid UserIdInput input)
    {
        return accountService.getUserInfo(input.getId());
    }

    @PostMapping("register")
    public Output register(@Valid RegisterInput input)
    {
        Output output = accountService.register(input);
        if (output.getInfo() == Output.Info.OK)
            autoLogin(input.getUsername(), input.getPassword());
        return output;
    }

    @GetMapping("getUserAvatar")
    public void getUserAvatar(@Valid UserIdInput input, HttpServletResponse response) throws IOException
    {
        byte[] data = accountService.getUserAvatar(input.getId());
        response.setContentType("image/png");
        response.getOutputStream().write(data);
    }

    @PostMapping("setUserAvatar")
    public Output setUserAvatar(@Valid SetUserAvatarInput input) throws IOException
    {
        byte[] data = input.getFile().getBytes();
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
