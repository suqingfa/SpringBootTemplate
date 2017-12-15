package application.model.account;

import application.Application;
import application.entity.User;
import application.model.ModelUpdateEntity;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Validated
public class UpdateInput implements ModelUpdateEntity<User>
{
    @NotNull
    @Length(min = 6)
    private String password;

    @Override
    public void update(User user)
    {
        PasswordEncoder passwordEncoder = Application.getBean(PasswordEncoder.class);
        user.setPassword(passwordEncoder.encode(password));
    }
}
