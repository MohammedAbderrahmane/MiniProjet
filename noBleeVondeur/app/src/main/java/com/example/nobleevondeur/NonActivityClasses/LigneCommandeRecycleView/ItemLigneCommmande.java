package com.example.nobleevondeur.NonActivityClasses.LigneCommandeRecycleView;

public class ItemLigneCommmande {

    String nom,quentite,prix;

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public ItemLigneCommmande(String nom, String prix, String quentite) {
        this.nom = nom;
        this.prix = prix;
        this.quentite = quentite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getQuentite() {
        return quentite;
    }

    public void setQuentite(String quentite) {
        this.quentite = quentite;
    }
}
