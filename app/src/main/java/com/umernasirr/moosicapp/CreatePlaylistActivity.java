package com.umernasirr.moosicapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreatePlaylistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createplaylist);

        TextView error = (TextView) findViewById(R.id.txtErrorPlaylist);
    ProgressBar spinner = (ProgressBar) findViewById(R.id.loadPlaylist);
        Retrofit retrofit = RetrofitFactory.getRetrofit();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        spinner.setVisibility(View.GONE);


        Button btnGoback = (Button) findViewById(R.id.btnGoback);
        Button btnSavePlaylist = (Button) findViewById(R.id.btnSavePlaylist);
        EditText edtTxtPlaylistName = (EditText) findViewById(R.id.edtTxtPlaylistName);

        btnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlaylistListActivity.class);
                startActivity(intent);
            }
        });


        btnSavePlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);

                String playlistName = edtTxtPlaylistName.getText().toString();

                if (!playlistName.equals("")) {

                    PlaylistModel playlistModel = new PlaylistModel(playlistName, "5fda76c5f866c90bf03054bf");

                    Call<PlaylistResponse> call = apiInterface.addPlaylist(playlistModel);

                    call.enqueue(new Callback<PlaylistResponse>() {
                        @Override
                        public void onResponse(Call<PlaylistResponse> call, Response<PlaylistResponse> response) {
                            spinner.setVisibility(View.GONE);

                            PlaylistResponse result = response.body();

                            if (String.valueOf(response.code()).equals("200")) {
                                Toast.makeText(getApplicationContext(), "Successfully Created Playlist", Toast.LENGTH_SHORT).show();

                            } else {
                                AuthResponse message = new Gson().fromJson(response.errorBody().charStream(), AuthResponse.class);

                                error.setText(message.getError());

                            }
                        }

                        @Override
                        public void onFailure(Call<PlaylistResponse> call, Throwable t) {
                            spinner.setVisibility(View.GONE);
                            error.setText("Please check your internet connection");
                        }
                    });


                    Toast.makeText(getApplicationContext(), "Playlist Created", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), PlaylistListActivity.class);
                    startActivity(intent);
                }
            }
        });


    }
}
