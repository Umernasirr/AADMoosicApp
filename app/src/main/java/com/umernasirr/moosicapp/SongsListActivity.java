package com.umernasirr.moosicapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SongsListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<SongModel> songsList;
    SongListAdapter songListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

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

        SongsData songsData = SongsData.getInstance();

        RecyclerView rvSongsList = findViewById(R.id.rvSongList);

         songsList = songsData.songsList;
         songListAdapter = new SongListAdapter(this, songsData.songsList);


//        rvSongsList.setAdapter(songListAdapter);
        rvSongsList.setLayoutManager(new LinearLayoutManager(this));


        SearchView searchViewSongs = findViewById(R.id.searchViewSongs);
        Call<SongsResponse> call = apiInterface.getSongs();


        call.enqueue(new Callback<SongsResponse>() {
            @Override
            public void onResponse(Call<SongsResponse> call, Response<SongsResponse> response) {

                SongsResponse result = response.body();
//                Gson gson = new Gson();
//                String json = gson.toJson(result.getData().get(0).toString());
                Log.d("Login", result.getData().get(0).getUser());

        for(int i = 0 ; i<= result.getData().size(); i++){
            SongModel songModel = new SongModel(1,result.getData().get(i).getDescription(), result.getData().get(i).getUrl(), 1,"talal" );
            Log.d("Login", songModel.toString());
//            songsData.songsList.add(songModel);
        }
//                Log.d("Login", songsData.songsList.toString());
//                SongListAdapter songListAdapter = new SongListAdapter(SongsListActivity.this, songsData.songsList);


            }

            @Override
            public void onFailure(Call<SongsResponse> call, Throwable t) {

            }
        });

        searchViewSongs.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                songListAdapter.getFilter().filter(newText);
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

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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

        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
