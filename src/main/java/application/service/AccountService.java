package application.service;

import application.entity.File;
import application.entity.User;
import application.model.Output;
import application.model.account.*;
import application.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.IOException;

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
        if (userRepository.findFirstByUsername(input.getUsername())
                .isPresent())
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
        UserInfoOutput output = userRepository.findFirstById(id, UserInfoOutput.class);
        return outputOk(output);
    }

    public byte[] getUserAvatar(UserIdInput input)
    {
        return fileManager.find("UserAvatar/" + input.getId())
                .orElse(fileManager.find("UserAvatar")
                        .orElse(null));
    }

    public Output setUserAvatar(SetUserAvatarInput input) throws IOException
    {
        File file = input.toEntity();
        fileManager.save(file);
        return outputOk();
    }
}
