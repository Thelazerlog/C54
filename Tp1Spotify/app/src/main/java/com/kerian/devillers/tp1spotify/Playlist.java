package com.kerian.devillers.tp1spotify;

import android.graphics.Bitmap;

public class Playlist {
    String nom;
    String genre;
    String nbChansons;
    Bitmap image;

    public Playlist(String nom, String genre, String nbChansons, Bitmap image) {
        this.nom = nom;
        this.genre = genre;
        this.nbChansons = nbChansons;
        this.image = image;
    }
}
