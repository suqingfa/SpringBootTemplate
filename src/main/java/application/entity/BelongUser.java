package application.entity;

import java.io.Serializable;

public interface BelongUser extends Serializable
{
    User getUser();

    default boolean isNotBelong()
    {
        String userId = User.getUserId();
        if (userId == null || getUser() == null)
            return true;

        return !getUser().getId().equals(userId);
    }
}
