package application.entity;

import application.Application;

import java.io.Serializable;

public interface BelongUser extends Serializable
{
    User getUser();

    default boolean isNotBelong()
    {
        String userId = Application.getUserId();
        return userId == null || getUser() == null || !getUser().getId()
                .equals(userId);
    }
}
