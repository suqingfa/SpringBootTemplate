package application.entity;

import java.io.Serializable;

public interface BelongUser extends Serializable
{
    User getUser();

    default boolean isNotBelong()
    {
        String userId = User.getUserId();
        return userId == null || getUser() == null || !getUser().getId()
                .equals(userId);

    }
}
