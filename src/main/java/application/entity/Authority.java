package application.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Authorities")
public class Authority implements GrantedAuthority
{
    @Id
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public String getAuthority()
    {
        return role.name();
    }

    public enum Role
    {
        ADMIN,
        USER,
    }
}
