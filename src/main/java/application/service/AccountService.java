package application.service;

import application.entity.File;
import application.entity.User;
import application.model.Output;
import application.model.OutputResult;
import application.model.account.*;
import application.repository.FileRepository;
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
    @Resource
    private FileRepository fileRepository;

    public Output register(RegisterInput input)
    {
        if (userRepository.existsByUsername(input.getUsername()))
            return outputUsernameExist();

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

    public Output<UserInfoOutput> getUserInfo(String id)
    {
        UserInfoOutput output = new UserInfoOutput().fromEntity(userRepository.findOne(id));
        return output(output);
    }

    public byte[] getUserAvatar(String UserId)
    {
        File file = fileRepository.findOne("UserAvatar/" + UserId);
        if (file == null)
        {
            file = fileRepository.findOne("UserAvatar");
        }
        return file.getData();
    }

    public Output setUserAvatar(byte[] data)
    {
        File file = new File();
        file.setId("UserAvatar/" + User.getUserId());
        file.setData(data);
        fileRepository.save(file);
        return outputOk();
    }
}
