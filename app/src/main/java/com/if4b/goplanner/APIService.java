package com.if4b.goplanner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    @GET("study")
    Call<ValueData<List<Study>>> getStudy();

    @GET("work")
    Call<ValueData<List<Work>>> getWork();

    @GET("Note")
    Call<ValueData<List<Note>>> getNote();

    @FormUrlEncoded
    @POST("auth/login")
    Call<ValueData<User>> login(@Field("username")String username,
                                @Field("password")String password);

    @FormUrlEncoded
    @POST("auth/register")
    Call<ValueData<User>> register(@Field("username")String username,
                                   @Field("password")String password);


}
