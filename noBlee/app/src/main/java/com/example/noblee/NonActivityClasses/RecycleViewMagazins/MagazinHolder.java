package com.example.noblee.NonActivityClasses.RecycleViewMagazins;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.R;

public class MagazinHolder extends RecyclerView.ViewHolder{

    TextView nom,location,nomVendeur,telephone;
    AppCompatButton consulter;
    ImageView image;
    RecyclerView produitsRecycleView;

    public MagazinHolder(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.magazin_image);
        nom = itemView.findViewById(R.id.magazin_nom);
        location = itemView.findViewById(R.id.magazin_location);
        nomVendeur = itemView.findViewById(R.id.magazin_vendeur);
        telephone = itemView.findViewById(R.id.magazin_telephone);
        produitsRecycleView = itemView.findViewById(R.id.magazin_recycle_view);
        consulter = itemView.findViewById(R.id.magazin_consulter);
    }
}
