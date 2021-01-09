package com.umernasirr.moosicapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SongsListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<SongModel> songsList;
    SongListAdapter songListAdapter;
    RecyclerView rvSongsList;
    SongsData songsData;
    String playlist_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        Intent intent = getIntent();

        playlist_id = (String) intent.getStringExtra("playlistid");




        Toolbar toolbar = findViewById(R.id.songListToolbar);
        setSupportActionBar(toolbar);
        Retrofit retrofit = RetrofitFactory.getRetrofit();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayoutSongsList);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openNavDrawer, R.string.closeNavDrawer);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();
        NavigationView navViewListings = findViewById(R.id.navViewListings);

        navViewListings.setNavigationItemSelectedListener(this);

        SearchView searchViewSongs = findViewById(R.id.searchViewSongs);

        if (playlist_id != null) {
            Log.d("testing","https://moosikk.herokuapp.com/api/v1/playlist/" + playlist_id);

            Call<PlaylistResponseById> call;

            call = apiInterface.getPlaylistbyId("https://moosikk.herokuapp.com/api/v1/playlist/" + playlist_id);

            call.enqueue(new Callback<PlaylistResponseById>() {
                @Override
                public void onResponse(Call<PlaylistResponseById> call, Response<PlaylistResponseById> response) {

                    songsData = new SongsData();
                    rvSongsList = findViewById(R.id.rvSongList);

                    PlaylistResponseById result = response.body();

                    Gson gson = new Gson();

                    String json = gson.toJson(response.body().getData());


                    if (result.getData() != null) {

                        ArrayList<SongModel> newSongModel = response.body().getData().get(0).getSong();

                        songListAdapter = new SongListAdapter(SongsListActivity.this, newSongModel);

                        rvSongsList.setAdapter(songListAdapter);
                        rvSongsList.setLayoutManager(new LinearLayoutManager(SongsListActivity.this));
                    }
                }

                @Override
                public void onFailure(Call<PlaylistResponseById> call, Throwable t) {
                    Log.d("testing", "Error");

                    Gson gson = new Gson();


                    Log.d("testing", t.getMessage());

                }

            });

        } else {
            Call<SongsResponse> call;
            call = apiInterface.getSongs();

            call.enqueue(new Callback<SongsResponse>() {
                @Override
                public void onResponse(Call<SongsResponse> call, Response<SongsResponse> response) {
                    songsData = new SongsData();
                    rvSongsList = findViewById(R.id.rvSongList);


                    SongsResponse result = response.body();
                    if (result.getData() != null) {

                        for (int i = 0; i < result.getData().size(); i++) {
                            SongModel songModel = new SongModel(result.getData().get(i).get_id(), result.getData().get(i).getDescription(), result.getData().get(i).getUser() , result.getData().get(i).getUrl(), result.getData().get(i).getCreatedAt()  ,  result.getData().get(i).get__v()   );
                            songsData.songsList.add(songModel);
                        }


                        songListAdapter = new SongListAdapter(SongsListActivity.this, songsData.songsList);

                        rvSongsList.setAdapter(songListAdapter);
                        rvSongsList.setLayoutManager(new LinearLayoutManager(SongsListActivity.this));
                    }
                }

                @Override
                public void onFailure(Call<SongsResponse> call, Throwable t) {
                    Log.d("myTag", "Error");
                }
            });

            searchViewSongs.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (songListAdapter != null) {
                        songListAdapter.getFilter().filter(newText);
                    }
                    return false;

                }
            });

            Button btnCreateSong = findViewById(R.id.btnCreateSong);

            btnCreateSong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), CreateSongActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
    public boolean onNavigationItemSelected (@NonNull MenuItem item){
        Intent intent;
        switch (item.getItemId()) {
            case R.id.mitemProfile:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;

            case R.id.mitemListings:
                intent = new Intent(this, SongsListActivity.class);
                startActivity(intent);
                break;

            case R.id.mitemUpload:
                intent = new Intent(this, CreateSongActivity.class);
                startActivity(intent);
                break;

            case R.id.mitemPlaylist:
                intent = new Intent(this, PlaylistListActivity.class);
                startActivity(intent);
                break;

            case R.id.mitemPosts:
                intent = new Intent(this, PostListActivity.class);
                startActivity(intent);
                break;

            case R.id.mitemLogout:

                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();

                editor.putString("token", "");
                editor.putBoolean("isAuthenticated",false);
                editor.putString("user", "");
                editor.commit();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);

                break;

        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged ( boolean hasCapture){

    }
}
