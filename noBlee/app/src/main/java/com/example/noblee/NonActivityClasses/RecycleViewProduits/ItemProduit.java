package com.example.noblee.NonActivityClasses.RecycleViewProduits;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import androidx.recyclerview.widget.LinearLayoutManager;

public class ItemProduit {
    String nom,image, prix;

    public ItemProduit() {
    }

    public ItemProduit(String nom, String prix, String image) {
        this.nom = nom;
        this.prix = prix;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
