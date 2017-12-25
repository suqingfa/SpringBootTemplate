package application.model.account;

import application.Application;
import application.entity.Authority;
import application.entity.User;
import application.model.ModelToEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

@Getter
@Setter
@Validated
public class RegisterInput implements ModelToEntity<User>
{
    @NotNull
    @Length(min = 4)
    private String username;
    @NotNull
    @Length(min = 6)
    private String password;
    private Authority.Role role = Authority.Role.USER;

    @Override
    public User toEntity()
    {
        PasswordEncoder passwordEncoder = Application.getBean(PasswordEncoder.class);
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        Authority authority = new Authority();
        authority.setRole(role);
        user.setAuthorities(Arrays.asList(authority));

        return user;
    }
}
