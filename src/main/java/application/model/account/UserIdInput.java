package application.model.account;

import application.Application;
import lombok.Data;

@Data
public class UserIdInput
{
    private String id = Application.getUserId();
}
