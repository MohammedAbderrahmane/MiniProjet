package com.example.noblee.NonActivityClasses.RecycleViewPagnie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.noblee.CommandeActivity;
import com.example.noblee.NonActivityClasses.GestionDePagnies;
import com.example.noblee.R;

public class PagnieAdapter extends RecyclerView.Adapter<PagnieHolder> {

    Context context;


    public PagnieAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PagnieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PagnieHolder(LayoutInflater.from(context).inflate(R.layout.layout_pagnie,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PagnieHolder holder, int position) {
        ItemPagnie pagnie = GestionDePagnies.pagnies.get(position);

        holder.nom.setText("Nom de produit : " + pagnie.getNom());
        holder.prix.setText("Prix de produit : " + pagnie.getPrix());
        holder.quentite.setText("Quentité : " + pagnie.getQuentite());
        setUpImage(holder,pagnie.getImageUrl());
        


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                GestionDePagnies.supprimerPagnie(pagnie.getNom());
                ((CommandeActivity) context).updatePrixTotal();
                notifyDataSetChanged();
            }catch (Exception e){
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }
            }
        });
    }

    private void setUpImage(PagnieHolder holder, String imageUrl) {
        Glide.with(context)
                .load(imageUrl)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return GestionDePagnies.pagnies.size();
    }
}
