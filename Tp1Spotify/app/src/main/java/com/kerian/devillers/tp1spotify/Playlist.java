package com.kerian.devillers.tp1spotify;

import android.graphics.Bitmap;

public class Playlist {
    String nom;
    Integer duree;
    Integer nbChansons;
    Integer image;
    String lien;

    public Playlist(String nom, Integer duree, Integer nbChansons, Integer image, String lien) {
        this.nom = nom;
        this.duree = duree;
        this.nbChansons = nbChansons;
        this.image = image;
        this.lien = lien;
    }
}
