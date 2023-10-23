package com.kdevillers.question5;

import java.io.Serializable;
import java.util.Vector;

public class Transaction implements Serializable {
    Vector<VehiculeHyundai> achats;

    public Transaction() {
        achats = new Vector<>();
    }

    public boolean ajouterVoiture(VehiculeHyundai voiture){
        if (achats.size() < 2 && voiture.getQte() > 0){
            achats.add(voiture);
            return true;
        }
        return false;
    }

    public int calculerTotal(){
        int total = 0;

        for (VehiculeHyundai voiture: achats) {
            total += voiture.getPrix();
        }
        return total;
    }

    public Vector<VehiculeHyundai> getAchats() {
        return achats;
    }
}
