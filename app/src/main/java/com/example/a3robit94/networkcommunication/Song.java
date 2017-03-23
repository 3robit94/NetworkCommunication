package com.example.a3robit94.networkcommunication;

/**
 * Created by 3robit94 on 23/03/2017.
 */
public class Song {

    private String title;
    private String artist;
    private int year;

    public Song(String title, String artist, int year){
        this.title = title;
        this.artist = artist;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist(){
        return artist;
    }

    public int getYear(){
        return year;
    }
}
