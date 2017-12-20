package application.model.account;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
public class LoginInput
{
    @NotNull
    @Length(min = 4)
    private String username;
    @NotNull
    @Length(min = 6)
    private String password;
}
