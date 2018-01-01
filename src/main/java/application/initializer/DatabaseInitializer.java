package application.initializer;

import application.entity.Authority;
import application.repository.AuthorityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class DatabaseInitializer implements CommandLineRunner
{
    @Resource
    private AuthorityRepository authorityRepository;

    @Override
    public void run(String... args)
    {
        List<Authority> list = authorityRepository.findAll();
        Class<Authority.Role> roleClass = Authority.Role.class;
        for (Authority.Role role : roleClass.getEnumConstants())
        {
            if (list.stream()
                    .anyMatch(authority -> authority.getRole() == role))
            {
                continue;
            }
            Authority authority = new Authority();
            authority.setRole(role);
            authorityRepository.save(authority);
        }
    }
}
