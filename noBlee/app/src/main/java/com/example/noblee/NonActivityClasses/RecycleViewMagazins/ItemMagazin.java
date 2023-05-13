package com.example.noblee.NonActivityClasses.RecycleViewMagazins;

import com.google.firebase.firestore.DocumentReference;

public class ItemMagazin {
    String nom,location,nomVondeur,telephone,imageUrl;

    DocumentReference reference;
    public ItemMagazin(String nom, String location, String nom_vondeur, String telephone, String imageUrl, DocumentReference reference) {
        this.nom = nom;
        this.location = location;;
        this.nomVondeur = nom_vondeur;
        this.telephone = telephone;
        this.imageUrl = imageUrl;
        this.reference = reference;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public DocumentReference getReference() {
        return reference;
    }

    public void setReference(DocumentReference reference) {
        this.reference = reference;
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
