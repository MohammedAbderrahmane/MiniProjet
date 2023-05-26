package com.example.noblee.NonActivityClasses;

import com.google.firebase.Timestamp;

public class Commande {
    Timestamp date;
    String maladePath,prixTotal,statue;
    public Commande(Timestamp now, String userPath, String prixTotal) {
        this.date = now;
        this.maladePath = userPath;
        this.prixTotal = prixTotal;
        statue = "envoye";
    }

    public Timestamp getDate() {
        return date;
    }

    public String getMaladePath() {
        return maladePath;
    }

    public String getPrixTotal() {
        return prixTotal;
    }

    public String getStatue() {
        return statue;
    }
}
