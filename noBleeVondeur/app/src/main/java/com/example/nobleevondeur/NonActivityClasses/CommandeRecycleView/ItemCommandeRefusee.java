package com.example.nobleevondeur.NonActivityClasses.CommandeRecycleView;

import com.google.firebase.Timestamp;

public class ItemCommandeRefusee {
    Timestamp date;
    String maladeRef;
    String prixTotal;
    String commandeRefuseePath;

    public String getCommandeRefuseePath() {
        return commandeRefuseePath;
    }

    public void setCommandeRefuseePath(String commandeRefuseePath) {
        this.commandeRefuseePath = commandeRefuseePath;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getMaladeRef() {
        return maladeRef;
    }

    public void setMaladeRef(String maladeRef) {
        this.maladeRef = maladeRef;
    }

    public String getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(String prixTotal) {
        this.prixTotal = prixTotal;
    }



    public ItemCommandeRefusee(Timestamp date, String maladeRef, String prixTotal, String commandeRefuseePath) {
        this.date = date;
        this.maladeRef = maladeRef;
        this.prixTotal = prixTotal;
        this.commandeRefuseePath = commandeRefuseePath;
    }
}
