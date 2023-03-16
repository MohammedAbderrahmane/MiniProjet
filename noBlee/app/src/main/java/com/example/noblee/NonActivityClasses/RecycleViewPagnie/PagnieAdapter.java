package com.example.noblee.NonActivityClasses.RecycleViewPagnie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.NonActivityClasses.SavedPagniesDb;
import com.example.noblee.R;

public class PagnieAdapter extends RecyclerView.Adapter<PagnieHolder> {

    Context context;


    public PagnieAdapter() {
    }

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
        ItemPagnie pagnie = SavedPagniesDb.pagnies.get(position);

        holder.nomPagnie.setText(pagnie.getNom());
        holder.prixPagnie.setText(pagnie.getPrix());
        holder.quentitePagnie.setText(pagnie.getQuentite());

        holder.deletePagnie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SavedPagniesDb.getInstance(context.getApplicationContext()).supprimer(pagnie.getNom());
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return SavedPagniesDb.pagnies.size();
    }
}
