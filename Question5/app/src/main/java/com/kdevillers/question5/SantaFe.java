package com.kdevillers.question5;

public class SantaFe extends VehiculeHyundai{

    public SantaFe ( String nom, String alimentation, int qte)
    {
        super ( nom, alimentation, qte);
        if ( alimentation.equals("essence"))
            setPrix(39904);
        else if ( alimentation.equals("hybride"))
            setPrix(47404);
        else
            setPrix(52404);  // hybride rechargeable
    }
}
