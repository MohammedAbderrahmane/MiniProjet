package com.example.noblee.NonActivityClasses.RecycleViewPagnie;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.R;

public class PagnieHolder extends RecyclerView.ViewHolder {

    TextView nomPagnie,prixPagnie,quentitePagnie;
    ImageView imagePagnie;
    ImageButton deletePagnie;

    public PagnieHolder(@NonNull View itemView) {
        super(itemView);
        nomPagnie = itemView.findViewById(R.id.pagnie_nom_produit);
        prixPagnie = itemView.findViewById(R.id.pagnie_prix_produit);
        quentitePagnie = itemView.findViewById(R.id.pagnie_quentite_produit);
        imagePagnie = itemView.findViewById(R.id.pagnie_image);
        deletePagnie = itemView.findViewById(R.id.pagnie_delete);
    }
}
