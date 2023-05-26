package com.example.noblee.NonActivityClasses.EtatDialog.LigneCommandeRecycleView;

public class ItemLigneCommmande {

    String nom,quentite,prix,imageUrl;

    public ItemLigneCommmande(String nom, String prix, String quentite,String imageUrl) {
        this.nom = nom;
        this.prix = prix;
        this.quentite = quentite;
        this.imageUrl = imageUrl;
    }

    public String getNom() {
        return nom;
    }

    public String getQuentite() {
        return quentite;
    }

    public String getPrix() {
        return prix;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
