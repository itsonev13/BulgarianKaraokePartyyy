package com.bkp.bulgariankaraokepartyyy;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

public class KaraokeActivity extends AppCompatActivity {
    private Button btnPause, btnPrev, btnNext;
    private TextView txtSongName;

    private String songName;
    private int position;
    private ArrayList<Song> mySongs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karakoke);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD700")));
        getSupportActionBar().setTitle("Karaoke");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtSongName = findViewById(R.id.txtSongName);

        btnPause = findViewById(R.id.btnPlay);
        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrevious);

        if (PlayerActivity.mediaPlayer != null) {
            PlayerActivity.mediaPlayer.pause();
        }

        Intent currIntent = getIntent();
        Bundle bundle = currIntent.getExtras();

        mySongs = new ArrayList<>(bundle.getParcelableArrayList("songs"));

        songName = mySongs.get(position).getName();
        txtSongName.setSelected(true);

        position = bundle.getInt("position", 0);
        Uri uri = Uri.parse(mySongs.get(position).getLyrics());
        songName = mySongs.get(position).getName();
        txtSongName.setText(songName);

        int currMediaPlayerPosition = PlayerActivity.mediaPlayer.getCurrentPosition();

        PlayerActivity.mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        PlayerActivity.mediaPlayer.start();

        if(PlayerActivity.mediaPlayer != null){
            PlayerActivity.mediaPlayer.seekTo(currMediaPlayerPosition);
            PlayerActivity.mediaPlayer.setOnCompletionListener(mediaPlayer -> btnNext.performClick());
        }

        btnPause.setOnClickListener(view -> {
            if (PlayerActivity.mediaPlayer.isPlaying()) {
                btnPause.setBackgroundResource(R.drawable.ic_play);
                PlayerActivity.mediaPlayer.pause();

            } else {
                btnPause.setBackgroundResource(R.drawable.ic_pause);
                PlayerActivity.mediaPlayer.start();
            }
        });

        btnNext.setOnClickListener(view -> {
            PlayerActivity.mediaPlayer.pause();
            PlayerActivity.mediaPlayer = new MediaPlayer();

            Uri u = Uri.parse(mySongs.get(position).getSource());

            try {
                PlayerActivity.mediaPlayer.setDataSource(u.toString());
                PlayerActivity.mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }

            PlayerActivity.mediaPlayer.setOnPreparedListener(mediaPlayer -> {
                songName = mySongs.get(position).getName();
                txtSongName.setText(songName);

                mediaPlayer.start();
                btnPause.setBackgroundResource(R.drawable.ic_pause);

                mediaPlayer.setOnCompletionListener(mp -> btnNext.performClick());
            });
        });

        btnPrev.setOnClickListener(view -> {
            PlayerActivity.mediaPlayer.pause();

            Uri u = Uri.parse(mySongs.get(position).getSource());
            PlayerActivity.mediaPlayer = new MediaPlayer();

            try {
                PlayerActivity.mediaPlayer.setDataSource(u.toString());
                PlayerActivity.mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }

            PlayerActivity.mediaPlayer.setOnPreparedListener(mediaPlayer -> {
                songName = mySongs.get(position).getName();
                txtSongName.setText(songName);

                mediaPlayer.start();
                btnPause.setBackgroundResource(R.drawable.ic_pause);
            });
        });
    }

    public boolean onTouchEvent(MotionEvent touchEvent) {
        float x1 = touchEvent.getX();
        float y1 = touchEvent.getY();

        if(touchEvent.getAction() == MotionEvent.ACTION_UP) {
            if (x1 > y1) {
                //Right
                Intent toPlayerActivity = new Intent(KaraokeActivity.this, PlayerActivity.class);

                toPlayerActivity.putExtra("songs", mySongs);
                toPlayerActivity.putExtra("songName", songName);
                toPlayerActivity.putExtra("position", position);
                toPlayerActivity.putExtra("from", "karaoke");

                startActivity(toPlayerActivity);
                this.overridePendingTransition(R.anim.swipe_right_animation_enter, R.anim.swipe_right_animation_leave);

                return true;
            }
        }

        return true;
    }
}
