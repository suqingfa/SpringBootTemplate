package application.service;

import application.entity.User;
import application.model.Output;
import application.model.OutputResult;
import application.model.account.RegisterInput;
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
}
