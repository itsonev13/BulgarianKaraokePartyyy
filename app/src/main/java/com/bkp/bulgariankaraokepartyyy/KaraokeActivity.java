package com.bkp.bulgariankaraokepartyyy;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class KaraokeActivity extends AppCompatActivity {
    final Database db = new Database(this);
    int position;
    public static ArrayList<Song> mySongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_karakoke);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7453C8")));
        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        mySongs = new ArrayList<>( bundle.getParcelableArrayList("songs"));

        if (PlayerActivity.mediaPlayer != null) {
            PlayerActivity.mediaPlayer.pause();
            //PlayerActivity.mediaPlayer.stop();
            //PlayerActivity.mediaPlayer.release();
        }

        Uri u = Uri.parse(mySongs.get(1).getLyrics());
        PlayerActivity.mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
        String name = mySongs.get(0).getName();

        PlayerActivity.mediaPlayer.start();
        position = 0;
    }

    public boolean onTouchEvent(MotionEvent touchEvent) {
        float x1 = touchEvent.getX();
        float y1 = touchEvent.getY();

        if(touchEvent.getAction() == MotionEvent.ACTION_UP) {
            if (x1 > y1) {
                //Right
                Intent toPlayerActivity = new Intent(KaraokeActivity.this, PlayerActivity.class);
                position = toPlayerActivity.getIntExtra("pos", 0);

                toPlayerActivity.putExtra("mainActivitySongName", "Nqkva si tam pesen");
                toPlayerActivity.putExtra("pos", position);
                toPlayerActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                toPlayerActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(toPlayerActivity);
                this.overridePendingTransition(R.anim.swipe_right_animation_enter, R.anim.swipe_right_animation_leave);

                return true;
            }
        }

        return true;
    }

}
