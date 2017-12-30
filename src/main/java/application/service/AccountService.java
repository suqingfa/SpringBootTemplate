package application.service;

import application.Context;
import application.entity.User;
import application.model.Output;
import application.model.account.*;
import application.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import static application.model.Output.outputOk;
import static application.model.Output.outputUsernameExist;

@Service
@Transactional
public class AccountService
{
    @Resource
    private UserRepository userRepository;
    @Resource
    private FileManager fileManager;

    public Output register(RegisterInput input)
    {
        if (userRepository.existsByUsername(input.getUsername()))
        {
            return outputUsernameExist();
        }

        userRepository.save(input.toEntity());

        return outputOk();
    }

    public Output updatePassword(UpdatePasswordInput input)
    {
        User user = userRepository.getCurrentUser();
        input.update(user);
        userRepository.save(user);
        return outputOk();
    }

    public Output getUserInfo(String id)
    {
        UserInfoOutput output = new UserInfoOutput().fromEntity(userRepository.findOne(id));
        return outputOk(output);
    }

    public byte[] getUserAvatar(String UserId)
    {
        byte[] result = fileManager.findOne("UserAvatar/" + UserId);
        if (result == null)
        {
            fileManager.findOne("UserAvatar");
        }
        return result;
    }

    public Output setUserAvatar(byte[] data)
    {
        fileManager.save("UserAvatar/" + Context.getUserId(), data);
        return outputOk();
    }
}
