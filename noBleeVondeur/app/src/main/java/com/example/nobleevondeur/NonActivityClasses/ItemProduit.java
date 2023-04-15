package com.example.nobleevondeur.NonActivityClasses;

public class ItemProduit {

    String nom;
    String prix;
    String categorie;
    String imageUrl;

    public ItemProduit(String nom, String prix, String categorie,String imageUrl) {
        this.nom = nom;
        this.prix = prix;
        this.imageUrl = imageUrl;
        this.categorie = categorie;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }
}
