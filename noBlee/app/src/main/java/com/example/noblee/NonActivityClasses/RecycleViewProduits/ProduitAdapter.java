package com.example.noblee.NonActivityClasses.RecycleViewProduits;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.noblee.LoginActivity;
import com.example.noblee.NonActivityClasses.GestionDeMagazin;
import com.example.noblee.NonActivityClasses.GestionDePagnies;
import com.example.noblee.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

import java.util.List;

public class ProduitAdapter extends RecyclerView.Adapter<ProduitHolder> {

    private Context context;
    private List<ItemProduit> produits;
    private DocumentReference magazinRef;


    public ProduitAdapter(Context context, List<ItemProduit> produits) {
        this.context = context;
        this.produits = produits;
    }

    @NonNull
    @Override
    public ProduitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProduitHolder(LayoutInflater.from(context).inflate(R.layout.layout_produit,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProduitHolder holder, int position) {

        ItemProduit produit = produits.get(position);

        holder.nom.setText(         "Produit : " + produit.getNom());
        holder.categorie.setText(   "Categorie : " + produit.getCategorie());
        holder.prix.setText(        "Prix : " + produit.getPrix());

        setUpImage(produit.getImageUrl(),holder);
        // dialog
        setUpAjouterAuPagnie(holder,produit);
    }

    private void setUpImage(String imageUrl, ProduitHolder holder) {
        Glide.with(context)
                .load(imageUrl)
                .into(holder.image);
    }

    private void setUpAjouterAuPagnie(ProduitHolder holder, ItemProduit produit){
        Bundle argsOfDialog = new Bundle();
        QuentiteDialoge quentiteDialoge = new QuentiteDialoge();

        holder.ajouterAuPagnie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    Intent goLogin = new Intent(( (Activity)context), LoginActivity.class);
                    goLogin.putExtra("currentPage",LoginActivity.TO_CONSULTER);
                    ( (Activity)context).finish();
                    ( (Activity)context).startActivity(goLogin);
                    return;
                }
                if (GestionDePagnies.poduitDejaDemmande(produit.getNom())){
                    Toast.makeText(context, "Produit existe déja dans pagnie", Toast.LENGTH_SHORT).show();
                }
                if (GestionDeMagazin.checkMemeMagazin(magazinRef)) {
                    argsOfDialog.putString("nom", produit.getNom());
                    argsOfDialog.putString("prix", produit.getPrix());
                    argsOfDialog.putString("image", produit.getImageUrl());
                    quentiteDialoge.setArguments(argsOfDialog);
                    quentiteDialoge.show(((AppCompatActivity) context).getSupportFragmentManager(), "quentite");
                }else{
                    Toast.makeText(context, "Order Annulée : You must select products from one Shop", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return produits.size();
    }

    public void setMagazinRef(DocumentReference magazinRef) {
        this.magazinRef = magazinRef;
    }
}
