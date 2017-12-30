package application.model.account;

import application.Context;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserIdInput
{
    @NotNull
    private String id = Context.getUserId()
            .orElse(null);
}
