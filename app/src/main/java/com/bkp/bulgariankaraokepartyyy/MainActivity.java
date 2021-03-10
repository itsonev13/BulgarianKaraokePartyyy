package com.bkp.bulgariankaraokepartyyy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//TODO Animation to change logo with text box
//TODO Implement radio activity
//TODO Implement Cloud storage
//TODO Download files from cloud

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    final Database db = new Database(this);
    ListView listView;
    String[] items;
    Button hbtnnext,hbtnprev,hbtnpause;
    TextView txtnp;
    customAdapter customAdapter = new customAdapter();
    int position;
   public static ArrayList<Song>  mySongs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySongs = db.getAllSongs();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        listView = findViewById(R.id.listviewsongs);
        txtnp = findViewById(R.id.txtnp);

        runtimepermission();

        hbtnnext = findViewById(R.id.hbtnnext);
        hbtnprev = findViewById(R.id.hbtnprev);
        hbtnpause = findViewById(R.id.hbtnpause);


        if(PlayerActivity.mediaPlayer!=null) {
            PlayerActivity.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                    hbtnnext.performClick();
                }
            });
        }
        txtnp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PlayerActivity.mediaPlayer==null || txtnp.getText().toString().equals(""))
                {
                    Toast.makeText(MainActivity.this, "No song is playing", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent mIntent=new Intent(MainActivity.this, PlayerActivity.class);
                    mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(mIntent);
                }
            }
        });


        hbtnpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PlayerActivity.mediaPlayer!=null)
                {
                    if (PlayerActivity.mediaPlayer.isPlaying())
                    {
                        hbtnpause.setBackgroundResource(R.drawable.ic_play_circle);
                        PlayerActivity.mediaPlayer.pause();

                    }
                    else
                    {
                        hbtnpause.setBackgroundResource(R.drawable.ic_pause_circle);
                        PlayerActivity.mediaPlayer.start();
                    }
                }
            }
        });


        hbtnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PlayerActivity.mediaPlayer!=null)
                {
                    for(int i=0 ;i<mySongs.size() ;i++) {
                        if(mySongs.get(i).getName().equals(txtnp.getText().toString())) {
                            PlayerActivity.mediaPlayer.stop();
                            PlayerActivity.mediaPlayer.release();
                            if(i== mySongs.size()-1){

                                Uri u = Uri.parse(mySongs.get(0).getSource());
                                PlayerActivity.mediaPlayer = MediaPlayer.create(getApplicationContext(),u);
                               String name = mySongs.get(0).getName();
                                txtnp.setText(name);
                                PlayerActivity.mediaPlayer.start();
                                return;
                            }
                            else{
                                Uri u = Uri.parse(mySongs.get(i+1).getSource());
                                PlayerActivity.mediaPlayer = MediaPlayer.create(getApplicationContext(),u);
                                String name = mySongs.get(i+1).getName();
                                txtnp.setText(name);
                                PlayerActivity.mediaPlayer.start();
                                return;

                            }
                        }
                    }

                }
            }
        });
        hbtnprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PlayerActivity.mediaPlayer!=null)
                {
                    for(int i=0 ;i<mySongs.size() ;i++) {
                        if(mySongs.get(i).getName().equals(txtnp.getText().toString())) {
                            PlayerActivity.mediaPlayer.stop();
                            PlayerActivity.mediaPlayer.release();
                            if(i==0){

                                Uri u = Uri.parse(mySongs.get(mySongs.size()-1).toString());
                                PlayerActivity.mediaPlayer = MediaPlayer.create(getApplicationContext(),u);
                                String name = mySongs.get(mySongs.size()-1).getName();
                                txtnp.setText(name);
                                PlayerActivity.mediaPlayer.start();
                                return;
                            }
                            else{
                                Uri u = Uri.parse(mySongs.get(i-1).getSource());
                                PlayerActivity.mediaPlayer = MediaPlayer.create(getApplicationContext(),u);
                                String name = mySongs.get(i-1).getName();
                                txtnp.setText(name);
                                PlayerActivity.mediaPlayer.start();
                                return;

                            }
                        }
                    }
                }
            }
        });


    }

    public void runtimepermission()
    {
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



    void display()
    {


        items = new String[mySongs.size()];
        for (int i=0;i<mySongs.size();i++)
        {
            items[i] = mySongs.get(i).getName();
        }

        listView.setAdapter(customAdapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String songName = (String) listView.getItemAtPosition(position);
                String sname = mySongs.get(position).getName();
                startActivity(new Intent(getApplicationContext(),PlayerActivity.class)
                .putExtra("songs", mySongs)
                        .putExtra("songname",songName)
                        .putExtra("pos", position));


            }
        });
    }
    class customAdapter extends BaseAdapter
    {

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

            View view = getLayoutInflater().inflate(R.layout.list_song,null);
            TextView textsong = view.findViewById(R.id.txtsongname);
            textsong.setSelected(true);
            textsong.setText(items[position]);
            return view;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.navalbums:
                Toast.makeText(this, "Albums here", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navartists:
                Toast.makeText(this, "Artists here", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navsongs:
                Toast.makeText(this, "All songs here", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navonline:
                download();
                Toast.makeText(this, "Online Library here", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navradio:
                Intent radiointent = new Intent(MainActivity.this,RadioActivity.class);
                startActivity(radiointent);
                Toast.makeText(this, "Radio here", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startActivity(startMain);
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        if (PlayerActivity.mediaPlayer!=null)
        {
            if (PlayerActivity.mediaPlayer.isPlaying())
            {
                hbtnpause.setBackgroundResource(R.drawable.ic_pause_circle);
            }
            else {
                hbtnpause.setBackgroundResource(R.drawable.ic_play_circle);
            }
            Intent i = getIntent();
            String songName = i.getStringExtra(PlayerActivity.EXTRA_NAME);
            txtnp.setText(songName);
            txtnp.setSelected(true);
        }
        else
        {
            hbtnpause.setBackgroundResource(R.drawable.ic_play_circle);
        }
        super.onResume();
    }
    public void download(){
        ArrayList<Song> cloudSongs = new ArrayList<>();
        cloudSongs.add(new Song("Eminem - Not afraid","https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/Eminem%20-%20Not%20Afraid%20(Official%20Video).mp3?alt=media&token=c12d4c2b-b95e-41a6-a776-29f738739349"));
        cloudSongs.add( new Song("Bad Meets Evil - Fast Lane ft. Eminem, Royce Da 5'9","https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/Bad%20Meets%20Evil%20-%20Fast%20Lane%20ft.%20Eminem%2C%20Royce%20Da%205'9.mp3?alt=media&token=3b0cafaa-ec19-44b7-98a7-5280c3abce91"));
        cloudSongs.add( new Song("Eminem - Sing For The Moment (Official Music Video)","https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/Eminem%20-%20Sing%20For%20The%20Moment%20(Official%20Music%20Video).mp3?alt=media&token=b14ee08b-d87d-4edc-ba64-55e5a3321937"));
        cloudSongs.add(new Song("MC Hammer - U Can't Touch This (Official Music Video)","https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/MC%20Hammer%20-%20U%20Can't%20Touch%20This%20(Official%20Music%20Video).mp3?alt=media&token=b8518a85-9730-4e11-bbb9-c53b3ccf32ae"));
        cloudSongs.add( new Song("Mozart - Lacrimosa","https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/Mozart%20-%20Lacrimosa.mp3?alt=media&token=c343b4af-d601-49c5-9841-e20671587c57"));
        cloudSongs.add( new Song("Pitbull - Hey Baby (Drop It To The Floor) ft. T-Pain.mp3","https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/Pitbull%20-%20Hey%20Baby%20(Drop%20It%20To%20The%20Floor)%20ft.%20T-Pain.mp3?alt=media&token=5335b768-47cc-4f3a-baf0-5136266f8d00"));
        cloudSongs.add( new Song("Ricky Martin - Livin' La Vida Loca","https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/Ricky%20Martin%20-%20Livin'%20La%20Vida%20Loca.mp3?alt=media&token=1ccbfc3d-f462-4aba-bc30-168acad27e9d"));
        cloudSongs.add( new Song("Saweetie - Best Friend (feat. Doja Cat) [Official Music Video]","https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/Saweetie%20-%20Best%20Friend%20(feat.%20Doja%20Cat)%20%5BOfficial%20Music%20Video%5D.mp3?alt=media&token=aa8e61cb-7320-4c7a-83e7-8f81468a6b27"));
        cloudSongs.add( new Song("Timbaland - The Way I Are ft. Keri Hilson, D.O.E., Sebastian (Official Music Video)","https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/Timbaland%20-%20The%20Way%20I%20Are%20ft.%20Keri%20Hilson%2C%20D.O.E.%2C%20Sebastian%20(Official%20Music%20Video).mp3?alt=media&token=7ac824b7-5ef9-4a0a-8bb1-22ac474bf35b"));
        cloudSongs.add( new Song("Timbaland - The Way I Are ft. Keri Hilson, D.O.E., Sebastian (Official Music Video)","https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/Timbaland%20-%20The%20Way%20I%20Are%20ft.%20Keri%20Hilson%2C%20D.O.E.%2C%20Sebastian%20(Official%20Music%20Video).mp3?alt=media&token=7ac824b7-5ef9-4a0a-8bb1-22ac474bf35b"));
        cloudSongs.add( new Song("S.A.R.S. - Perspektiva (Official Video)","https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/S.A.R.S.%20-%20Perspektiva%20(Official%20Video).mp3?alt=media&token=aaf7c0b2-b0b3-4b9a-88db-6a18fe55f547"));
        cloudSongs.add( new Song("G-Eazy - I Mean It (Official Music Video) ft. Remo","https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/G-Eazy%20-%20I%20Mean%20It%20(Official%20Music%20Video)%20ft.%20Remo.mp3?alt=media&token=bf81e315-1b0c-462a-811d-a7719f5d1800"));
        cloudSongs.add( new Song("Future - Life Is Good (Official Music Video) ft. Drake","https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/Future%20-%20Life%20Is%20Good%20(Official%20Music%20Video)%20ft.%20Drake.mp3?alt=media&token=9fbbf31f-4bfe-4dcd-a56d-00113ba4d9f1"));
        cloudSongs.add( new Song("Jack Harlow - WHATS POPPIN feat. Dababy, Tory Lanez, & Lil Wayne [Official Video]","https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/Jack%20Harlow%20-%20WHATS%20POPPIN%20feat.%20Dababy%2C%20Tory%20Lanez%2C%20%26%20Lil%20Wayne%20%5BOfficial%20Video%5D.mp3?alt=media&token=9fbf6297-de63-4ed7-a704-7d89d25914fc"));

        for (Song s :cloudSongs) {

            List<Song> songCheck = db.getSongsByName(s.getName());
            if(songCheck.size()==0){
                db.addSong(s);
            }
        }
        mySongs = db.getAllSongs();
        customAdapter.notifyDataSetChanged();


    }

}
