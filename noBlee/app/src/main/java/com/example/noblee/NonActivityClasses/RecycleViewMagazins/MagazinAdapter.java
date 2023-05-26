package com.example.noblee.NonActivityClasses.RecycleViewMagazins;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.noblee.NonActivityClasses.RecycleViewProduits.ProduitAdapter;
import com.example.noblee.R;

import java.util.List;

public class MagazinAdapter extends RecyclerView.Adapter<MagazinHolder>{

    Context context;
    List<ItemMagazin> magazins;

    public MagazinAdapter(Context context, List<ItemMagazin> magazins) {
        this.context = context;
        this.magazins = magazins;
    }

    @NonNull
    @Override
    public MagazinHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MagazinHolder(LayoutInflater.from(context).inflate(R.layout.layout_magazin,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MagazinHolder holder, int position) {
        ItemMagazin magazin = magazins.get(position);

        setUpImage(holder,magazin.getImageUrl());
        holder.nom.setText(         "Magazin : " + magazin.getNom());
        holder.location.setText(    "Location : " + magazin.getLocation());
        holder.nomVendeur.setText(  "nom de vendeur : " + magazin.getNomVondeur());
        holder.telephone.setText(   "Numero telephone : " + magazin.getTelephone());

        holder.consulter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manageRecycleView(holder.produitsRecycleView.getVisibility(),holder,magazin);
            }
        });
    }

    private void manageRecycleView(int visibility, MagazinHolder holder, ItemMagazin magazin) {
        if (visibility == View.VISIBLE){
            holder.produitsRecycleView.setVisibility(View.GONE);
            holder.consulter.setText("Consulter les produits");
            return;
        }
        holder.produitsRecycleView.setVisibility(View.VISIBLE);
        holder.consulter.setText("Casher les produits");
        setUpRecycleView(holder,magazin);
    }

    public void setUpRecycleView(MagazinHolder holder, ItemMagazin magazin) {
        ProduitAdapter produitAdapter = new ProduitAdapter(context,magazin.getProduitList());
        produitAdapter.setMagazinRef(magazin.getReference());
        holder.produitsRecycleView.setLayoutManager(new LinearLayoutManager(context));
        holder.produitsRecycleView.setAdapter(produitAdapter);

    }

    private void setUpImage(MagazinHolder holder,String imageUrl) {
        Glide.with(context)
                .load(imageUrl)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return magazins.size();
    }

    public List<ItemMagazin> getMagazins() {
        return magazins;
    }



}
