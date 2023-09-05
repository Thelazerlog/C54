package com.eric.labonte.appsaveinstancestate;

import java.io.Serializable;

public class Utilisateur implements Serializable {
    String nom;
    String prenom;

    public Utilisateur(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }
}
