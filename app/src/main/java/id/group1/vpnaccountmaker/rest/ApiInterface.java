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
}
