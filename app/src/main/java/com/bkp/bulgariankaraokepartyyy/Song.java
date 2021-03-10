package com.bkp.bulgariankaraokepartyyy;


import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {
    private  int id;
    private String name;
    private String source;
    private String lyrics;
    ;

    public Song(){

    }

    public Song(int id, String name, String source, String lyrics) {
        this.id = id;
        this.name = name;
        this.source = source;
        this.lyrics = lyrics;
    }

    public Song(String name, String source, String lyrics) {
        this.name = name;
        this.source = source;
        this.lyrics = lyrics;
    }

    public Song(String name, String source) {
        this.name = name;
        this.source = source;
    }

    protected Song(Parcel in) {
        id = in.readInt();
        name = in.readString();
        source = in.readString();
        lyrics = in.readString();
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public String getLyrics() {
        return lyrics;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(source);
        dest.writeString(lyrics);
    }
    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

}