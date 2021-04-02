package com.bkp.bulgariankaraokepartyyy;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Име на базата
    private static final String DATABASE_NAME = "Bulgarify";
    private static final String TABLE_CONTACTS = "songs";
    // Имена на колоните на Таблицата
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SOURCE = "source";
    private static final String KEY_LYRICS = "lyrics";

    // Създаване на Таблицата
    // Създаване на Таблицата
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS +
                "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_SOURCE + " TEXT,"
                + KEY_LYRICS +" TEXT"+")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        // Повторно създаване на базата от данни
        onCreate(db);
    }

    // Добавяне на нов Потребител
    void addSong(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, song.getName());
        values.put(KEY_SOURCE, song.getSource());
        values.put(KEY_LYRICS, song.getLyrics());
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Затравяне на връзката с базата от данни
    }
    // Взимане на Потребител
    Song getSong(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_SOURCE,KEY_LYRICS }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null,
                null);
        if (cursor != null)
            cursor.moveToFirst();
        Song song = new Song(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3));
        return song;
    }
    // Вземане на всички Потребители
    public ArrayList<Song> getAllSongs() {
        ArrayList<Song> songsList = new ArrayList<>();
        // избор на всички
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Song song = new Song();
                song.setId(Integer.parseInt(cursor.getString(0)));
                song.setName(cursor.getString(1));
                song.setSource(cursor.getString(2));
                song.setLyrics(cursor.getString(3));
                songsList.add(song);
            } while (cursor.moveToNext());
        }
        return songsList;
    }
    public void dropSongs(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
    }

    public List<Song> getSongsByName(String name){
        List<Song> songsList = new ArrayList<>();
        // избор на всички
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS+ " WHERE "+ KEY_NAME +" LIKE "+"\"%"+name+"%\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Song song = new Song();
                song.setId(Integer.parseInt(cursor.getString(0)));
                song.setName(cursor.getString(1));
                song.setSource(cursor.getString(2));
                song.setLyrics(cursor.getString(3));
                // Добавяне на Потребител в колекцията
                songsList.add(song);
            } while (cursor.moveToNext());
        }
        // Връщане на колекция от Потребители
        return songsList;

    }
    // Обновяване на даден Потребител
    public int updateSongs(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, song.getName());
        values.put(KEY_SOURCE, song.getSource());
        values.put(KEY_LYRICS, song.getLyrics());
        // Обновяване на ред
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(song.getId()) });
    }
    // Изтриване на Потребител
    public void deleteSongsByName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_NAME + " = ?",
                new String[] { String.valueOf(name) });
        db.close();
    }
    public int getSongsCount() {
        String countQuery = "SELECT * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // Връщане на броя
        return cursor.getCount();
    }

    public Database(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
}