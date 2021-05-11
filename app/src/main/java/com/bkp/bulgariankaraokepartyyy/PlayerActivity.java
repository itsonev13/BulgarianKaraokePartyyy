package com.bkp.bulgariankaraokepartyyy;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;

import android.animation.ObjectAnimator;

import android.content.Intent;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gauravk.audiovisualizer.visualizer.BlastVisualizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class PlayerActivity extends AppCompatActivity {
    public static final String EXTRA_NAME = "song_name";
    public static MediaPlayer mediaPlayer;

    private Button btnPause, btnPrev, btnNext, btnFF, btnFR, btnLoop, btnShuffle;
    private TextView txtSongName, txtSongStart, txtSongStop;
    private SeekBar seekMusic;
    private BlastVisualizer mVisualizer;
    private String songName;
    private ImageView imageView;

    private int position;

    private ArrayList<Song> mySongs;
    private Thread updateSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD700")));
        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtSongName = findViewById(R.id.txtSongName);
        txtSongStart = findViewById(R.id.txtSongStart);
        txtSongStop = findViewById(R.id.txtSongStop);
        seekMusic = findViewById(R.id.seekBars);
        btnPause = findViewById(R.id.btnPlay);
        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrevious);
        btnFF = findViewById(R.id.btnFForward);
        btnFR = findViewById(R.id.btnFReverse);
        btnShuffle = findViewById(R.id.btnShuffle);
        btnLoop = findViewById(R.id.btnLoop);

        mVisualizer = findViewById(R.id.blastVisualizer);
        imageView = findViewById(R.id.imageView);

        updateSeekBar = new Thread() {
            @Override
            public void run() {
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;

                while (currentPosition < totalDuration) {
                    try {
                        sleep(500);
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekMusic.setProgress(currentPosition);
                    } catch (InterruptedException | IllegalStateException e) {
                        e.printStackTrace();
                    }
                }

                seekMusic.setProgress(0);
            }
        };

        int mediaPlayerCurrPosition = 0;

        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayerCurrPosition = mediaPlayer.getCurrentPosition();
        }

        Intent currIntent = getIntent();
        Bundle bundle = currIntent.getExtras();

        mySongs = new ArrayList<>(bundle.getParcelableArrayList("songs"));

        songName = mySongs.get(position).getName();
        txtSongName.setSelected(true);

        position = bundle.getInt("position", 0);
        Uri uri = Uri.parse(mySongs.get(position).getMainSource());
        songName = mySongs.get(position).getName();
        txtSongName.setText(songName);

        String from = bundle.getString("from");

        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();

        if(from.equals("karaoke")){
            mediaPlayer.seekTo(mediaPlayerCurrPosition);
        }

        mediaPlayer.setOnCompletionListener(mediaPlayer -> btnNext.performClick());

        //visualizer
        int audioSessionId = mediaPlayer.getAudioSessionId();
        if (audioSessionId != -1) {
            mVisualizer.setAudioSessionId(audioSessionId);
        }

        String endTime = createTime(mediaPlayer.getDuration());
        txtSongStop.setText(endTime);

        final Handler handler = new Handler();
        final int delay = 1000; //milliseconds

        handler.postDelayed(new Runnable() {
            public void run() {
                //do something
                String currentTime = createTime(mediaPlayer.getCurrentPosition());
                txtSongStart.setText(currentTime);
                handler.postDelayed(this, delay);
            }
        }, delay);

        seekMusic.setMax(mediaPlayer.getDuration());

        updateSeekBar.start();

        seekMusic.getProgressDrawable().setColorFilter(getResources().getColor(R.color.av_light_blue), PorterDuff.Mode.MULTIPLY);
        seekMusic.getThumb().setColorFilter(getResources().getColor(R.color.av_light_blue), PorterDuff.Mode.SRC_IN);

        seekMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        btnPause.setOnClickListener(view -> {
            seekMusic.setMax(mediaPlayer.getDuration());
            if (mediaPlayer.isPlaying()) {
                btnPause.setBackgroundResource(R.drawable.ic_play);
                mediaPlayer.pause();

            } else {
                btnPause.setBackgroundResource(R.drawable.ic_pause);
                mediaPlayer.start();
            }
        });

        btnNext.setOnClickListener(view -> {
            mediaPlayer.pause();
            mediaPlayer = new MediaPlayer();
            position = ((position + 1) % mySongs.size());

            Uri u = Uri.parse(mySongs.get(position).getMainSource());
            try {
                mediaPlayer.setDataSource(u.toString());
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mediaPlayer.setOnPreparedListener(mediaPlayer -> {
                songName = mySongs.get(position).getName();
                txtSongName.setText(songName);
                seekMusic.setMax(mediaPlayer.getDuration());
                seekMusic.setProgress(0);
                mediaPlayer.start();
                btnPause.setBackgroundResource(R.drawable.ic_pause);
                startAnimationNext(imageView);
                String endTime1 = createTime(mediaPlayer.getDuration());
                txtSongStop.setText(endTime1);
                int audioSessionId1 = mediaPlayer.getAudioSessionId();
                if (audioSessionId1 != -1) {
                    mVisualizer.setAudioSessionId(audioSessionId1);
                }
                mediaPlayer.setOnCompletionListener(mp -> btnNext.performClick());
            });
        });

        btnPrev.setOnClickListener(view -> {
            mediaPlayer.pause();

            position = ((position - 1) < 0) ? (mySongs.size() - 1) : (position - 1);

            Uri u = Uri.parse(mySongs.get(position).getMainSource());
            mediaPlayer = new MediaPlayer();

            try {
                mediaPlayer.setDataSource(u.toString());
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mediaPlayer.setOnPreparedListener(mediaPlayer -> {
                songName = mySongs.get(position).getName();
                txtSongName.setText(songName);
                seekMusic.setMax(mediaPlayer.getDuration());
                seekMusic.setProgress(0);
                mediaPlayer.start();
                btnPause.setBackgroundResource(R.drawable.ic_pause);
                startAnimationPrev(imageView);
                String endTime12 = createTime(mediaPlayer.getDuration());
                txtSongStop.setText(endTime12);
                int audioSessionId12 = mediaPlayer.getAudioSessionId();
                if (audioSessionId12 != -1)
                    mVisualizer.setAudioSessionId(audioSessionId12);
            });
        });

        btnFF.setOnClickListener(view -> {
            if (mediaPlayer.isPlaying()) {
                startColorAnimation(view, btnFF);
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10000);
            }
        });

        btnFR.setOnClickListener(view -> {
            if (mediaPlayer.isPlaying()) {
                startColorAnimation(view, btnFR);
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 10000);
            }
        });

        btnLoop.setOnClickListener(view -> {
            if (mediaPlayer != null) {
                if (mediaPlayer.isLooping()) {
                    mediaPlayer.setLooping(false);
                    btnLoop.setBackgroundResource(R.drawable.ic_repeat);

                    Toast.makeText(getApplicationContext(), "Repeat off", Toast.LENGTH_SHORT).show();

                } else {
                    mediaPlayer.setLooping(true);
                    btnLoop.setBackgroundResource(R.drawable.ic_repeat_one);
                    Toast.makeText(getApplicationContext(), "Repeat on", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnShuffle.setOnClickListener(view -> {

            Collections.shuffle(mySongs);
            Toast.makeText(getApplicationContext(), "Shuffling songs", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent mIntent = new Intent(PlayerActivity.this, MainActivity.class);
            songName = mySongs.get(position).getName();
            mIntent.putExtra(EXTRA_NAME, songName);
            mIntent.putExtra("position", position);
            startActivity(mIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String name = intent.getStringExtra("mainActivitySongName");
        position = intent.getIntExtra("position", 0);
        txtSongName.setText(name);
        String endTime = createTime(mediaPlayer.getDuration());
        seekMusic.setMax(mediaPlayer.getDuration());
        txtSongStop.setText(endTime);

        //visualizer
        int audioSessionId = mediaPlayer.getAudioSessionId();
        if (audioSessionId != -1) {
            mVisualizer.setAudioSessionId(audioSessionId);
        }

        mediaPlayer.setOnCompletionListener(mediaPlayer -> {
            btnNext.performClick();
            seekMusic.setProgress(0);
        });

        updateSeekBar = new Thread() {

            @Override
            public void run() {
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;

                while (currentPosition < totalDuration) {
                    try {
                        sleep(500);
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekMusic.setProgress(currentPosition);
                    } catch (InterruptedException | IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
                seekMusic.setProgress(0);
            }
        };

        mediaPlayer.start();
        updateSeekBar.start();
    }

    public boolean onTouchEvent(MotionEvent touchEvent) {
        float x1 = touchEvent.getX();
        float y1 = touchEvent.getY();

        if (touchEvent.getAction() == MotionEvent.ACTION_UP) {
            if (x1 < y1) {
                //Left
                Intent i = new Intent(PlayerActivity.this, KaraokeActivity.class)
                        .putExtra("songs", mySongs)
                        .putExtra("songName", songName)
                        .putExtra("position", position);

                startActivity(i);
                this.overridePendingTransition(R.anim.swipe_left_animation_enter, R.anim.swipe_left_animation_leave);

                return true;
            }
        }

        return false;
    }

    public String createTime(int duration) {
        String time = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;
        time += min + ":";

        if (sec < 10) {
            time += "0";
        }
        time += sec;

        return time;
    }

    public void startAnimationNext(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f);
        animator.setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator);
        animatorSet.start();

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mVisualizer, "alpha", 0f, 1f);
        animator2.setDuration(4000);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animator2);
        animatorSet2.start();
    }

    public void startAnimationPrev(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "rotation", 360f, 0f);
        animator.setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator);
        animatorSet.start();

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mVisualizer, "alpha", 0f, 1f);
        animator2.setDuration(4000);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animator2);
        animatorSet2.start();
    }

    public void startColorAnimation(View view, Button btn) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(btn, "alpha", 0.7f, 1f);
        animator.setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator);
        animatorSet.start();

    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    protected void onDestroy() {
        if (mVisualizer != null)
            mVisualizer.release();
        super.onDestroy();
    }
}
