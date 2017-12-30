package application.model.account;

import application.ContextHolder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserIdInput
{
    @NotNull
    private String id = ContextHolder.getUserId()
            .orElse(null);
}
