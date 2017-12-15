package application.controller;

import application.entity.User;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController
{
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public User index()
    {
        User user = userService.findByUsername("xx");
        if (user == null)
        {
            user = new User();
            user.setUsername("xx");
            user = userService.save(user);
        }

        return user;
    }
}
