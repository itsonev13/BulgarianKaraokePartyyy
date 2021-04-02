package com.bkp.bulgariankaraokepartyyy;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

public class KaraokeActivity extends AppCompatActivity {
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.karaoke_main);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7453C8")));
        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch (touchEvent.getAction()) {
            case MotionEvent.ACTION_UP:
                float x1 = touchEvent.getX();
                float y1 = touchEvent.getY();

                if (x1 > y1) {
                    //Left
                    Intent toPlayerActivity = new Intent(KaraokeActivity.this, PlayerActivity.class);
                    position = toPlayerActivity.getIntExtra("pos", 0);

                    toPlayerActivity.putExtra("mainActivitySongName", "Nqkva si tam pesen");
                    toPlayerActivity.putExtra("pos", position);
                    toPlayerActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    toPlayerActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                    startActivity(toPlayerActivity);
                    this.overridePendingTransition(R.anim.swipe_right_animation_enter, R.anim.swipe_right_animation_leave);
                }

                break;
        }

        return false;
    }

}
