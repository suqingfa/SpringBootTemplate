package application.model.account;

import java.util.List;

public interface UserOutput
{
    String getId();

    String getUsername();

    List<Authority> getAuthorities();

    interface Authority
    {
        String getRole();
    }
}
