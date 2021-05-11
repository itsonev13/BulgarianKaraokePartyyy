package com.bkp.bulgariankaraokepartyyy;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Име на базата
    private static final String DATABASE_NAME = "Bulgarify";
    private static final String TABLE_SONGS = "songs";
    private static final String TABLE_LYRICS = "songs_lyrics";

    // Имена на колоните на Таблицата
    private static final String KEY_SONGS_ID = "id";
    private static final String KEY_SONGS_NAME = "name";
    private static final String KEY_SONGS_MAIN_SOURCE = "mainSource";
    private static final String KEY_SONGS_INSTRUMENTAL_SOURCE = "instrumentalSource";

    private static final String KEY_SONGS_LYRICS_ID = "id";
    private static final String KEY_SONGS_LYRICS_TEXT = "lyric";
    private static final String KEY_SONGS_LYRICS_FROM = "from_sec";
    private static final String KEY_SONGS_LYRICS_TO = "to_sec";
    private static final String KEY_SONGS_LYRICS_SONG_ID = "song_id";

    // Създаване на Таблицата
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LYRICS);

        String CREATE_SONGS_TABLE = "CREATE TABLE " + TABLE_SONGS +
                "("
                + KEY_SONGS_ID + " INTEGER PRIMARY KEY,"
                + KEY_SONGS_NAME + " TEXT,"
                + KEY_SONGS_MAIN_SOURCE + " TEXT,"
                + KEY_SONGS_INSTRUMENTAL_SOURCE + " TEXT"
                + ")";

        String CREATE_SONGS_LYRICS_TABLE = "CREATE TABLE " + TABLE_LYRICS +
                "("
                + KEY_SONGS_LYRICS_ID + " INTEGER PRIMARY KEY,"
                + KEY_SONGS_LYRICS_TEXT + " TEXT,"
                + KEY_SONGS_LYRICS_FROM + " INT,"
                + KEY_SONGS_LYRICS_TO + " INT,"
                + KEY_SONGS_LYRICS_SONG_ID + " INT,"
                + "FOREIGN KEY (" + KEY_SONGS_LYRICS_SONG_ID + ") REFERENCES " + TABLE_SONGS + "(" + KEY_SONGS_ID + ")"
                + ")";

        db.execSQL(CREATE_SONGS_TABLE);
        db.execSQL(CREATE_SONGS_LYRICS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LYRICS);

        // Повторно създаване на базата от данни
        onCreate(db);
    }

    // Добавяне на нова песен
    long addSong(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SONGS_NAME, song.getName());
        values.put(KEY_SONGS_MAIN_SOURCE, song.getMainSource());
        values.put(KEY_SONGS_INSTRUMENTAL_SOURCE, song.getInstrumentalSource());
        long songId = db.insert(TABLE_SONGS, null, values);

        // Затравяне на връзката с базата от данни
        db.close();
        return songId;
    }

    // Добавяне на текст към песен
    void addLyrics(Map<Integer,String> lyrics, long songId){
        SQLiteDatabase db = this.getWritableDatabase();

        for (Map.Entry<Integer, String>  entry: lyrics.entrySet()) {
            ContentValues lyricsValues = new ContentValues();
            lyricsValues.put(KEY_SONGS_LYRICS_TEXT, entry.getValue());
            lyricsValues.put(KEY_SONGS_LYRICS_FROM, entry.getKey());
            lyricsValues.put(KEY_SONGS_LYRICS_SONG_ID, songId);
            db.insert(TABLE_LYRICS, null, lyricsValues);
        }

        db.close();
    }

    // Вземане на всички песни
    public ArrayList<Song> getAllSongs() {
        ArrayList<Song> songsList = new ArrayList<>();
        // избор на всички
        String selectQuery = "SELECT * FROM " + TABLE_SONGS;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Song song = new Song();
                song.setId(Integer.parseInt(cursor.getString(0)));
                song.setName(cursor.getString(1));
                song.setMainSource(cursor.getString(2));
                song.setInstrumentalSource(cursor.getString(3));
                songsList.add(song);
            } while (cursor.moveToNext());
        }
        return songsList;
    }

    // Взимане на песен по id
    public Map<Integer, String> getSongLyricsBySongId(int songId){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectLyricsQuery = "SELECT * FROM " + TABLE_LYRICS + " WHERE " + KEY_SONGS_LYRICS_SONG_ID + " = " + "'" + songId + "'";

        Cursor cursor = db.rawQuery(selectLyricsQuery, null);

        Map<Integer, String> songLyrics = new HashMap<>(30);

        if (cursor.moveToFirst()) {
            do {
                songLyrics.put(cursor.getInt(2), cursor.getString(1));
            } while (cursor.moveToNext());
        }

        return songLyrics;
    }

    // Взимане на песен по име
    public List<Song> getSongsByName(String name) {
        List<Song> songsList = new ArrayList<>();
        // избор на всички
        String selectQuery = "SELECT * FROM " + TABLE_SONGS + " WHERE " + KEY_SONGS_NAME + " LIKE " + "\"%" + name + "%\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Song song = new Song();
                song.setId(Integer.parseInt(cursor.getString(0)));
                song.setName(cursor.getString(1));
                song.setMainSource(cursor.getString(2));
                song.setInstrumentalSource(cursor.getString(3));

                // Добавяне на песен в колекцията
                songsList.add(song);
            } while (cursor.moveToNext());
        }

        // Връщане на колекция от песни
        return songsList;
    }

    // Взимане на песен по id
    Song getSong(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SONGS, new String[]{KEY_SONGS_ID,
                        KEY_SONGS_NAME, KEY_SONGS_MAIN_SOURCE, KEY_SONGS_INSTRUMENTAL_SOURCE}, KEY_SONGS_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null,
                null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        Song song = new Song(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));

        return song;
    }

    // Изтриване на всички песни
    public void dropSongs() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);
    }

    // Изтриване на текстовете на песните
    public void dropSongsLyrics(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LYRICS);
    }

    // Обновяване на дадена песен
    public int updateSongs(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SONGS_NAME, song.getName());
        values.put(KEY_SONGS_MAIN_SOURCE, song.getMainSource());
        values.put(KEY_SONGS_INSTRUMENTAL_SOURCE, song.getInstrumentalSource());
        // Обновяване на ред
        return db.update(TABLE_SONGS, values, KEY_SONGS_ID + " = ?",
                new String[]{String.valueOf(song.getId())});
    }

    // Изтриване на песен по име
    public void deleteSongsByName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SONGS, KEY_SONGS_NAME + " = ?",
                new String[]{String.valueOf(name)});
        db.close();
    }

    // Взимане на броя на песните
    public int getSongsCount() {
        String countQuery = "SELECT * FROM " + TABLE_SONGS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}