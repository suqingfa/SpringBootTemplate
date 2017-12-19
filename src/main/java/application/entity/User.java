package application.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@Table(name = "Users")
public class User implements UserDetails
{
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column
    private String password;
    @Transient
    private boolean accountNonExpired = true;
    @Transient
    private boolean accountNonLocked = true;
    @Transient
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp createTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    private Set<Authority> authorities;

    public static String getUserId()
    {
        User user = getUser();
        if (user == null)
        {
            return null;
        }

        return user.getId();
    }

    public static User getUser()
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof User))
            return null;

        User user = (User) principal;
        return user;
    }
}
