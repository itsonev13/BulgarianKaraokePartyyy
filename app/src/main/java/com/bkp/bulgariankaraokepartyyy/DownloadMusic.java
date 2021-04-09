package com.bkp.bulgariankaraokepartyyy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownloadMusic {

    public static void download(Database db) {
        ArrayList<Song> cloudSongs = new ArrayList<>();
//        cloudSongs.add(new Song("Eminem - Not afraid", "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/Eminem%20-%20Not%20Afraid%20(Official%20Video).mp3?alt=media&token=c12d4c2b-b95e-41a6-a776-29f738739349"));
//        cloudSongs.add(new Song("Bad Meets Evil - Fast Lane ft. Eminem, Royce Da 5'9", "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/Bad%20Meets%20Evil%20-%20Fast%20Lane%20ft.%20Eminem%2C%20Royce%20Da%205'9.mp3?alt=media&token=3b0cafaa-ec19-44b7-98a7-5280c3abce91", "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/Bad%20Meets%20Evil%20-%20Fast%20Lane%20ft.%20Eminem%2C%20Royce%20Da%205'9%20(Instrumental).mp3?alt=media&token=635f7fc5-77a0-4ee7-a035-cf0d97f0c05a"));
//        cloudSongs.add(new Song("Eminem - Sing For The Moment", "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/Eminem%20-%20Sing%20For%20The%20Moment%20(Official%20Music%20Video).mp3?alt=media&token=b14ee08b-d87d-4edc-ba64-55e5a3321937"));
//        cloudSongs.add(new Song("MC Hammer - U Can't Touch This", "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/MC%20Hammer%20-%20U%20Can't%20Touch%20This%20(Official%20Music%20Video).mp3?alt=media&token=b8518a85-9730-4e11-bbb9-c53b3ccf32ae"));
//        cloudSongs.add(new Song("Pitbull - Hey Baby (Drop It To The Floor) ft. T-Pain", "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/Pitbull%20-%20Hey%20Baby%20(Drop%20It%20To%20The%20Floor)%20ft.%20T-Pain.mp3?alt=media&token=5335b768-47cc-4f3a-baf0-5136266f8d00"));
//        cloudSongs.add(new Song("Ricky Martin - Livin' La Vida Loca", "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/Ricky%20Martin%20-%20Livin'%20La%20Vida%20Loca.mp3?alt=media&token=1ccbfc3d-f462-4aba-bc30-168acad27e9d"));
//        cloudSongs.add(new Song("Saweetie - Best Friend (feat. Doja Cat)", "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/Saweetie%20-%20Best%20Friend%20(feat.%20Doja%20Cat)%20%5BOfficial%20Music%20Video%5D.mp3?alt=media&token=aa8e61cb-7320-4c7a-83e7-8f81468a6b27"));
//        cloudSongs.add(new Song("Timbaland - The Way I Are ft. Keri Hilson, D.O.E., Sebastian", "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/Timbaland%20-%20The%20Way%20I%20Are%20ft.%20Keri%20Hilson%2C%20D.O.E.%2C%20Sebastian%20(Official%20Music%20Video).mp3?alt=media&token=7ac824b7-5ef9-4a0a-8bb1-22ac474bf35b"));
//        cloudSongs.add(new Song("S.A.R.S. - Perspektiva ", "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/S.A.R.S.%20-%20Perspektiva%20(Official%20Video).mp3?alt=media&token=aaf7c0b2-b0b3-4b9a-88db-6a18fe55f547"));
//        cloudSongs.add(new Song("G-Eazy - I Mean It  ft. Remo", "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/G-Eazy%20-%20I%20Mean%20It%20(Official%20Music%20Video)%20ft.%20Remo.mp3?alt=media&token=bf81e315-1b0c-462a-811d-a7719f5d1800"));
//        cloudSongs.add(new Song("Future - Life Is Good  ft. Drake", "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/Future%20-%20Life%20Is%20Good%20(Official%20Music%20Video)%20ft.%20Drake.mp3?alt=media&token=9fbbf31f-4bfe-4dcd-a56d-00113ba4d9f1"));
//        cloudSongs.add(new Song("Jack Harlow - WHATS POPPIN feat. Dababy, Tory Lanez, & Lil Wayne", "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/Jack%20Harlow%20-%20WHATS%20POPPIN%20feat.%20Dababy%2C%20Tory%20Lanez%2C%20%26%20Lil%20Wayne%20%5BOfficial%20Video%5D.mp3?alt=media&token=9fbf6297-de63-4ed7-a704-7d89d25914fc"));
//        cloudSongs.add(new Song("Maroon 5 - Beautiful Mistakes ft. Megan Thee Stallion", "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/Maroon%205%20-%20Beautiful%20Mistakes%20ft.%20Megan%20Thee%20Stallion%20(Official%20Music%20Video).mp3?alt=media&token=f8017dce-55f3-436b-bbbe-4e66ffc143ec"));

        cloudSongs.add(new Song("Търновска Царица - Илка Александрова", "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/%D0%A2%D1%8A%D1%80%D0%BD%D0%BE%D0%B2%D1%81%D0%BA%D0%B0%20%D1%86%D0%B0%D1%80%D0%B8%D1%86%D0%B0.mp3?alt=media&token=c1d8a150-de36-4286-a696-ec72e6c92e57", "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/%D0%A2%D1%8A%D1%80%D0%BD%D0%BE%D0%B2%D1%81%D0%BA%D0%B0%20%D1%86%D0%B0%D1%80%D0%B8%D1%86%D0%B0%20(instrumental).mp3?alt=media&token=e48fa573-4967-4354-814c-af9de30ec98b"));
        Map<Integer, String> lyricsTurnovskaCarica = new HashMap<>();

        lyricsTurnovskaCarica.put(13000, "Сънувала сънувала\n търновска царица\n"
                + "Сънувала сънувала\n търновска царица\n"
                + "През среда се\n"
                + "През среда сe,\n"
                + "Небе препукало\n"
                + "Ситни звезди\n"
                + "Ситни звезди\n"
                + "на земя паднали.");

        lyricsTurnovskaCarica.put(55000, "Ясен месец\n"
                + "Ясен месец\n"
                + "Кървав ми е огрял\n"
                + "Ясен месец\n"
                + "Ясен месец\n"
                + "Кървав ми е огрял\n");

        lyricsTurnovskaCarica.put(69000, "А по него\n"
                + "А по него\n"
                + "звездица Деница\n"
                + "и тя ми е\n"
                + "и тя ми е\n"
                + "кърваво огряла\n");

        lyricsTurnovskaCarica.put(97000, "Ой Шишмане, цар Иване,\n"
                + "що съм сънувала?\n"
                + "Ой Шишмане, цар Иване,\n"
                + "що съм сънувала?\n"
                + "Отговаря\n"
                + "Отговаря\n"
                + "цар Иван Шишмана\n"
                + "Ой царице, хубавице,\n"
                + "съня ти е на зло");

        lyricsTurnovskaCarica.put(138000, "Дето се е\n"
                + "Дето се е\n"
                + "небе препукало\n"
                + "Ще да падне\n"
                + "Ще да падне\n"
                + "българското царство\n"
                + "Ситни звезди - ще загине\n"
                + "българската войска\n"
                + "Ясен месец - ще да падне\n"
                + "сам Иван Шишмана");

        lyricsTurnovskaCarica.put(180000, "Мина време, писмо дойде\n"
                + "от Костина града\n"
                + "Писмо пише и нарежда-\n"
                + "войска загинала\n"
                + "на Софийско равно поле\n"
                + "паднал и загинал\n"
                + "последния български цар-\n"
                + "Цар Иван Шишмана");


        List<Map<Integer, String>> lyrics = new ArrayList<>(4);
        lyrics.add(lyricsTurnovskaCarica);

        for (int i = 0; i < cloudSongs.size(); i++) {
            List<Song> songCheck = db.getSongsByName(cloudSongs.get(i).getName());

            if (songCheck.size() == 0) {
                long currSongId = db.addSong(cloudSongs.get(i));
                db.addLyrics(lyrics.get(i), currSongId);
            }
        }
    }
}
