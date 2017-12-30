package application.entity;

import application.Context;

import java.io.Serializable;

public interface BelongUser extends Serializable
{
    User getUser();

    default boolean isNotBelong()
    {
        return Context.getUserId()
                .map(x -> x.equals(getUser().getId()))
                .orElse(false);
    }
}
