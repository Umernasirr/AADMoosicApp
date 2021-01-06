package com.umernasirr.moosicapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class MusicPlayerActivity extends AppCompatActivity {

    TextView playerPosition, playerDuration;
    SeekBar seekBar;
    ImageView btRew, btPlay, btPause, btFf;
    String song_url;
    String song_name;
    MediaPlayer mediaPlayer;
    Handler handler = new Handler();
    Runnable runnable;
    ProgressBar spinner;
    TextView txtFieldSongName;
    Button btnback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musicplayer);
        Bundle bundle = getIntent().getExtras();

        song_url = bundle.getString("song_url");
        song_name = bundle.getString("song_name");
        playerPosition = findViewById(R.id.player_position);
        playerDuration = findViewById(R.id.player_duration);
        seekBar = findViewById(R.id.seek_bar);
        btRew = findViewById(R.id.bt_rew);
        btPlay = findViewById(R.id.bt_play);
        btPause = findViewById(R.id.bt_pause);
        btFf = findViewById(R.id.bt_ff);
        mediaPlayer = new MediaPlayer();
        spinner = findViewById(R.id.loaderMusic);
        txtFieldSongName = findViewById(R.id.txtFieldSongName);
        btnback = (Button) findViewById(R.id.btnback);

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

        int duration = mediaPlayer.getDuration();

        // convert ms to sec/min
        String sDuration = convertFormat(duration);

        //SetDuration
        playerDuration.setText(sDuration);

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
                String sDuration = convertFormat(duration);

                playerDuration.setText(sDuration);
                // conditioner

                if (mediaPlayer.isPlaying() && duration != currentPosition) {

                    // fast forward 5 sec

                    currentPosition = currentPosition + 5000;

                    playerPosition.setText(convertFormat(currentPosition));

                    mediaPlayer.seekTo(currentPosition);

                }

            }
        });

        btRew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = mediaPlayer.getCurrentPosition();

                if (mediaPlayer.isPlaying() && currentPosition > 5000) {

                    currentPosition = currentPosition - 5000;

                    playerPosition.setText(convertFormat(currentPosition));

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

    private void prepareMediaPlayer() {


        txtFieldSongName.setText(song_name + "");

        try {
            String urlMusic = "https://moosikk.herokuapp.com/uploads/"+ song_url;

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

                    handler.postDelayed(runnable, 0);
                }

            });


        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }


    @SuppressLint("DefaultLocale")
    private String convertFormat(int duration) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }

}
