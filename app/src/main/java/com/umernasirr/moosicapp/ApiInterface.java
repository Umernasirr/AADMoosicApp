package com.umernasirr.moosicapp;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface ApiInterface {
    @POST("auth/login")
    Call<AuthResponse> login(@Body UserModel user);

    @POST("auth/register")
    Call<AuthResponse> register(@Body UserModel user);

    @GET("song")
    Call<SongsResponse> getSongs();


    @Headers({"Accept: application/json"})
    @GET
    Call<PlaylistResponseById> getPlaylistbyId(@Url String url);

    @GET("playlist")
    Call<PlaylistResponse> getPlaylist();

    @POST("playlist/add-playlist")
    Call<PlaylistResponse> addPlaylist(@Body PlaylistModel playlistModel);

    @PUT("playlist/add-song-to-playlist")
    Call<SongAddToPlaylist> songAddToPlaylist(@Body SongAddToPlaylist songAddToPlaylist);

    @DELETE
    Call<Object> deletePlaylist(@Url String url);

    @GET("post")
    Call<PostResponse> getPosts();

    @POST("post")
    Call<PostAdd> addPost(@Body PostAdd postModel);

    @Multipart
    @POST("song/add-song")
    Call<ResponseBody> addSong(
            @Part("_id") RequestBody _id,
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file
    );



}
