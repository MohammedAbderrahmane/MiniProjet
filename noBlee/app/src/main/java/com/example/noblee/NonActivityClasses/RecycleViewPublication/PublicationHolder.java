package com.example.noblee.NonActivityClasses.RecycleViewPublication;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.R;

public class PublicationHolder extends RecyclerView.ViewHolder {

    TextView auteur,date,contenu;
    AppCompatButton like,dislike,commanter;
    ImageButton ajouterNewCommantaire,modirateurSupprimer;
    ImageView isDoctorImage;
    EditText newCommantaire;
    RecyclerView commantaireRecycleView;
    View commantaireLayout;

    public PublicationHolder(@NonNull View itemView) {
        super(itemView);

        auteur = itemView.findViewById(R.id.pub_auteur);
        date = itemView.findViewById(R.id.pub_date);
        isDoctorImage = itemView.findViewById(R.id.pub_is_doctor);

        modirateurSupprimer = itemView.findViewById(R.id.pub_modirateur);
        contenu = itemView.findViewById(R.id.pub_contenu);
        like = itemView.findViewById(R.id.pub_like);
        dislike = itemView.findViewById(R.id.pub_dislike);

        newCommantaire = itemView.findViewById(R.id.pub_new_commantaire);
        ajouterNewCommantaire = itemView.findViewById(R.id.pub_ajouter_commantaire);

        commanter = itemView.findViewById(R.id.pub_commanter);
        commantaireLayout =itemView.findViewById(R.id.commantaire_constraint_layout);
        commantaireRecycleView = itemView.findViewById(R.id.pub_commantaires);

    }
}
