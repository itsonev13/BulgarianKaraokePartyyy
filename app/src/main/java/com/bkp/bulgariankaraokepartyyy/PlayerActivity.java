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
    private static boolean updateIsStoped = false;

    private Button btnPause, btnPrev, btnNext, btnFF, btnFR, btnLoop, btnShuffle;
    private TextView txtSongName, txtSongStart, txtSongStop;
    private SeekBar seekMusic;
    private BlastVisualizer mVisualizer;
    private String sname;
    private ImageView imageView;

    private int position;

    private ArrayList<Song> mySongs;
    private Thread updateSeekbar;

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
        btnPause = findViewById(R.id.btnPlayPlayer);
        btnNext = findViewById(R.id.btnNextPlayer);
        btnPrev = findViewById(R.id.btnPrevious);
        btnFF = findViewById(R.id.btnFForwardPlayer);
        btnFR = findViewById(R.id.btnFReversePlayer);
        btnShuffle = findViewById(R.id.btnShuffle);
        btnLoop = findViewById(R.id.btnLoop);

        mVisualizer = findViewById(R.id.blastVisualizer);
        imageView = findViewById(R.id.imageView);

        updateSeekbar = new Thread() {
            @Override
            public void run() {
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;

                while (currentPosition < totalDuration) {
                    try {
                        sleep(500);

                        if (updateIsStoped) {
                            break;
                        }

                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekMusic.setProgress(currentPosition);
                    } catch (InterruptedException | IllegalStateException e) {
                        e.printStackTrace();
                    }
                }

                seekMusic.setProgress(0);
                updateIsStoped = false;
            }
        };

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        Intent currIntent = getIntent();
        Bundle bundle = currIntent.getExtras();

        mySongs = new ArrayList<>(bundle.getParcelableArrayList("songs"));

        sname = mySongs.get(position).getName();
        //String songName = currIntent.getStringExtra("songName");
        txtSongName.setSelected(true);

        position = bundle.getInt("pos", 0);
        Uri uri = Uri.parse(mySongs.get(position).getSource());
        sname = mySongs.get(position).getName();
        txtSongName.setText(sname);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btnNext.performClick();
            }
        });

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

        updateSeekbar.start();

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

        btnShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Collections.shuffle(mySongs);
                Toast.makeText(getApplicationContext(), "Shuffling songs", Toast.LENGTH_SHORT).show();
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                seekMusic.setMax(mediaPlayer.getDuration());
                if (mediaPlayer.isPlaying()) {
                    btnPause.setBackgroundResource(R.drawable.ic_play);
                    mediaPlayer.pause();

                } else {
                    btnPause.setBackgroundResource(R.drawable.ic_pause);
                    mediaPlayer.start();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = new MediaPlayer();
                position = ((position + 1) % mySongs.size());

                Uri u = Uri.parse(mySongs.get(position).getSource());
                try {
                    mediaPlayer.setDataSource(u.toString());
                    mediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        sname = mySongs.get(position).getName();
                        txtSongName.setText(sname);
                        seekMusic.setMax(mediaPlayer.getDuration());
                        seekMusic.setProgress(0);
                        mediaPlayer.start();
                        btnPause.setBackgroundResource(R.drawable.ic_pause);
                        startAnimation(imageView);
                        String endTime = createTime(mediaPlayer.getDuration());
                        txtSongStop.setText(endTime);
                        int audioSessionId = mediaPlayer.getAudioSessionId();
                        if (audioSessionId != -1) {
                            mVisualizer.setAudioSessionId(audioSessionId);
                        }
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                btnNext.performClick();
                            }
                        });
                    }
                });
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position - 1) < 0) ? (mySongs.size() - 1) : (position - 1);

                Uri u = Uri.parse(mySongs.get(position).getSource());
                //  mediaPlayer = MediaPlayer.create(getApplicationContext(),u);
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(u.toString());
                    mediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        sname = mySongs.get(position).getName();
                        txtSongName.setText(sname);
                        seekMusic.setMax(mediaPlayer.getDuration());
                        seekMusic.setProgress(0);
                        mediaPlayer.start();
                        btnPause.setBackgroundResource(R.drawable.ic_pause);
                        startAnimationl2r(imageView);
                        String endTime = createTime(mediaPlayer.getDuration());
                        txtSongStop.setText(endTime);
                        int audioSessionId = mediaPlayer.getAudioSessionId();
                        if (audioSessionId != -1)
                            mVisualizer.setAudioSessionId(audioSessionId);
                    }
                });
            }
        });

        btnFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    startColorAnimation(v, btnFF);
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10000);
                }
            }
        });

        btnFR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    startColorAnimation(v, btnFR);
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 10000);
                }
            }
        });

        btnLoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Intent mIntent = new Intent(PlayerActivity.this, MainActivity.class);
            sname = mySongs.get(position).getName();
            mIntent.putExtra(EXTRA_NAME, sname);
            startActivity(mIntent);
            updateIsStoped = true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String name = intent.getStringExtra("mainActivitySongName");
        position = intent.getIntExtra("pos", 0);
        txtSongName.setText(name);
        String endTime = createTime(mediaPlayer.getDuration());
        seekMusic.setMax(mediaPlayer.getDuration());
        txtSongStop.setText(endTime);

        //visualizer
        int audioSessionId = mediaPlayer.getAudioSessionId();
        if (audioSessionId != -1)
            mVisualizer.setAudioSessionId(audioSessionId);


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btnNext.performClick();
                seekMusic.setProgress(0);
            }
        });

        updateSeekbar = new Thread() {

            @Override
            public void run() {
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;
                updateIsStoped = false;

                while (currentPosition < totalDuration) {
                    try {
                        sleep(500);

                        if (updateIsStoped) {
                            break;
                        }

                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekMusic.setProgress(currentPosition);
                    } catch (InterruptedException | IllegalStateException e) {
                        e.printStackTrace();

                    }
                }
                seekMusic.setProgress(0);
                updateIsStoped = false;
            }
        };

        updateSeekbar.start();
    }

    public boolean onTouchEvent(MotionEvent touchEvent) {
        float x1 = touchEvent.getX();
        float y1 = touchEvent.getY();

        if (touchEvent.getAction() == MotionEvent.ACTION_UP) {
            if (x1 < y1) {
                //Left
                Intent i = new Intent(PlayerActivity.this, KaraokeActivity.class)
                        .putExtra("songs", mySongs)
                        .putExtra("songname", "Nqkvo ime")
                        .putExtra("pos", 1);

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

    public void startAnimation(View view) {
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

    public void startAnimationl2r(View view) {
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
