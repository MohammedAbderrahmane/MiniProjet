package com.example.noblee.NonActivityClasses;

import com.google.firebase.Timestamp;

public class Commande {
    Timestamp date;
    String adress,prixTotal;

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getMalade() {
        return adress;
    }

    public void setMalade(String malade) {
        this.adress = malade;
    }

    public String getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(String prixTotal) {
        this.prixTotal = prixTotal;
    }

    public Commande(Timestamp now, String userId, String prixTotal) {
        this.date = now;
        this.adress = userId;
        this.prixTotal = prixTotal;
    }
}
