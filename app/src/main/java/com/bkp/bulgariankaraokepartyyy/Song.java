package com.bkp.bulgariankaraokepartyyy;


import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {
    private  int id;
    private String name;
    private String mainSource;
    private String instrumentalSource;


    public Song(){

    }

    public Song(int id, String name, String mainSource, String instrumentalSource) {
        this.id = id;
        this.name = name;
        this.mainSource = mainSource;
        this.instrumentalSource = instrumentalSource;
    }

    public Song(String name, String mainSource, String instrumentalSource) {
        this.name = name;
        this.mainSource = mainSource;
        this.instrumentalSource = instrumentalSource;
    }

    public Song(String name, String mainSource) {
        this.name = name;
        this.mainSource = mainSource;
    }

    protected Song(Parcel in) {
        id = in.readInt();
        name = in.readString();
        mainSource = in.readString();
        instrumentalSource = in.readString();
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMainSource(String mainSource) {
        this.mainSource = mainSource;
    }

    public void setInstrumentalSource(String instrumentalSource) {
        this.instrumentalSource = instrumentalSource;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMainSource() {
        return mainSource;
    }

    public String getInstrumentalSource() {
        return instrumentalSource;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(mainSource);
        dest.writeString(instrumentalSource);
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