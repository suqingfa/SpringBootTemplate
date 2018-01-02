package application.model.account;

import application.ContextHolder;
import application.entity.File;
import application.model.ModelToEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;

@Getter
@Setter
@Validated
public class SetUserAvatarInput extends ModelToEntity<File>
{
    @NotNull
    private MultipartFile file;

    @Override
    protected void set(File file) throws IOException
    {
        file.setId("UserAvatar/" + ContextHolder.getUserIdOptional());
        file.setData(this.file.getBytes());
    }
}
