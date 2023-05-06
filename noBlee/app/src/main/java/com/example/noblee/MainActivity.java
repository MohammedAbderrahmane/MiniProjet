package com.example.noblee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.noblee.NonActivityClasses.User;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ImageButton prfl;
    AppCompatButton consulterProduits,pageCommande,donnerArgent,pagePublication,pageMessagrie,seRensigner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            User.createInstance();
        }

        consulterProduits = findViewById(R.id.main_consulter_produits);
        pageCommande = findViewById(R.id.main_page_commande);
        donnerArgent = findViewById(R.id.main_donner_argent);
        pagePublication = findViewById(R.id.main_publication);
        seRensigner = findViewById(R.id.main_se_rensigner);
        pageMessagrie = findViewById(R.id.main_poser_question);
        prfl = findViewById(R.id.main_to_profile);

        prfl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
            }
        });

        consulterProduits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ConsulterProduitsActivity.class));
            }
        });

        pageCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CommandeActivity.class));
            }
        });

        donnerArgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CharityActivity.class));
            }
        });

        pagePublication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PublicationActivity.class));
            }
        });
    }



}