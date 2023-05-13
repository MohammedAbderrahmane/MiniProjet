package com.example.noblee.NonActivityClasses.RecycleViewPagnie;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.R;

public class PagnieHolder extends RecyclerView.ViewHolder {

    TextView nom,prix,quentite;
    ImageView image;
    ImageButton delete;

    public PagnieHolder(@NonNull View itemView) {
        super(itemView);

        nom = itemView.findViewById(R.id.pagnie_nom_produit);
        prix = itemView.findViewById(R.id.pagnie_prix_produit);
        quentite = itemView.findViewById(R.id.pagnie_quentite_produit);
        image = itemView.findViewById(R.id.pagnie_image);
        delete = itemView.findViewById(R.id.pagnie_delete);
    }
}
