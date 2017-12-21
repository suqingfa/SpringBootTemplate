package application.model.account;

import application.entity.User;
import lombok.Data;

@Data
public class UserIdInput
{
    private String id = User.getUserId();
}
