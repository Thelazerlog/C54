package com.kdevillers.question5;

public class Ioniq5 extends VehiculeHyundai{
    public Ioniq5 ( String nom, int qte)
    {
        super ( nom, "electrique", qte);

        setPrix(51650);  // electrique
    }

}
