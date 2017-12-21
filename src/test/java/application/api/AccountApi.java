package application.api;

import application.model.Output;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface AccountApi
{
    String pubUrl = "/api/account/";

    @POST(pubUrl + "register")
    @FormUrlEncoded
    Call<Output> register(@Field("username") String username, @Field("password") String password);

    @POST(pubUrl + "login")
    @FormUrlEncoded
    Call<Output> login(@Field("username") String username, @Field("password") String password, @Field("remember-me") boolean remember);

    @POST(pubUrl + "logout")
    Call<Output> logout();

    @POST(pubUrl + "updatePassword")
    @FormUrlEncoded
    Call<Output> updatePassword(@Field("password") String password);

    @POST(pubUrl + "setUserAvatar")
    @Multipart
    Call<Output> setUserAvatar(@Part MultipartBody.Part file);

    @GET(pubUrl + "getUserAvatar")
    Call<ResponseBody> getUserAvatar();

    @GET(pubUrl + "getUserAvatar")
    Call<ResponseBody> getUserAvatar(@Query("id") String id);

    @GET(pubUrl + "getUserInfo")
    Call<Output> getUserInfo();

    @GET(pubUrl + "getUserInfo")
    Call<Output> getUserInfo(@Query("id") String id);
}
