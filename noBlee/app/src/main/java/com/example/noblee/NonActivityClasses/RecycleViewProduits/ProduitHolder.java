package com.example.noblee.NonActivityClasses.RecycleViewProduits;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.R;

public class ProduitHolder extends RecyclerView.ViewHolder {

    TextView nomProduit,prixProduit;
    ImageButton ajouterAuPagnie;
    ImageView imageProduit;

    public ProduitHolder(@NonNull View itemView) {
        super(itemView);
        nomProduit = itemView.findViewById(R.id.produit_nom_text);
        prixProduit = itemView.findViewById(R.id.produit_prix_text);
        ajouterAuPagnie = itemView.findViewById(R.id.produit_buy_btn);
        imageProduit = itemView.findViewById(R.id.produit_image);
    }
}
