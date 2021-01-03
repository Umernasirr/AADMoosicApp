package com.umernasirr.moosicapp;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class MusicPlayerActivity extends AppCompatActivity {

    TextView playerPosition, playerDuration;
    SeekBar seekBar;
    ImageView btRew, btPlay, btPause, btFf;

    MediaPlayer mediaPlayer;
    Handler handler = new Handler();
    Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musicplayer);

        playerPosition = findViewById(R.id.player_position);
        playerDuration = findViewById(R.id.player_duration);
        seekBar = findViewById(R.id.seek_bar);
        btRew = findViewById(R.id.bt_rew);
        btPlay = findViewById(R.id.bt_play);
        btPause = findViewById(R.id.bt_pause);
        btFf = findViewById(R.id.bt_ff);
        //mediaplayerthingyyy
        mediaPlayer = MediaPlayer.create(this, R.raw.song);

        //runnable

        runnable = new Runnable() {
            @Override
            public void run() {
                //progress On SeekBar
                seekBar.setProgress(mediaPlayer.getCurrentPosition());

                //HandlerDelayy for 0.5 sec
                handler.postDelayed(this, 500);

            }
        };

        //Getduration

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
                    //draggo seek baaar
                    mediaPlayer.seekTo(progress);
                }

                Log.d("myTag", "hi");

                Log.d("myTag", playerDuration+ "");
                Log.d("myTag", playerPosition +"");

                playerPosition.setText(convertFormat(mediaPlayer.getCurrentPosition()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

    }});
     mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btPause.setVisibility(View.GONE);

                btPlay.setVisibility(View.VISIBLE);

                mediaPlayer.seekTo(0);
            }
        });
    @SuppressLint("DefaultLocale")
    private String convertFormat(int duration){
      return String.format("%02d:%02d" , TimeUnit.MILLISECONDS.toMinutes(duration),
              TimeUnit.MILLISECONDS.toSeconds(duration),
              TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));

       

    }

   



}
