package application.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Authorities")
public class Authority implements GrantedAuthority
{
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;

    @ManyToMany(mappedBy = "authorities", targetEntity = User.class)
    private transient List<User> users = new ArrayList<>();

    @Column(unique = true)
    private String authority;
}
