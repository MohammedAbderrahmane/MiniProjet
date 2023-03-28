package com.example.noblee.NonActivityClasses;

import com.google.firebase.Timestamp;

public class Commande {
    Timestamp date;
    String malade,prixTotal;

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getMalade() {
        return malade;
    }

    public void setMalade(String malade) {
        this.malade = malade;
    }

    public String getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(String prixTotal) {
        this.prixTotal = prixTotal;
    }

    public Commande(Timestamp now, String userId, String prixTotal) {
        this.date = now;
        this.malade = userId;
        this.prixTotal = prixTotal;
    }
}
