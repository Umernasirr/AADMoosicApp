package com.umernasirr.moosicapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    //    Call<List<Post>> getPost();
    @POST("/auth/login")
    Call<UserModel>  login(@Body UserModel userModel);
}
