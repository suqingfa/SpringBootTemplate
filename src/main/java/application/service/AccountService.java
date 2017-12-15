package application.service;

import application.entity.User;
import application.model.Output;
import application.model.OutputResult;
import application.model.account.*;
import application.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
@Transactional
public class AccountService implements OutputResult
{
    @Resource
    private UserRepository userRepository;

    public Output register(RegisterInput input)
    {
        if (userRepository.existsByUsername(input.getUsername()))
            return outputUsernameExist();

        User user = userRepository.save(input.toEntity());
        if (user == null)
            return outputParameterError();
        return outputOk();
    }

    public Output update(UpdateInput input)
    {
        User user = userRepository.getCurrentUser();
        input.update(user);
        userRepository.save(user);
        return outputOk();
    }

    public Output<UserInfoOutput> getUserInfo(String id)
    {
        UserInfoOutput output = new UserInfoOutput().fromEntity(userRepository.findOne(id));
        return output(output);
    }
}
