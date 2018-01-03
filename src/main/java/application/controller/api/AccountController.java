package application.controller.api;

import application.ContextHolder;
import application.model.*;
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
    public Output getUserInfo(@Valid IdInput input)
    {
        return accountService.getUserInfo(input);
    }

    @GetMapping("listUser")
    public Output listUser(@Valid PageInput input)
    {
        return accountService.ListUser(input);
    }

    @PostMapping("register")
    public Output register(@Valid RegisterInput input)
    {
        return accountService.register(input);
    }

    @GetMapping("getUserAvatar")
    public void getUserAvatar(@Valid IdInput input, HttpServletResponse response) throws IOException
    {
        byte[] data = accountService.getUserAvatar(input);
        response.setContentType("image/png");
        response.getOutputStream()
                .write(data);
    }

    @PostMapping("setUserAvatar")
    public Output setUserAvatar(@Valid SetUserAvatarInput input)
    {
        return accountService.setUserAvatar(input);
    }

    private void autoLogin(String username, String password)
    {
        ContextHolder.getHttpServletRequestOptional()
                .ifPresent(request ->
                {
                    AuthenticationManager authenticationManager = ContextHolder.getBean(AuthenticationManager.class);
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
                    token.setDetails(new WebAuthenticationDetails(request));
                    Authentication authenticatedUser = authenticationManager.authenticate(token);
                    SecurityContextHolder.getContext()
                            .setAuthentication(authenticatedUser);
                    request.getSession()
                            .setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder
                                    .getContext());
                });
    }
}
