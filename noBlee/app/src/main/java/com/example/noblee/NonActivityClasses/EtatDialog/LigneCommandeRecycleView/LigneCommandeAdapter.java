package com.example.noblee.NonActivityClasses.EtatDialog.LigneCommandeRecycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.noblee.R;

import java.util.List;

public class LigneCommandeAdapter extends RecyclerView.Adapter<LigneCommandeHolder> {

    Context context;
    List<ItemLigneCommmande> ligneCommmandeList;

    public LigneCommandeAdapter(Context context, List<ItemLigneCommmande> itemLigneCommmandes) {
        this.context = context;
        ligneCommmandeList = itemLigneCommmandes;
    }

    @NonNull
    @Override
    public LigneCommandeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LigneCommandeHolder(LayoutInflater.from(context).inflate(R.layout.layout_ligne_commande,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull LigneCommandeHolder holder, int position) {

        ItemLigneCommmande ligneCommmande = ligneCommmandeList.get(position);

        holder.nom.setText(ligneCommmande.getNom());
        holder.prix.setText(ligneCommmande.getPrix());
        holder.quentite.setText(ligneCommmande.getQuentite());
        setUpImage(ligneCommmande.getImageUrl(),holder);
    }

    private void setUpImage(String imageUrl, LigneCommandeHolder holder) {
        Glide.with(context)
                .load(imageUrl)
                .into(holder.image);
    }
    @Override
    public int getItemCount() {
        return ligneCommmandeList.size();
    }
}
