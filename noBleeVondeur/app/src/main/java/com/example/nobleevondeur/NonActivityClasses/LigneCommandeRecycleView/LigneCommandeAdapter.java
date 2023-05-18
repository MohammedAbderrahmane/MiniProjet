package com.example.nobleevondeur.NonActivityClasses.LigneCommandeRecycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nobleevondeur.R;

import java.util.List;

public class LigneCommandeAdapter extends RecyclerView.Adapter<LigneCommandeHolder> {

    Context context;
    public List<ItemLigneCommmande> ligneCommmandeList;

    public LigneCommandeAdapter(Context context, List<ItemLigneCommmande> itemLigneCommmandes) {
        this.context = context;
        ligneCommmandeList = itemLigneCommmandes;
    }

    @NonNull
    @Override
    public LigneCommandeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LigneCommandeHolder(LayoutInflater.from(context).inflate(R.layout.ligne_commande_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull LigneCommandeHolder holder, int position) {

        ItemLigneCommmande ligneCommmande = ligneCommmandeList.get(position);

        holder.nom.setText(ligneCommmande.getNom());
        holder.quentite.setText(ligneCommmande.getQuentite());
    }

    @Override
    public int getItemCount() {
        return ligneCommmandeList.size();
    }
}
