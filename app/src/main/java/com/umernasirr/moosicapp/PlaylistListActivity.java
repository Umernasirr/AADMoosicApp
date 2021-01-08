package com.umernasirr.moosicapp;

import android.content.Intent;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PlaylistListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    PlaylistData playListData;
    RecyclerView rvPlaylist;
    PlaylistListAdapter playlistListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_list);

        Toolbar toolbar = findViewById(R.id.playlistListToolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayoutPlaylist);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openNavDrawer, R.string.closeNavDrawer);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();
        NavigationView navViewListings = findViewById(R.id.navViewPlaylist);

        navViewListings.setNavigationItemSelectedListener(this);


        rvPlaylist = (RecyclerView) findViewById(R.id.rvPlaylist);


        SearchView searchViewPlaylist = (SearchView) findViewById(R.id.searchViewPlaylist);

        Retrofit retrofit = RetrofitFactory.getRetrofit();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<PlaylistResponse> call = apiInterface.getPlaylist();

        call.enqueue(new Callback<PlaylistResponse>() {
            @Override
            public void onResponse(Call<PlaylistResponse> call, Response<PlaylistResponse> response) {
                playListData = new PlaylistData();
                rvPlaylist = findViewById(R.id.rvPlaylist);

                PlaylistResponse result = response.body();

                Gson gson = new Gson();
                String json = gson.toJson( response.body());
                Log.e("testing", json);


                for (int i = 0; i < result.getData().size(); i++) {
                    PlaylistModel playListModel = new PlaylistModel(result.getData().get(i).get_id(), result.getData().get(i).getName(), result.getData().get(i).getSong());
                    playListData.playlistList.add(playListModel);
                }
                playlistListAdapter = new PlaylistListAdapter(PlaylistListActivity.this, playListData.playlistList);

                rvPlaylist.setAdapter(playlistListAdapter);
                rvPlaylist.setLayoutManager(new LinearLayoutManager(PlaylistListActivity.this));
            }

            @Override
            public void onFailure(Call<PlaylistResponse> call, Throwable t) {
                Log.d("myTag", "Error");
            }
        });


        searchViewPlaylist.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (playlistListAdapter != null) {

                    playlistListAdapter.getFilter().filter(newText);
                }

                return false;
            }
        });

        Button btnCreatePlaylist = (Button) findViewById(R.id.btnCreatePlaylist);

        btnCreatePlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreatePlaylistActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
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

            case R.id.mitemPosts:
                intent = new Intent(this, PostListActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
