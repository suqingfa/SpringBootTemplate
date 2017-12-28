package application.test;

import application.api.AccountApi;
import application.model.Output;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
public class AccountTests extends ApiTests
{
    private AccountApi accountApi;

    public AccountTests() throws IOException
    {
        login();
        accountApi = retrofit.create(AccountApi.class);
    }

    @Test
    public void updatePassword() throws IOException
    {
        Output output = accountApi.updatePassword("password")
                .execute()
                .body();
        assertEquals(output.getInfo(), Output.Info.OK);
        log.info(output.toString());
    }

    @Test
    public void setUserAvatar() throws IOException
    {
        File file = new File("G:\\图片\\FIL1173.JPG");
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        Output output = accountApi.setUserAvatar(body)
                .execute()
                .body();
        assertEquals(output.getInfo(), Output.Info.OK);
        log.info(output.toString());
    }

    @Test
    public void getUserAvatar() throws IOException
    {
        ResponseBody body = accountApi.getUserAvatar()
                .execute()
                .body();
        assertNotNull(body);
        log.info("length: {}", body.contentLength());
    }

    @Test
    public void getUserInfo() throws IOException
    {
        Output output = accountApi.getUserInfo()
                .execute()
                .body();
        assertEquals(output.getInfo(), Output.Info.OK);
        log.info(output.toString());
    }
}
