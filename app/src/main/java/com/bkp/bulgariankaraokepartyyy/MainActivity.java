package com.bkp.bulgariankaraokepartyyy;

import android.Manifest;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO Animation to change logo with text box

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final Database db = new Database(this);
    private final customAdapter customAdapter = new customAdapter();

    private DrawerLayout drawerLayout;
    private ListView listView;
    private String[] items;
    private Button btnNext, btnPrevious, btnPause;
    private ImageButton searchBtn;
    private TextView bottomPanel, searchQuery;
    private int position;
    private static ArrayList<Song> mySongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        listView = findViewById(R.id.listViewSongs);
        bottomPanel = findViewById(R.id.bottomPanelMain);

        mySongs = db.getAllSongs();

        runtimePermission();

        btnNext = findViewById(R.id.btnNextMain);
        btnPrevious = findViewById(R.id.btnPreviousMain);
        btnPause = findViewById(R.id.btnPauseMain);
        searchBtn = findViewById(R.id.SearchBtn);
        searchQuery = findViewById(R.id.SearchBox);

        Intent currIntent = getIntent();
        Bundle bundle = currIntent.getExtras();
        if(bundle != null){
            position = bundle.getInt("position", 0);
        }


        if (PlayerActivity.mediaPlayer != null) {
            PlayerActivity.mediaPlayer.setOnCompletionListener(mediaPlayer -> btnNext.performClick());
        }

        searchBtn.setOnClickListener(view -> {
            String query = searchQuery.getText().toString();
            List<Song> songs = db.getSongsByName(query);
            mySongs = new ArrayList<>(songs);
            items = new String[songs.size()];
            for (int i = 0; i < songs.size(); i++) {
                items[i] = songs.get(i).getName();
            }

            customAdapter.notifyDataSetChanged();

        });

        searchQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    items = new String[mySongs.size()];
                    for (int i = 0; i < mySongs.size(); i++) {
                        items[i] = mySongs.get(i).getName();
                    }

                    customAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        bottomPanel.setOnClickListener(view -> {
            if (PlayerActivity.mediaPlayer == null || bottomPanel.getText().toString().equals("")) {
                Toast.makeText(MainActivity.this, "No song is playing", Toast.LENGTH_SHORT).show();
            } else {
                Intent mIntent = new Intent(MainActivity.this, PlayerActivity.class);
                mIntent.putExtra("mainActivitySongName", bottomPanel.getText().toString());
                mIntent.putExtra("position", position);
                mIntent.putExtra("from", "main");
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(mIntent);
            }
        });

        btnPause.setOnClickListener(view -> {
            if (PlayerActivity.mediaPlayer != null) {
                if (PlayerActivity.mediaPlayer.isPlaying()) {
                    btnPause.setBackgroundResource(R.drawable.ic_play_circle);
                    PlayerActivity.mediaPlayer.pause();

                } else {
                    btnPause.setBackgroundResource(R.drawable.ic_pause_circle);
                    PlayerActivity.mediaPlayer.start();
                }
            }
        });

        btnPrevious.setOnClickListener(view -> {
            if (PlayerActivity.mediaPlayer != null) {
                for (int i = 0; i < mySongs.size(); i++) {
                    if (mySongs.get(i).getName().equals(bottomPanel.getText().toString())) {
                        PlayerActivity.mediaPlayer.stop();
                        PlayerActivity.mediaPlayer.release();

                        if (i == 0) {
                            Uri u = Uri.parse(mySongs.get(mySongs.size() - 1).getMainSource());
                            PlayerActivity.mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                            String name = mySongs.get(mySongs.size() - 1).getName();
                            bottomPanel.setText(name);
                            PlayerActivity.mediaPlayer.start();
                            position = mySongs.size() - 1;
                        } else {
                            Uri u = Uri.parse(mySongs.get(i - 1).getMainSource());
                            PlayerActivity.mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                            String name = mySongs.get(i - 1).getName();
                            bottomPanel.setText(name);
                            PlayerActivity.mediaPlayer.start();
                            position = i - 1;
                        }
                        return;
                    }
                }
            }
        });

        btnNext.setOnClickListener(view -> {
            if (PlayerActivity.mediaPlayer != null) {
                for (int i = 0; i < mySongs.size(); i++) {
                    if (mySongs.get(i).getName().equals(bottomPanel.getText().toString())) {
                        PlayerActivity.mediaPlayer.stop();
                        PlayerActivity.mediaPlayer.release();

                        if (i == mySongs.size() - 1) {
                            Uri u = Uri.parse(mySongs.get(0).getMainSource());
                            PlayerActivity.mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                            String name = mySongs.get(0).getName();
                            bottomPanel.setText(name);
                            PlayerActivity.mediaPlayer.start();
                            position = 0;
                        } else {
                            Uri u = Uri.parse(mySongs.get(i + 1).getMainSource());
                            PlayerActivity.mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                            String name = mySongs.get(i + 1).getName();
                            bottomPanel.setText(name);
                            PlayerActivity.mediaPlayer.start();
                            position = i + 1;

                        }
                        return;
                    }
                }
            }
        });
    }

    public void runtimePermission() {
        Dexter.withContext(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {

                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        display();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                    }
                }).check();
    }

    public void display() {
        items = new String[mySongs.size()];
        for (int i = 0; i < mySongs.size(); i++) {
            items[i] = mySongs.get(i).getName();
        }

        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String songName = (String) listView.getItemAtPosition(position);
            //String sname = mySongs.get(position).getName();
            startActivity(new Intent(getApplicationContext(), PlayerActivity.class)
                    .putExtra("songs", mySongs)
                    .putExtra("songName", songName)
                    .putExtra("position", position)
                    .putExtra("from", "main"));
        });
    }

    public class customAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.list_song, null);
            TextView textSong = view.findViewById(R.id.txtsongname);
            textSong.setSelected(true);
            textSong.setText(items[position]);
            return view;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.navonline:
                download();
                Toast.makeText(this, "Online Library here", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navradio:
                Intent radioIntent = new Intent(MainActivity.this, RadioActivity.class);
                startActivity(radioIntent);
                Toast.makeText(this, "Radio here", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startActivity(startMain);
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        if (PlayerActivity.mediaPlayer != null) {
            if (PlayerActivity.mediaPlayer.isPlaying()) {
                btnPause.setBackgroundResource(R.drawable.ic_pause_circle);
            } else {
                btnPause.setBackgroundResource(R.drawable.ic_play_circle);
            }
            Intent i = getIntent();
            String songName = i.getStringExtra(PlayerActivity.EXTRA_NAME);
            bottomPanel.setText(songName);
            bottomPanel.setSelected(true);
        } else {
            btnPause.setBackgroundResource(R.drawable.ic_play_circle);
        }
        super.onResume();
    }

    public void download() {
        DownloadMusic.download(db);

        mySongs = db.getAllSongs();

        items = new String[mySongs.size()];
        for (int i = 0; i < mySongs.size(); i++) {
            items[i] = mySongs.get(i).getName();
        }

        customAdapter.notifyDataSetChanged();
    }
}
