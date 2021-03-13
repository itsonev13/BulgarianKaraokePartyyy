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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gauravk.audiovisualizer.visualizer.BlastVisualizer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class PlayerActivity extends AppCompatActivity {
    Button btnpause,btnprev,btnnext,btnff,btnfr,btnloop,btnshuff;
    TextView txtsn,txtsstart,txtsstop;
    SeekBar seekmusic;
    BlastVisualizer mVisualizer;
    String sname;
    ImageView imageView;
    public static final String EXTRA_NAME = "song_name";

    static MediaPlayer mediaPlayer;
    int position;

    ArrayList<Song> mySongs;
    Thread updateSeekbar;
    static boolean updateIsStoped= false;


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home)
        {
            Intent mIntent=new Intent(PlayerActivity.this, MainActivity.class);
            sname = mySongs.get(position).getName();
            mIntent.putExtra(EXTRA_NAME, sname);
            startActivity(mIntent);
            updateIsStoped = true ;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String name = intent.getStringExtra("mainActivitySongName");
        position = intent.getIntExtra("pos",0);
        txtsn.setText(name);
        String endtime = createtime(mediaPlayer.getDuration());
        seekmusic.setMax(mediaPlayer.getDuration());
        txtsstop.setText(endtime);
        //visualizer
        int audioSessionId = mediaPlayer.getAudioSessionId();
        if (audioSessionId != -1)
            mVisualizer.setAudioSessionId(audioSessionId);
        //
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btnnext.performClick();
            }
        });
        updateSeekbar = new Thread()
        {
            @Override
            public void run() {
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;
                updateIsStoped= false;
                while (currentPosition<totalDuration )
                {
                    try {
                        sleep(500);
                        if(updateIsStoped)
                            break;
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekmusic.setProgress(currentPosition);
                    }
                    catch (InterruptedException | IllegalStateException e)
                    {
                        e.printStackTrace();

                    }
                }
                seekmusic.setProgress(0);
                updateIsStoped=false;
            }
        };
        updateSeekbar.start();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD700")));
        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        btnshuff = findViewById(R.id.btnshuffle);
        btnpause = findViewById(R.id.playbtn);
        btnnext = findViewById(R.id.btnnext);
        btnprev = findViewById(R.id.btnprev);
        txtsn = findViewById(R.id.txtsn);
        seekmusic = findViewById(R.id.seekbars);
        txtsstart  =findViewById(R.id.txtsstart);
        txtsstop = findViewById(R.id.txtsstop);
        btnff = findViewById(R.id.btnff);
        btnfr = findViewById(R.id.btnfr);
        btnloop = findViewById(R.id.btnloop);

        mVisualizer = findViewById(R.id.blastvisualizer);
        imageView = findViewById(R.id.imageview);


        updateSeekbar = new Thread()
        {
            @Override
            public void run() {
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;

                while (currentPosition<totalDuration)
                {
                    try {
                        sleep(500);
                        if(updateIsStoped)
                            break;
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekmusic.setProgress(currentPosition);
                    }
                    catch (InterruptedException | IllegalStateException e)
                    {
                        e.printStackTrace();

                    }
                }
                seekmusic.setProgress(0);
                updateIsStoped=false;
            }
        };

        if (mediaPlayer!=null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }


        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        mySongs = (ArrayList) bundle.getParcelableArrayList("songs");

        sname = mySongs.get(position).getName();
        String songName = i.getStringExtra("songname");
        txtsn.setSelected(true);

        position = bundle.getInt("pos",0);
        Uri uri = Uri.parse(mySongs.get(position).getSource());
        sname = mySongs.get(position).getName();
        txtsn.setText(sname);

        mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
        mediaPlayer.start();


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btnnext.performClick();
            }
        });

        //visualizer
        int audioSessionId = mediaPlayer.getAudioSessionId();
        if (audioSessionId != -1)
            mVisualizer.setAudioSessionId(audioSessionId);



        String endtime = createtime(mediaPlayer.getDuration());
        txtsstop.setText(endtime);

        final Handler handler = new Handler();
        final int delay = 1000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                //do something
                String currenttime = createtime(mediaPlayer.getCurrentPosition());
                txtsstart.setText(currenttime);
                handler.postDelayed(this, delay);
            }
        }, delay);

        seekmusic.setMax(mediaPlayer.getDuration());

        updateSeekbar.start();

        seekmusic.getProgressDrawable().setColorFilter(getResources().getColor(R.color.av_light_blue), PorterDuff.Mode.MULTIPLY);
        seekmusic.getThumb().setColorFilter(getResources().getColor(R.color.av_light_blue),PorterDuff.Mode.SRC_IN);

        seekmusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
        btnshuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Collections.shuffle(mySongs);
                Toast.makeText(getApplicationContext(),"Suffling songs", Toast.LENGTH_SHORT).show();
            }
        });

        btnpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                seekmusic.setMax(mediaPlayer.getDuration());
                if (mediaPlayer.isPlaying())
                {
                    btnpause.setBackgroundResource(R.drawable.ic_play);
                    mediaPlayer.pause();

                }
                else
                {
                    btnpause.setBackgroundResource(R.drawable.ic_pause);
                    mediaPlayer.start();
                }
            }
        });

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = new MediaPlayer();
                position = ((position+1)%mySongs.size());

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
                        txtsn.setText(sname);
                        seekmusic.setMax(mediaPlayer.getDuration());
                        seekmusic.setProgress(0);
                        mediaPlayer.start();
                        btnpause.setBackgroundResource(R.drawable.ic_pause);
                        startAnimation(imageView);
                        String endТime = createtime(mediaPlayer.getDuration());
                        txtsstop.setText(endТime);
                        int audioSessionId = mediaPlayer.getAudioSessionId();
                        if (audioSessionId != -1)
                            mVisualizer.setAudioSessionId(audioSessionId);
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                btnnext.performClick();
                            }
                        });
                    }
                });
            }
        });

        btnprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position-1)<0)?(mySongs.size()-1):(position-1);

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
                        txtsn.setText(sname);
                        seekmusic.setMax(mediaPlayer.getDuration());
                        seekmusic.setProgress(0);
                        mediaPlayer.start();
                        btnpause.setBackgroundResource(R.drawable.ic_pause);
                        startAnimationl2r(imageView);
                        String endТime = createtime(mediaPlayer.getDuration());
                        txtsstop.setText(endТime);
                        int audioSessionId = mediaPlayer.getAudioSessionId();
                        if (audioSessionId != -1)
                            mVisualizer.setAudioSessionId(audioSessionId);
                    }
                });
            }
        });

        btnff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying())
                {
                    startColorAnimation(v,btnff);
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+10000);
                }
            }
        });
        btnfr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying())
                {
                    startColorAnimation(v,btnfr);
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-10000);
                }
            }
        });
        btnloop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer!=null)
                {
                    if (mediaPlayer.isLooping())
                    {
                        mediaPlayer.setLooping(false);
                        btnloop.setBackgroundResource(R.drawable.ic_repeat);

                        Toast.makeText(getApplicationContext(),"Repeat off", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        mediaPlayer.setLooping(true);
                        btnloop.setBackgroundResource(R.drawable.ic_repeat_one);
                        Toast.makeText(getApplicationContext(),"Repeat on", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }

    public String createtime(int duration)
    {
        String time = "";
        int min = duration/1000/60;
        int sec = duration/1000%60;
        time+= min + ":";

        if (sec<10)
        {
            time+= "0";
        }
        time+=sec;

        return time;
    }
    public void startAnimation(View view)
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "rotation", 0f , 360f);
        animator.setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator);
        animatorSet.start();

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mVisualizer, "alpha", 0f , 1f);
        animator2.setDuration(4000);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animator2);
        animatorSet2.start();
    }
    public void startAnimationl2r(View view)
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "rotation", 360f , 0f);
        animator.setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator);
        animatorSet.start();

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mVisualizer, "alpha", 0f , 1f);
        animator2.setDuration(4000);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animator2);
        animatorSet2.start();
    }
    public void startColorAnimation(View view,Button btn){
        ObjectAnimator animator = ObjectAnimator.ofFloat(btn, "alpha", 0.7f , 1f);
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
