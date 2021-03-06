package application.config;

import application.entity.Authority;
import application.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.query.spi.EvaluationContextExtension;
import org.springframework.data.repository.query.spi.EvaluationContextExtensionSupport;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository)
    {
        return username ->
                userRepository.findFirstByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("UsernameNotFoundException"));
    }

    @Bean
    // 在Repository中使用与权限认证相关的SpEL时需要添加Bean
    // https://spring.io/blog/2014/07/15/spel-support-in-spring-data-jpa-query-definitions#spel-evaluationcontext-extension-model
    public EvaluationContextExtension securityExtension()
    {
        return new EvaluationContextExtensionSupport()
        {
            @Override
            public String getExtensionId()
            {
                return "security";
            }

            @Override
            public SecurityExpressionRoot getRootObject()
            {
                Authentication authentication = SecurityContextHolder.getContext()
                        .getAuthentication();
                return new SecurityExpressionRoot(authentication)
                {
                };
            }
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService)
    {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Override
    public void configure(WebSecurity web)
    {
        web.ignoring().antMatchers("/error/**", "/js/**", "/css/**", "/images/**", "/**/favicon.ico", "/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.csrf().ignoringAntMatchers("/api/**");

        http.authorizeRequests()
                .antMatchers("/api/common/**", "/api/account/login", "/api/account/register")
                .permitAll()
                .antMatchers("/api/**")
                .authenticated()
                .antMatchers("/admin/**")
                .hasAuthority(Authority.Role.ADMIN.name());

        http.formLogin()
                .loginPage("/api/account/login")
                .successForwardUrl("/api/account/loginSuccess")
                .permitAll();

        http.rememberMe().tokenValiditySeconds(10 * 24 * 3600);

        http.logout()
                .logoutUrl("/api/account/logout")
                .logoutSuccessUrl("/account/login?logout")
                .invalidateHttpSession(true);

        http.sessionManagement()
                .sessionFixation()
                .migrateSession()
                .maximumSessions(1)
                .expiredUrl("/api/account/login?maxSessions");
    }
}