package com.umernasirr.moosicapp;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("auth/login")
    Call<LoginResponse> login(@Body UserModel user);
}
