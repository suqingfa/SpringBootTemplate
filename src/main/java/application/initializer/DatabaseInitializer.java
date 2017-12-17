package application.initializer;

import application.entity.Authority;
import application.repository.AuthorityRepository;
import application.util.GetLogger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;

@Component
public class DatabaseInitializer implements CommandLineRunner, GetLogger
{
    @Resource
    private AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) throws IllegalAccessException
    {
        Class<Authority.Roles> rolesClass = Authority.Roles.class;
        for (Field field : rolesClass.getFields())
        {
            String authorityString = (String) field.get(rolesClass);
            if (authorityRepository.existsByAuthority(authorityString))
                continue;

            Authority authority = new Authority();
            authority.setAuthority(authorityString);
            authorityRepository.save(authority);
        }
    }
}
