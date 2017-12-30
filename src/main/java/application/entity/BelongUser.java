package application.entity;

import application.ContextHolder;

import java.io.Serializable;

public interface BelongUser extends Serializable
{
    User getUser();

    default boolean isNotBelong()
    {
        return ContextHolder.getUserId()
                .map(x -> x.equals(getUser().getId()))
                .orElse(false);
    }
}
