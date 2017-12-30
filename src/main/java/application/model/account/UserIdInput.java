package application.model.account;

import application.Context;
import lombok.Data;

@Data
public class UserIdInput
{
    private String id = Context.getUserId();
}
