package application.entity;

import application.Context;

import java.io.Serializable;

public interface BelongUser extends Serializable
{
    User getUser();

    default boolean isNotBelong()
    {
        String userId = Context.getUserId();
        return userId == null || getUser() == null || !getUser().getId()
                .equals(userId);
    }
}
