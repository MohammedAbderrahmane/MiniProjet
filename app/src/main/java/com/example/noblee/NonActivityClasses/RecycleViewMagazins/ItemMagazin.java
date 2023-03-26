package com.example.noblee.NonActivityClasses.RecycleViewMagazins;

import com.google.firebase.firestore.DocumentReference;

public class ItemMagazin {
    String nom, nomVondeur,telephone;
    DocumentReference magazinReference;

    public ItemMagazin(String nom, String nom_vondeur, String telephone,DocumentReference documentReference) {
        this.nom = nom;
        this.nomVondeur = nom_vondeur;
        this.telephone = telephone;
        this.magazinReference = documentReference;
    }

    public DocumentReference getMagazinReference() {
        return magazinReference;
    }

    public void setMagazinReference(DocumentReference magazinReference) {
        this.magazinReference = magazinReference;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomVondeur() {
        return nomVondeur;
    }

    public void setNomVondeur(String nomVondeur) {
        this.nomVondeur = nomVondeur;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
