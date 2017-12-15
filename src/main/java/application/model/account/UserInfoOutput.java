package application.model.account;

import application.entity.User;
import application.model.ModelFromEntity;
import lombok.Data;

@Data
public class UserInfoOutput implements ModelFromEntity<User>
{
    private String id;
    private String username;

    @Override
    public UserInfoOutput fromEntity(User user)
    {
        id = user.getId();
        username = user.getUsername();
        return this;
    }
}
