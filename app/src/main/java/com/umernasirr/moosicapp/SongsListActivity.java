package com.umernasirr.moosicapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SongsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);


        SongsData songsData = SongsData.getInstance();

        RecyclerView rvSongsList = (RecyclerView) findViewById(R.id.rvSongList);

        ArrayList<SongModel> songsList = songsData.songsList;
        SongListAdapter songListAdapter = new SongListAdapter(this, songsData.songsList);


        rvSongsList.setAdapter(songListAdapter);
        rvSongsList.setLayoutManager(new LinearLayoutManager(this));


        SearchView searchViewSongs = (SearchView) findViewById(R.id.searchViewSongs);

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

        Button btnCreateSong = (Button) findViewById(R.id.btnCreateSong);

        btnCreateSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateSongActivity.class);
                startActivity(intent);
            }
        });





    }
}
