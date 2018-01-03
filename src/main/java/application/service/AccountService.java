package application.service;

import application.entity.File;
import application.entity.User;
import application.model.*;
import application.model.account.*;
import application.repository.FileRepository;
import application.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static application.model.Output.*;

@Service
@Transactional
public class AccountService
{
    @Resource
    private UserRepository userRepository;
    @Resource
    private FileRepository fileRepository;

    public Output register(RegisterInput input)
    {
        if (userRepository.findFirstByUsername(input.getUsername())
                .isPresent())
        {
            return outputUsernameExist();
        }

        userRepository.save(input.toEntity(User.class));

        return outputOk();
    }

    public Output updatePassword(UpdatePasswordInput input)
    {
        User user = userRepository.getCurrentUser();
        input.update(user);
        userRepository.save(user);
        return outputOk();
    }

    public Output getUserInfo(IdInput input)
    {
        Optional<UserOutput> optional = userRepository.findFirstById(input.getId(), UserOutput.class);
        return output(optional);
    }

    public Output ListUser(PageInput input)
    {
        List<UserOutput> outputs = userRepository.findAllByIdNotNull(input.getPageable());
        return outputOk(outputs);
    }

    public byte[] getUserAvatar(IdInput input)
    {
        return fileRepository.find("UserAvatar/" + input.getId())
                .orElse(fileRepository.find("UserAvatar")
                        .orElse(null));
    }

    public Output setUserAvatar(SetUserAvatarInput input)
    {
        File file = input.toEntity(File.class);
        fileRepository.save(file);
        return outputOk();
    }
}
