package application.model.account;

import application.entity.User;
import application.model.ModelFromEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoOutput extends ModelFromEntity<User>
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
