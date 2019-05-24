package com.example.heroapp.HeroesApi;

import com.example.heroapp.Model.Heroes;
import com.example.heroapp.Model.ImageResponse;
import com.example.heroapp.Model.LoginSignupResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface HeroesAPI {

    @GET("heroes")
    Call<List<Heroes>> getHeroes();

    @POST("heroes")
    Call<Void> addHero(@Body Heroes heroes);

    @FormUrlEncoded
    @POST("heroes")
    Call<Void> addHero(@Field("name") String name,@Field("desc") String des, @Field("image") String filename) ;

    @Multipart
    @POST("upload")
    Call<ImageResponse> uploadImage(@Part MultipartBody.Part img);

    @FormUrlEncoded
    @POST("heroes")
    Call<Void> addMapHero(@FieldMap Map<String, String> map);


    @FormUrlEncoded
    @POST("users/login")

    Call<LoginSignupResponse> checkUser(@Field("username")String username,@Field("password") String password);

}
