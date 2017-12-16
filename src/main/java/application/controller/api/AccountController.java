package application.controller.api;

import application.Application;
import application.entity.User;
import application.model.Output;
import application.model.OutputResult;
import application.model.account.RegisterInput;
import application.model.account.UpdateInput;
import application.service.AccountService;
import application.util.GetLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    public static String getAutoLoginPassword()
    {
        HttpSession httpSession = Application.getRequest().getSession();
        if (httpSession == null || httpSession.getAttribute("autoLogin") == null)
        {
            return null;
        }
        httpSession.removeAttribute("autoLogin");
        return "autoLogin";
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

    @PostMapping("register")
    public Output register(@Valid @RequestBody RegisterInput input, Errors errors)
    {
        if (errors.hasErrors())
            return outputParameterError();
        Output output = accountService.register(input);
        if (output.getCodeInfo() == Output.Code.OK)
            autoLogin(input.getUsername());
        return output;
    }

    protected void autoLogin(String username)
    {
        HttpServletRequest request = Application.getRequest();
        AuthenticationManager authenticationManager = Application.getBean(AuthenticationManager.class);
        request.getSession().setAttribute("autoLogin", true);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, "autoLogin");
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    }
}
