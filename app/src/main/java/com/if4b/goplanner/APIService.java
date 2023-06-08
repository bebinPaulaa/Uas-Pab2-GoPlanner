package com.if4b.goplanner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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
    //------------------ POST ----------------------
    @FormUrlEncoded
    @POST("study")
    Call<ValueNoData> addStudy(@Field("content")String content,
                                                @Field("user_id")String userId);
    @FormUrlEncoded
    @POST("work")
    Call<ValueNoData> addWork(@Field("content")String id,
                              @Field("user_id")String userId);

    @FormUrlEncoded
    @POST("note")
    Call<ValueNoData> addNote(@Field("content")String id,
                              @Field("user_id")String userId);
    //------------------ PUT ----------------------
    @FormUrlEncoded
    @PUT("study")
    Call<ValueNoData> updateStudy(@Field("id")String id,
                                  @Field("content")String content);

    @FormUrlEncoded
    @PUT("work")
    Call<ValueNoData> updateWork(@Field("id")String id,
                                  @Field("content")String content);
    @FormUrlEncoded
    @PUT("note")
    Call<ValueNoData> updateNote(@Field("id")String id,
                                 @Field("content")String content);

    //----------------- Delete -------------------
    @DELETE("study/{id}")
    Call<ValueNoData> deleteStudy(@Path("id")String id);
    @DELETE("work/{id}")
    Call<ValueNoData> deleteWork(@Path("id")String id);
    @DELETE("note/{id}")
    Call<ValueNoData> deleteNote(@Path("id")String id);













}
