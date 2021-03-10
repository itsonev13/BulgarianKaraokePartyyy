package com.bkp.bulgariankaraokepartyyy;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class RadioActivity extends AppCompatActivity {
    Button btnradio;
    boolean prepared = false;
    boolean started = false;

    String stream = "http://46.10.150.243/njoy.mp3";
    MediaPlayer mediaPlayer;
    TextView txtFm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);

        btnradio = findViewById(R.id.btnradio);
        txtFm = findViewById(R.id.txtfm);
        txtFm.setText("106.9 FM");
        btnradio.setEnabled(false);
        btnradio.setText("Loading...");

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        new PlayerTask().execute(stream);
        btnradio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (started)
                {
                    started = false;
                    mediaPlayer.pause();
                    btnradio.setBackgroundResource(R.drawable.ic_play);

                }
                else
                {
                    started = true;
                    mediaPlayer.start();
                    btnradio.setBackgroundResource(R.drawable.ic_pause);

                }

            }

        });
    }
    class PlayerTask extends AsyncTask<String, Void, Boolean>
    {

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.prepare();
                prepared = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            btnradio.setEnabled(true);
            btnradio.setText("");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (started)
        {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (started)
        {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (prepared)
        {
            mediaPlayer.release();
        }
    }
}