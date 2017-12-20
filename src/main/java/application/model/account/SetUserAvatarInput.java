package application.model.account;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
public class SetUserAvatarInput
{
    @NotNull
    private String data;
}
