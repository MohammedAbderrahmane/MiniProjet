package com.example.noblee.NonActivityClasses.EtatDialog.CommandeRecycleView;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.R;

public class CommandeHolder extends RecyclerView.ViewHolder {

    TextView magazin,time,prixTotal,statue;
    RecyclerView ligneRecycleView;

    public CommandeHolder(@NonNull View itemView) {
        super(itemView);

        magazin = itemView.findViewById(R.id.item_commande_magazin);
        time = itemView.findViewById(R.id.item_commande_time);
        ligneRecycleView = itemView.findViewById(R.id.item_commande_lignes);
        prixTotal = itemView.findViewById(R.id.item_commande_prix_total);
        statue = itemView.findViewById(R.id.item_commande_statue);
    }
}
