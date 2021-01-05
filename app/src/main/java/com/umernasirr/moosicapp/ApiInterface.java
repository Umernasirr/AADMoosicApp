package com.umernasirr.moosicapp;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("auth/login")
    Call<AuthResponse> login(@Body UserModel user);

    @POST("auth/register")
    Call<AuthResponse> register(@Body UserModel user);

    @GET("song")
    Call<SongsResponse> getSongs();
}
