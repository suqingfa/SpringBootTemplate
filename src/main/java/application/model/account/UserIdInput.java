package application.model.account;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserIdInput
{
    @NotNull
    private String id;
}
