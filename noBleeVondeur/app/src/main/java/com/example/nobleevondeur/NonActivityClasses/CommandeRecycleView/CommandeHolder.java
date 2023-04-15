package com.example.nobleevondeur.NonActivityClasses.CommandeRecycleView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nobleevondeur.R;

public class CommandeHolder extends RecyclerView.ViewHolder {

    TextView nubmber,prixTotal;
    RecyclerView lignies_commandes;
    ImageButton accepter,refuser;

    public CommandeHolder(@NonNull View itemView) {
        super(itemView);

        nubmber = itemView.findViewById(R.id.commande_number);
        accepter = itemView.findViewById(R.id.commande_accept);
        refuser = itemView.findViewById(R.id.commande_refuser);
        lignies_commandes = itemView.findViewById(R.id.commande_ligne_commande);
        prixTotal = itemView.findViewById(R.id.commande_prix_total);
    }
}
