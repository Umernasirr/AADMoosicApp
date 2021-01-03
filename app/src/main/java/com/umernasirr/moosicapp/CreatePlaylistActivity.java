package com.umernasirr.moosicapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CreatePlaylistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createplaylist);

        Button btnGoback = (Button) findViewById(R.id.btnGoback);
        Button btnSavePlaylist= (Button) findViewById(R.id.btnSavePlaylist);
        TextView txtPlaylistName = (TextView) findViewById(R.id.txtPlaylistName);

        btnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlaylistListActivity.class);
                startActivity(intent);
            }
        });


    String playlistName = txtPlaylistName.getText().toString();
        btnSavePlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playlistName.equals("")){
                    PlaylistModel playlistModel = new PlaylistModel(1,playlistName, new ArrayList<SongModel>() );



                    PlaylistData playlistData = PlaylistData.getInstance();
                    playlistData.playlistList.add(playlistModel);
                    Toast.makeText(getApplicationContext(),"Playlist Created", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), PlaylistListActivity.class);
                    startActivity(intent);
                }
            }
        });




    }
}
