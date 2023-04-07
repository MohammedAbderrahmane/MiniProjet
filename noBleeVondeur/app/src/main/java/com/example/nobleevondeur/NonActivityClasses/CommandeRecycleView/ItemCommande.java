package com.example.nobleevondeur.NonActivityClasses.CommandeRecycleView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class ItemCommande {

    Timestamp date;
    String maladeRef,prixTotal;
    DocumentReference commandeRef;

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

    public DocumentReference getCommandeRef() {
        return commandeRef;
    }

    public void setCommandeRef(DocumentReference commandeRef) {
        this.commandeRef = commandeRef;
    }

    public ItemCommande(Timestamp date, String maladeRef, String prixTotal, DocumentReference reference) {
        this.date = date;
        this.maladeRef = maladeRef;
        this.prixTotal = prixTotal;
        this.commandeRef = reference;
    }
}
