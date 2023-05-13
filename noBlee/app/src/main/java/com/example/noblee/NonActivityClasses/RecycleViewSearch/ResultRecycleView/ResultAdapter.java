package com.example.noblee.NonActivityClasses.RecycleViewSearch.ResultRecycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.noblee.NonActivityClasses.RecycleViewProduits.ItemProduit;
import com.example.noblee.NonActivityClasses.RecycleViewProduits.ProduitHolder;
import com.example.noblee.R;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ProduitHolder> {

    Context context;
    List<ItemProduit> produits;

    public ResultAdapter(Context context, List<ItemProduit> produits) {
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
        holder.categorie.setText(   " ");
        holder.prix.setText(        "Prix : " + produit.getPrix());

        setUpImage(produit.getImageUrl(),holder);

    }

    private void setUpImage(String imageUrl, ProduitHolder holder) {
        Glide.with(context)
                .load(imageUrl)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return produits.size();
    }
}
