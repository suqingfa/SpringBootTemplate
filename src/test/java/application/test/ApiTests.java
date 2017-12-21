package application.test;

import application.api.AccountApi;
import application.model.Output;
import lombok.extern.slf4j.Slf4j;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.net.CookieManager;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@Slf4j
public class ApiTests
{
    protected final Retrofit retrofit;

    public ApiTests()
    {
        OkHttpClient client = new OkHttpClient
                .Builder()
                .readTimeout(600, TimeUnit.SECONDS)
                .connectTimeout(600, TimeUnit.SECONDS)
                .cookieJar(new JavaNetCookieJar(new CookieManager()))
                .build();

        retrofit = new Retrofit
                .Builder()
                .baseUrl("http://localhost:8080/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void login() throws IOException
    {
        AccountApi accountApi = retrofit.create(AccountApi.class);

        String username = "username";
        String password = "password";

        Output output = accountApi.login(username, password, true).execute().body();
        if (output.getInfo() != Output.Info.OK)
        {
            output = accountApi.register(username, password).execute().body();
            assertEquals(output.getInfo(), Output.Info.OK);
            log.info("register username:{}", username);

            output = accountApi.login(username, password, true).execute().body();
        }

        assertEquals(output.getInfo(), Output.Info.OK);
    }
}
