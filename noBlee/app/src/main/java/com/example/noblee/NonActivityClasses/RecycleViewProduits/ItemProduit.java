package com.example.noblee.NonActivityClasses.RecycleViewProduits;

public class ItemProduit {
    String nom, imageUrl, prix,categorie;

     public ItemProduit(String nom, String prix, String image,String categorie) {
        this.nom = nom;
        this.prix = prix;
        this.imageUrl = image;
        this.categorie = categorie;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
