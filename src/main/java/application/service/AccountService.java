package application.service;

import application.entity.*;
import application.model.Output;
import application.model.OutputResult;
import application.model.account.*;
import application.repository.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;

@Service
@Transactional
public class AccountService implements OutputResult
{
    @Resource
    private UserRepository userRepository;
    @Resource
    private AuthorityRepository authorityRepository;
    @Resource
    private ImageRepository imageRepository;

    public Output register(RegisterInput input)
    {
        if (userRepository.existsByUsername(input.getUsername()))
            return outputUsernameExist();

        User user = input.toEntity();
        Authority authority = authorityRepository.findByRole(input.getRole());
        user.setAuthorities(new HashSet<>(Arrays.asList(authority)));
        userRepository.save(user);

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
