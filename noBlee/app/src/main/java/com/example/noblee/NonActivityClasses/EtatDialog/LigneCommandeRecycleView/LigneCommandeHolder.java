package com.example.noblee.NonActivityClasses.EtatDialog.LigneCommandeRecycleView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.R;

public class LigneCommandeHolder extends RecyclerView.ViewHolder {

    TextView nom,prix,quentite;
    ImageView image;

    public LigneCommandeHolder(@NonNull View itemView) {
        super(itemView);

        nom = itemView.findViewById(R.id.ligne_commande_nom);
        prix = itemView.findViewById(R.id.ligne_commande_prix);
        quentite = itemView.findViewById(R.id.ligne_commande_quentite);
        image = itemView.findViewById(R.id.ligne_commande_image);
    }
}
