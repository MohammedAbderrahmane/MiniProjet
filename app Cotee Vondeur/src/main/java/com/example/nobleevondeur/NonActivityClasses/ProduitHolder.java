package com.example.nobleevondeur.NonActivityClasses;

import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nobleevondeur.R;

public class ProduitHolder extends RecyclerView.ViewHolder{

    Button modifier,supprimer;
    TextView prix,nom;
    ImageView image;
    // ConstraintLayout
    ConstraintLayout modificationLayout;
    ImageButton modifierPhoto;
    EditText modifierPrix,modifierNom;
    Button modifierBtn;
    TextView modifierStatueImage;

    // ---

    public ProduitHolder(@NonNull View itemView) {
        super(itemView);

        modifier = itemView.findViewById(R.id.produit_modifier_btn);
        supprimer = itemView.findViewById(R.id.produit_supprime_btn);
        prix = itemView.findViewById(R.id.produit_prix);
        nom = itemView.findViewById(R.id.produit_nom);
        image = itemView.findViewById(R.id.produit_image);

        // the GONE constraintLayout

        modificationLayout = itemView.findViewById(R.id.modifier_layout);
        modifierPrix = itemView.findViewById(R.id.modifier_prix);
        modifierNom = itemView.findViewById(R.id.modifier_nom);
        modifierPhoto = itemView.findViewById(R.id.modifier_photo);
        modifierBtn = itemView.findViewById(R.id.modifier_modifier_btn);
        modifierStatueImage = itemView.findViewById(R.id.modifier_statue_image);
    }
}
