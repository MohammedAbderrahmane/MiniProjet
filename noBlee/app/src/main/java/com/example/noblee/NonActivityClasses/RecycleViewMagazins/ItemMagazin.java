package com.example.noblee.NonActivityClasses.RecycleViewMagazins;

import com.example.noblee.NonActivityClasses.RecycleViewProduits.ItemProduit;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ItemMagazin {
    String nom,location,nomVondeur,telephone,imageUrl;
    List<ItemProduit> produitList;

    DocumentReference reference;
    public ItemMagazin(String nom, String location, String nom_vondeur, String telephone, String imageUrl, DocumentReference reference) {
        this.nom = nom;
        this.location = location;;
        this.nomVondeur = nom_vondeur;
        this.telephone = telephone;
        this.imageUrl = imageUrl;
        this.reference = reference;
        setUpProduits();
    }

    private void setUpProduits() {
        produitList = new ArrayList<>();
        reference.collection("Produits")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            produitList.add(
                                    new ItemProduit(
                                            documentSnapshot.getString("nom"),
                                            documentSnapshot.getString("prix"),
                                            documentSnapshot.getString("imageUrl"),
                                            documentSnapshot.getString("categorie")
                                    )
                            );
                        }
                    }
                });
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

    public List<ItemProduit> getProduitList() {
        return produitList;
    }
}
