package com.bkp.bulgariankaraokepartyyy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownloadMusic {

    public static void download(Database db) {
        ArrayList<Song> cloudSongs = new ArrayList<>();

        cloudSongs.add(new Song("Търновска Царица - Илка Александрова",
                "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/%D0%A2%D1%8A%D1%80%D0%BD%D0%BE%D0%B2%D1%81%D0%BA%D0%B0%20%D1%86%D0%B0%D1%80%D0%B8%D1%86%D0%B0.mp3?alt=media&token=c1d8a150-de36-4286-a696-ec72e6c92e57",
                "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/%D0%A2%D1%8A%D1%80%D0%BD%D0%BE%D0%B2%D1%81%D0%BA%D0%B0%20%D1%86%D0%B0%D1%80%D0%B8%D1%86%D0%B0%20(instrumental).mp3?alt=media&token=e48fa573-4967-4354-814c-af9de30ec98b"));
        cloudSongs.add(new Song("Бяла Роза - Славка Калчева",
                "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/%D0%91%D1%8F%D0%BB%D0%B0%20%D1%80%D0%BE%D0%B7%D0%B0%20.mp3?alt=media&token=3b7d9870-e604-4670-a417-110dd6423d40",
                "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/%D0%91%D1%8F%D0%BB%D0%B0%20%D1%80%D0%BE%D0%B7%D0%B0%20%D0%98%D0%BD%D1%81%D1%82%D1%80%D1%83%D0%BC%D0%B5%D0%BD%D1%82%D0%B0%D0%BB.mp3?alt=media&token=85c75aa3-8523-4f5a-a5b7-9deefde0d482"));
        cloudSongs.add(new Song("Мома Яница - Яница",
                "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/%D0%9C%D0%BE%D0%BC%D0%B0%20%D0%AF%D0%BD%D0%B8%D1%86%D0%B0-%D0%AF%D0%BD%D0%B8%D1%86%D0%B0.mp3?alt=media&token=57ff58b0-9ea3-43d0-8a52-f60c8ef345fa",
                "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/%D0%9C%D0%BE%D0%BC%D0%B0%20%D0%AF%D0%BD%D0%B8%D1%86%D0%B0-%D0%AF%D0%BD%D0%B8%D1%86%D0%B0%20%D0%98%D0%BD%D1%81%D1%82%D1%80%D1%83%D0%BC%D0%B5%D0%BD%D1%82%D0%B0%D0%BB.mp3?alt=media&token=feb66db4-1f56-4f4a-ac87-a72572375b1f"));
        cloudSongs.add(new Song("Слави и Ку-Ку Бенд - Кой уши байрака",
                "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/%D0%A1%D0%BB%D0%B0%D0%B2%D0%B8%20%D0%B8%20%D0%9A%D1%83-%D0%9A%D1%83%20%D0%91%D0%B5%D0%BD%D0%B4%20-%20%D0%9A%D0%BE%D0%B9%20%D1%83%D1%88%D0%B8%20%D0%B1%D0%B0%D0%B9%D1%80%D0%B0%D0%BA%D0%B0.mp3?alt=media&token=0c3dc182-3407-4a91-bae8-c10d17145209",
                "https://firebasestorage.googleapis.com/v0/b/bulgarianparty-5acf8.appspot.com/o/%D0%A1%D0%BB%D0%B0%D0%B2%D0%B8%20%D0%B8%20%D0%9A%D1%83-%D0%9A%D1%83%20%D0%91%D0%B5%D0%BD%D0%B4%20-%20%D0%9A%D0%BE%D0%B9%20%D1%83%D1%88%D0%B8%20%D0%B1%D0%B0%D0%B9%D1%80%D0%B0%D0%BA%D0%B0%20(instrumental).mp3?alt=media&token=58178ae2-ea26-4a2b-890a-443b659024b6"));

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


        Map<Integer, String> lyricsKoiUshiBairqka = new HashMap<>();

        lyricsKoiUshiBairqka.put(30000, "Айде провикна се турския паша\nот Панагюрище,\n");
        lyricsKoiUshiBairqka.put(44000, "Айде, вий идете и я уловете\nРайнa Попгеоргиева!\n");
        lyricsKoiUshiBairqka.put(59000, "Айде, вий идете и я уловете\nРайнa Попгеоргиева!\n");
        lyricsKoiUshiBairqka.put(72000, "");

        lyricsKoiUshiBairqka.put(101000, "Нито я колете, нито я бесете,\nнай при мене доведете я!\n");
        lyricsKoiUshiBairqka.put(115000, "Ази да я питам, питам и разпитам\nкой уши байрака,");
        lyricsKoiUshiBairqka.put(129000, "кой уши байрака,\nкой му тури знака\n\"Смърт или свобода\"");
        lyricsKoiUshiBairqka.put(143000, "");

        lyricsKoiUshiBairqka.put(172000, "Айде провикна се\nРайна Попгеоргиева\nот Панагюрище\n");
        lyricsKoiUshiBairqka.put(186000, "Щете ме колете, щете ме бесете,\nаз съм Райна Попгеоргиева!\n");
        lyricsKoiUshiBairqka.put(201000, "Аз уших байрака,\nаз му турих знака,\n\"Смърт или свобода\"!\n");
        lyricsKoiUshiBairqka.put(214000, "\"Смърт или свобода\"!\n");
        lyricsKoiUshiBairqka.put(221000, "");


        List<Map<Integer, String>> lyrics = new ArrayList<>(4);
        lyrics.add(lyricsTurnovskaCarica);
        lyrics.add(lyricsBqlaRoza);
        lyrics.add(lyricsMomaQnica);
        lyrics.add(lyricsKoiUshiBairqka);

        for (int i = 0; i < cloudSongs.size(); i++) {
            List<Song> songCheck = db.getSongsByName(cloudSongs.get(i).getName());

            if (songCheck.size() == 0) {
                long currSongId = db.addSong(cloudSongs.get(i));
                db.addLyrics(lyrics.get(i), currSongId);
            }
        }
    }
}
