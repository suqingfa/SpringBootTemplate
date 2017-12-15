package application.service;

import application.entity.User;
import application.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
@Transactional
public class UserService
{
    @Resource
    private UserRepository userRepository;

    public User save(User user)
    {
        return userRepository.save(user);
    }

    public User findByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }
}
