package com.example.noblee;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.noblee.NonActivityClasses.RecycleViewProduits.QuentiteDialoge;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    ImageButton prfl;
    Button consulterProduits,pageCommande,donnerArgent;
    EditText recherche_ed;
    private Button test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        consulterProduits = findViewById(R.id.main_consulter_produits);
        pageCommande = findViewById(R.id.main_page_commande);
        donnerArgent = findViewById(R.id.main_donner_argent);

        prfl = findViewById(R.id.main_to_profile);
        recherche_ed = findViewById(R.id.main_recherche);


        prfl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FirebaseAuth.getInstance().getCurrentUser() == null)
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                else
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
    }



}