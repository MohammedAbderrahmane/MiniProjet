package com.example.nobleemedecin.NonActivityClasses.RecycleViewwCommantaire;

import com.google.firebase.firestore.DocumentReference;

public class ItemCommantaire {
    boolean creeParMedecin;
    String user,contenu;
    DocumentReference reference;

    public ItemCommantaire(String user, String contenu) {
        this.user = user;
        this.contenu = contenu;
    }

    public ItemCommantaire(String user, String contenu, DocumentReference reference, boolean creeParMedecin) {
        this.user = user;
        this.contenu = contenu;
        this.reference = reference;
        this.creeParMedecin = creeParMedecin;
    }

    public ItemCommantaire(String user, String contenu, DocumentReference reference) {
        this.user = user;
        this.contenu = contenu;
        this.reference = reference;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
}
