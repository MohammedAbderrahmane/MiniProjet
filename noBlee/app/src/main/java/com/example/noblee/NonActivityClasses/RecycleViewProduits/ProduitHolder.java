package com.example.noblee.NonActivityClasses.RecycleViewProduits;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.R;

public class ProduitHolder extends RecyclerView.ViewHolder {

    public TextView nom;
    public TextView prix;
    public TextView categorie;
    ImageButton ajouterAuPagnie;
    public ImageView image;

    public ProduitHolder(@NonNull View itemView) {
        super(itemView);
        nom = itemView.findViewById(R.id.produit_nom);
        prix = itemView.findViewById(R.id.produit_prix);
        categorie = itemView.findViewById(R.id.produit_categorie);
        ajouterAuPagnie = itemView.findViewById(R.id.produit_buy);
        image = itemView.findViewById(R.id.produit_image);
    }
}
