package application.service;

import application.entity.Image;
import application.entity.User;
import application.model.Output;
import application.model.OutputResult;
import application.model.account.*;
import application.repository.ImageRepository;
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
    private ImageRepository imageRepository;

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
        Image image = imageRepository.findOne("UserAvatar/" + UserId);
        if (image == null)
        {
            image = imageRepository.findOne("UserAvatar");
        }
        return image.getData();
    }

    public Output setUserAvatar(byte[] data)
    {
        Image image = new Image();
        image.setId("UserAvatar/" + User.getUserId());
        image.setData(data);
        imageRepository.save(image);
        return outputOk();
    }
}
