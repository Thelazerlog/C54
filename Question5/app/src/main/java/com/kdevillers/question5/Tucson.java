package com.kdevillers.question5;

public class Tucson extends VehiculeHyundai {

    public Tucson ( String nom, String alimentation, int qte)
    {
        super ( nom, alimentation, qte);
        if ( alimentation.equals("essence"))
            setPrix(30954);
        else if ( alimentation.equals("hybride"))
            setPrix(42454);
        else
            setPrix(47204);  // hybride rechargeable
    }
}
