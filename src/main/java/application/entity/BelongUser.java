package application.entity;

public interface BelongUser
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
