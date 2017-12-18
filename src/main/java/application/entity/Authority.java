package application.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "Authorities")
public class Authority implements GrantedAuthority
{
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public String getAuthority()
    {
        return role.name();
    }

    @ManyToMany
    private transient Set<User> users;

    public enum Role
    {
        ADMIN,
        USER,
    }
}
