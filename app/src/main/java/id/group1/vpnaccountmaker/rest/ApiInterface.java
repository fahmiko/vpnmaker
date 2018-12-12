package id.group1.vpnaccountmaker.rest;
import id.group1.vpnaccountmaker.model.*;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    @GET("server/all")
    Call<GetServer> getServer();

    @Multipart
    @POST("server/all")
    Call<GetServer> postServer(
            @Part MultipartBody.Part file,
            @Part("name_server") RequestBody name_server,
            @Part("location") RequestBody location,
            @Part("acc_remaining") RequestBody acc_remaining,
            @Part("action") RequestBody action
    );

    @Multipart
    @POST("server/all")
    Call<GetServer> putServer(
            @Part MultipartBody.Part file,
            @Part("id_server") RequestBody idServer,
            @Part("name_server") RequestBody name_server,
            @Part("location") RequestBody location,
            @Part("acc_remaining") RequestBody acc_remaining,
            @Part("action") RequestBody action
    );

    @Multipart
    @POST("server/all")
    Call<GetServer> deleteServer(
            @Part("id_server") RequestBody idServer,
            @Part("action") RequestBody action);

    @GET("user/all")
    Call<GetUser> getUser();

    @Multipart
    @POST("user/all")
    Call<GetUser> postUser(
            @Part MultipartBody.Part file,
            @Part("name") RequestBody name,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("action") RequestBody action
    );

    @Multipart
    @POST("user/all")
    Call<GetUser> putUser(
            @Part MultipartBody.Part file,
            @Part("id_user") RequestBody id_user,
            @Part("name") RequestBody name,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("action") RequestBody action
    );

    @Multipart
    @POST("user/all")
    Call<GetUser> deleteUser(
            @Part("id_user") RequestBody id_user,
            @Part("action") RequestBody action);

    @GET("vpn/all")
    Call<GetAcc> getAcc();

    @Multipart
    @POST("vpn/all")
    Call<GetAcc> postAcc(
            @Part MultipartBody.Part file,
            @Part("user") RequestBody user,
            @Part("server") RequestBody server,
            @Part("active") RequestBody active,
            @Part("action") RequestBody action
    );

    @Multipart
    @POST("vpn/all")
    Call<GetAcc> putAcc(
            @Part MultipartBody.Part file,
            @Part("id") RequestBody id,
            @Part("user") RequestBody user,
            @Part("server") RequestBody server,
            @Part("active") RequestBody active,
            @Part("action") RequestBody action
    );

    @Multipart
    @POST("vpn/all")
    Call<GetAcc> deleteAcc(
            @Part("id") RequestBody id,
            @Part("action") RequestBody action);
}
