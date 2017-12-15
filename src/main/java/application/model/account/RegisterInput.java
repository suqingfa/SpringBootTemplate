package application.model.account;

import application.Application;
import application.entity.User;
import application.model.ModelToEntity;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Validated
public class RegisterInput implements ModelToEntity<User>
{
    @NotNull
    @Length(min = 4)
    private String username;
    @NotNull
    @Length(min = 6)
    private String password;

    @Override
    public User toEntity()
    {
        PasswordEncoder passwordEncoder = Application.getBean(PasswordEncoder.class);
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        return user;
    }
}
