package com.umernasirr.moosicapp;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MusicPlayerActivity extends AppCompatActivity {

    TextView PlayerPosition, PlayerDuration, title;
    SeekBar seekBar;
    ImageView btRew, btPlay, btPause, btFf;
    String song_url;
    String song_name;
    String song_id;
    MediaPlayer mediaPlayer;
    Handler handler = new Handler();
    Runnable runnable;
    ProgressBar spinner;
    TextView txtFieldSongName;
    Button btnback;
    ImageView btnAddPlaylist;
    PlaylistData playListData;

     NotificationManager notificationManager;
    //populate wala kaam

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musicplayer);
        Bundle bundle = getIntent().getExtras();

        song_url = bundle.getString("song_url");
        song_name = bundle.getString("song_name");
        song_id = bundle.getString("song_id");

        playerPosition = findViewById(R.id.player_position);
        playerDuration = findViewById(R.id.player_duration);
        seekBar = findViewById(R.id.seek_bar);
        btRew = findViewById(R.id.bt_rew);
        btPlay = findViewById(R.id.bt_play);
        btPause = findViewById(R.id.bt_pause);
        btFf = findViewById(R.id.bt_ff);
        mediaPlayer = new MediaPlayer();
        spinner = findViewById(R.id.loaderMusic);
        spinner.setVisibility(View.GONE);

        txtFieldSongName = findViewById(R.id.txtFieldSongName);
        btnback = (Button) findViewById(R.id.btnback);

        btnAddPlaylist = (ImageView) findViewById(R.id.btnAddPlaylist);


        ArrayList<String> itemsArray = new ArrayList<>();


        Retrofit retrofit = RetrofitFactory.getRetrofit();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<PlaylistResponse> call = apiInterface.getPlaylist();

        call.enqueue(new Callback<PlaylistResponse>() {
            @Override
            public void onResponse(Call<PlaylistResponse> call, Response<PlaylistResponse> response) {
                playListData = new PlaylistData();

                PlaylistResponse result = response.body();
                for (int i = 0; i < result.getData().size(); i++) {
                    Boolean dontAdd = false;
                    PlaylistModel playListModel = new PlaylistModel(result.getData().get(i).get_id(), result.getData().get(i).getName(), result.getData().get(i).getSong());

                    ArrayList<SongModel> songList = result.getData().get(i).getSong();
                    playListData.playlistList.add(playListModel);
                    itemsArray.add(result.getData().get(i).getName());
                }
            }
// chnage from playlist response to playlist response id

            @Override
            public void onFailure(Call<PlaylistResponse> call, Throwable t) {
                Log.d("myTag", "Error");
            }
        });


        btnAddPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builderSingle = new AlertDialog.Builder(MusicPlayerActivity.this);
                builderSingle.setIcon(R.drawable.ic_audio);

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MusicPlayerActivity.this, android.R.layout.select_dialog_singlechoice);
                for (String item : itemsArray) {
                    arrayAdapter.add(item);
                }
                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(MusicPlayerActivity.this);


                        addSongToPlaylist(which);

                        builderInner.setTitle("The song has been added to playlist: ");
                        builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builderInner.show();
                    }
                });
                builderSingle.show();
            }
        });


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SongsListActivity.class);
                startActivity(intent);
            }
        });


        //runnable
        runnable = new Runnable() {
            @Override
            public void run() {
                //progress On SeekBar
                
                seekBar.setProgress(mediaPlayer.getCurrentPosition());

                //HandlerDelay for 0.5 sec
                handler.postDelayed(this, 500);
            }
        };

        //Get Duration

        int duration =mediaPlayer.getDuration();

        // convert ms to sec/min
        String sDuration = convertFormat(duration);

        //SetDuration
        PlayerDuration.setText(sDuration);

        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btPlay.setVisibility(View.GONE);

                btPause.setVisibility((View.VISIBLE));

                mediaPlayer.start();

                seekBar.setMax(mediaPlayer.getDuration());

                handler.postDelayed(runnable, 0);


            }
        });

        btPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btPause.setVisibility(View.GONE);

                btPlay.setVisibility(View.VISIBLE);

                mediaPlayer.pause();

                handler.removeCallbacks(runnable);

            }
        });

        btFf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //current position of player

                int currentPosition = mediaPlayer.getCurrentPosition();

                int duration = mediaPlayer.getDuration();
                // conditioner

                if(mediaPlayer.isPlaying() && duration != currentPosition){

                    // fast forward 5 sec

                    currentPosition = currentPosition + 5000;

                    PlayerPosition.setText(convertFormat(currentPosition));

                    mediaPlayer.seekTo(currentPosition);

                }

            }
        });

        btRew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = mediaPlayer.getCurrentPosition();

                if(mediaPlayer.isPlaying() && currentPosition > 5000){

                    currentPosition = currentPosition - 5000;

                    PlayerPosition.setText(convertFormat(currentPosition));

                    mediaPlayer.seekTo(currentPosition);

                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }


                playerPosition.setText(convertFormat(mediaPlayer.getCurrentPosition()));
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                seekBar.setSecondaryProgress(percent);
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btPause.setVisibility(View.GONE);

                btPlay.setVisibility(View.VISIBLE);

                mediaPlayer.seekTo(0);
            }
        });

        prepareMediaPlayer();

    }


    public void addSongToPlaylist(int position) {
        String playList_id = playListData.playlistList.get(position).getId();

        SongAddToPlaylist songAddToPlaylist = new SongAddToPlaylist(playList_id, song_id);

        Retrofit retrofit = RetrofitFactory.getRetrofit();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);


        Log.d("myTag", songAddToPlaylist.get_id() + "");
        Log.d("myTag", songAddToPlaylist.getSong() + "");

        Call<SongAddToPlaylist> call = apiInterface.songAddToPlaylist(songAddToPlaylist);

        call.enqueue(new Callback<SongAddToPlaylist>() {
            @Override
            public void onResponse(Call<SongAddToPlaylist> call, Response<SongAddToPlaylist> response) {
                Log.d("myTag", "Success");

                Gson gson = new Gson();
                String json = gson.toJson(response.body());
                Log.d("myTag", json.toString());


            }

            @Override
            public void onFailure(Call<SongAddToPlaylist> call, Throwable t) {
                Log.d("myTag", "Error");
            }
        });


    }


    private void prepareMediaPlayer() {
        txtFieldSongName.setText(song_name + "");

        try {
            String urlMusic = "https://moosikk.herokuapp.com/uploads/" + song_url;

            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            mediaPlayer.setDataSource(urlMusic);
            mediaPlayer.prepareAsync(); // prepare async to not block main thread
            spinner.setVisibility(View.VISIBLE);

            //mp3 will be started after completion of preparing...
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer player) {
                    spinner.setVisibility(View.GONE);

                    int duration = mediaPlayer.getDuration();
                    String sDuration = convertFormat(duration);

                    playerDuration.setText(sDuration);

                    btPlay.setVisibility(View.GONE);

                    btPause.setVisibility((View.VISIBLE));

                    mediaPlayer.start();

                    seekBar.setMax(mediaPlayer.getDuration());

//                    handler.postDelayed(runnable, 0);
                }

            });


        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }


    @SuppressLint("DefaultLocale")
    private String convertFormat(int duration){
        return String.format("%02:%02" , TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration),
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));



    }

}
   




