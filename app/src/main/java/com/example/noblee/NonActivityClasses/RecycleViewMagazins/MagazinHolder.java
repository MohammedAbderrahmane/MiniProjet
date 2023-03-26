package com.example.noblee.NonActivityClasses.RecycleViewMagazins;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.R;

public class MagazinHolder extends RecyclerView.ViewHolder{

    TextView nom,nomVondeur,telephone;
    RecyclerView produitsRecycleView;

    public MagazinHolder(@NonNull View itemView) {
        super(itemView);

        nom = itemView.findViewById(R.id.magazin_nom);
        nomVondeur = itemView.findViewById(R.id.magazin_nom_vondeur);
        telephone = itemView.findViewById(R.id.magazin_telephone);
        produitsRecycleView = itemView.findViewById(R.id.magazin_produits_recycleview);
    }
}
