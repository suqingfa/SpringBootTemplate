package application.entity;

import java.io.Serializable;

public interface BelongUser extends Serializable
{
    User getUser();

    default boolean isBelong()
    {
        String userId = User.getUserId();
        if (userId == null || getUser() == null)
            return false;

        return getUser().getId().equals(userId);
    }
}
