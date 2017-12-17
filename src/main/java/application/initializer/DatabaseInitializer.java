package application.initializer;

import application.entity.Authority;
import application.repository.AuthorityRepository;
import application.util.GetLogger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DatabaseInitializer implements CommandLineRunner, GetLogger
{
    @Resource
    private AuthorityRepository authorityRepository;

    @Override
    public void run(String... args)
    {
        Class<Authority.Role> roleClass = Authority.Role.class;
        for (Authority.Role role : roleClass.getEnumConstants())
        {
            if (authorityRepository.existsByRole(role))
                continue;
            Authority authority = new Authority();
            authority.setRole(role);
            authorityRepository.save(authority);
        }
    }
}
