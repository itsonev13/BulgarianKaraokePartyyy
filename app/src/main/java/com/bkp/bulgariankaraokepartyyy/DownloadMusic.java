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
        cloudSongs.add(new Song("Бяла Роза - Славка Калчева", "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/%D0%91%D1%8F%D0%BB%D0%B0%20%D1%80%D0%BE%D0%B7%D0%B0%20.mp3?alt=media&token=3b7d9870-e604-4670-a417-110dd6423d40", "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/%D0%91%D1%8F%D0%BB%D0%B0%20%D1%80%D0%BE%D0%B7%D0%B0%20%D0%98%D0%BD%D1%81%D1%82%D1%80%D1%83%D0%BC%D0%B5%D0%BD%D1%82%D0%B0%D0%BB.mp3?alt=media&token=85c75aa3-8523-4f5a-a5b7-9deefde0d482"));
        cloudSongs.add(new Song("Мома Яница - Яница", "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/%D0%9C%D0%BE%D0%BC%D0%B0%20%D0%AF%D0%BD%D0%B8%D1%86%D0%B0-%D0%AF%D0%BD%D0%B8%D1%86%D0%B0.mp3?alt=media&token=57ff58b0-9ea3-43d0-8a52-f60c8ef345fa", "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/%D0%9C%D0%BE%D0%BC%D0%B0%20%D0%AF%D0%BD%D0%B8%D1%86%D0%B0-%D0%AF%D0%BD%D0%B8%D1%86%D0%B0%20%D0%98%D0%BD%D1%81%D1%82%D1%80%D1%83%D0%BC%D0%B5%D0%BD%D1%82%D0%B0%D0%BB.mp3?alt=media&token=feb66db4-1f56-4f4a-ac87-a72572375b1f"));


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

        Map<Integer, String> lyricsBqlaRoza = new HashMap<>();
        lyricsBqlaRoza.put(40000,"Бяла роза с росата ще закича в косата, да посрещна аз зората.\n");
        lyricsBqlaRoza.put(48000, "Бързо слънце да изгрее,\n да изгрее да залезе,\n че да ида на чешмата.\n");

        lyricsBqlaRoza.put(55000,"Бяла роза с росата ще закича в косата, да посрещна аз зората.\n");
        lyricsBqlaRoza.put(62000, "Бързо слънце да изгрее,\n да изгрее да залезе,\n че да ида на чешмата.\n");

        lyricsBqlaRoza.put(73000, "Бяла роза ще закича,\n бяла роза с бодлите,\n но пази се ти пази се,\n");
        lyricsBqlaRoza.put(81000, "Бяла роза ще закича,\n бяла роза с бодлите,\n но пази се ти пази се,\n");
        lyricsBqlaRoza.put(89000, "");


        lyricsBqlaRoza.put(121000, "Снощи вечер на чешмата срещнах момък в позлата,\n с черни очи кат маслини\n");
        lyricsBqlaRoza.put(128000, "Яхна черно врано конче,\n чудни думи той ми рече,\n в любов ми се обрече.\n");

        lyricsBqlaRoza.put(135000, "Снощи вечер на чешмата срещнах момък в позлата,\n с черни очи кат маслини\n");
        lyricsBqlaRoza.put(143000, "Яхна черно врано конче,\n чудни думи той ми рече,\n в любов ми се обрече.\n");

        lyricsBqlaRoza.put(154000, "Бяла роза ще закича,\n бяла роза с бодлите,\n но пази се ти пази се,\n");
        lyricsBqlaRoza.put(161000, "Бяла роза ще закича,\n бяла роза с бодлите,\n но пази се ти пази се,\n");
        lyricsBqlaRoza.put(16800, "");

        lyricsBqlaRoza.put(201000, "Вече съм мома голяма,\n много лесно се недавам,\n дали туй не е измама.\n");
        lyricsBqlaRoza.put(208000, "Малко хитро ще опитам,\n този момък ще изпитам,\n с бяла роза в косата.\n");

        lyricsBqlaRoza.put(215000, "Вече съм мома голяма,\n много лесно се недавам,\n дали туй не е измама.\n");
        lyricsBqlaRoza.put(223000, "Малко хитро ще опитам,\n този момък ще изпитам,\n с бяла роза в косата.\n");

        lyricsBqlaRoza.put(234000, "Бяла роза ще закича,\n бяла роза с бодлите,\n но пази се ти пази се,\n");
        lyricsBqlaRoza.put(241000, "Бяла роза ще закича,\n бяла роза с бодлите,\n но пази се ти пази се,\n");

        lyricsBqlaRoza.put(249000, "Но пази се ти пази се,\n");

        Map<Integer, String> lyricsMomaQnica = new HashMap<>();

        lyricsMomaQnica.put(31000, "Раснала девойка, мома пораснала.\n" +
                "Със тънка снага, с китна премяна, румена, засмяна.\n");
        lyricsMomaQnica.put(45000,"Момче я хареса, хареса, бендиса,\n и се полуди и се зачуди как да я излъже.\n");

        lyricsMomaQnica.put(58000,"Хей, хей, хубавица, хей, бре гълъбица, мома Яница.\n" );
        lyricsMomaQnica.put(65000,"Кой ще се намери сърце да спечели на мома Яница?\n");

        lyricsMomaQnica.put(72000,"Хей, хей хубавица, хей, бре, гълъбица, мома Яница.\n" );
        lyricsMomaQnica.put(79000,"Кой ще се намери хубост да премери, мома да спечели?\n");
        lyricsMomaQnica.put(87000,"");

        lyricsMomaQnica.put(117000,"Давам ти коланче,\n алтан герданче.\n");
        lyricsMomaQnica.put(124000,"Мома ги пази,\n все да чапрази, дойде ми у нази.\n");
        lyricsMomaQnica.put(132000,"Ще те позлатя, джанъм, посребря - с двори широки, много имоти, чардарци високи.\n");

        lyricsMomaQnica.put(145000,"Хей, хей, хубавица, хей, бре гълъбица, мома Яница.\n" );
        lyricsMomaQnica.put(152000,"Кой ще се намери сърце да спечели на мома Яница?\n");

        lyricsMomaQnica.put(159000,"Хей, хей хубавица, хей, бре, гълъбица, мома Яница.\n" );
        lyricsMomaQnica.put(166000,"Кой ще се намери хубост да премери, мома да спечели?\n");
        lyricsMomaQnica.put(173000,"");

        lyricsMomaQnica.put(204000,"Нe ти ща дворите, дворите - къщите.\n Клетва съм дала и обещала с любов да пристана.");
        lyricsMomaQnica.put(217000,"С китка ме закичи, с хубост ме заплени.\n С любов голяма, любов за двама, двама завинаги.");

        lyricsMomaQnica.put(232000,"Хей, хей, хубавица, хей, бре гълъбица, мома Яница.\n" );
        lyricsMomaQnica.put(239000,"Кой ще се намери сърце да спечели на мома Яница?\n");

        lyricsMomaQnica.put(245000,"Хей, хей хубавица, хей, бре, гълъбица, мома Яница.\n" );
        lyricsMomaQnica.put(252000,"Кой ще се намери хубост да премери, мома да спечели?\n");
        lyricsMomaQnica.put(259000,"");

        List<Map<Integer, String>> lyrics = new ArrayList<>(4);
        lyrics.add(lyricsTurnovskaCarica);
        lyrics.add(lyricsBqlaRoza);
        lyrics.add(lyricsMomaQnica);
        for (int i = 0; i < cloudSongs.size(); i++) {
            List<Song> songCheck = db.getSongsByName(cloudSongs.get(i).getName());

            if (songCheck.size() == 0) {
                long currSongId = db.addSong(cloudSongs.get(i));
                db.addLyrics(lyrics.get(i), currSongId);
            }
        }
    }
}
