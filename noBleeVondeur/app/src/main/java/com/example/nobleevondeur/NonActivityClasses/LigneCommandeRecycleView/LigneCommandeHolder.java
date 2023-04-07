package com.example.nobleevondeur.NonActivityClasses.LigneCommandeRecycleView;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nobleevondeur.R;

public class LigneCommandeHolder extends RecyclerView.ViewHolder {

    TextView nom,prix,quentite;

    public LigneCommandeHolder(@NonNull View itemView) {
        super(itemView);

        nom = itemView.findViewById(R.id.ligne_commande_nom);
        prix = itemView.findViewById(R.id.ligne_commande_prix);
        quentite = itemView.findViewById(R.id.ligne_commande_quentite);
    }
}
