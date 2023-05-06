package com.example.noblee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.NonActivityClasses.Commande;
import com.example.noblee.NonActivityClasses.RecycleViewPagnie.ItemPagnie;
import com.example.noblee.NonActivityClasses.RecycleViewPagnie.PagnieAdapter;
import com.example.noblee.NonActivityClasses.SavedPagniesDb;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class CommandeActivity extends AppCompatActivity {

    RecyclerView a;
    AppCompatButton ajouter,confirme,annuler;
    TextView decripption,prixTotal;
    PagnieAdapter pagnieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent goLogin = new Intent(this, LoginActivity.class);
            goLogin.putExtra("currentPage",LoginActivity.TO_COMMANDE);
            finish();
            startActivity(goLogin);
            return;
        }

        SavedPagniesDb.getInstance(getApplicationContext()).setUpListPagnie();

        a = findViewById(R.id.commande_pagnies);
        confirme = findViewById(R.id.commande_confirme);
        annuler = findViewById(R.id.commande_annuler);
        ajouter = findViewById(R.id.commande_ajouter);
        decripption = findViewById(R.id.commande_description);
        prixTotal = findViewById(R.id.commande_prix_total);

        pagnieAdapter = new PagnieAdapter(CommandeActivity.this);
        a.setLayoutManager(new LinearLayoutManager(this));
        a.setAdapter(pagnieAdapter);

        confirme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : confimation de commande
                ajouterCommande();
            }
        });

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : confimation de annuler
                SavedPagniesDb.getInstance(getApplicationContext()).viderPagnie();
                updatePrixTotal();
                Toast.makeText(CommandeActivity.this, "pagnie est videe", Toast.LENGTH_SHORT).show();
                pagnieAdapter.notifyDataSetChanged();
            }
        });

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), ConsulterProduitsActivity.class));

            }
        });

        updatePrixTotal();
    }

    private void ajouterCommande() {
        // TODO : fix it
        CollectionReference commandeRef = FirebaseFirestore.getInstance()
                .document(SavedPagniesDb.getInstance(CommandeActivity.this).getMagazinPath())
                .collection("Commandes");
        commandeRef
                .add(new Commande(
                        new Timestamp(new Date()),
                        FirebaseAuth.getInstance().getCurrentUser().getUid(),
                        Integer.toString(SavedPagniesDb.getInstance(CommandeActivity.this).calculerSommeCommande())
                        )
                )
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        for (int i=0;i<SavedPagniesDb.pagnies.size();i++){
                            documentReference.collection("Ligne_commande")
                                    .add(new ItemPagnie(
                                            SavedPagniesDb.pagnies.get(i).getNom(),
                                            SavedPagniesDb.pagnies.get(i).getPrix(),
                                            SavedPagniesDb.pagnies.get(i).getQuentite())
                                    )
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(CommandeActivity.this,e.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CommandeActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void updatePrixTotal(){
        prixTotal.setText(
                "Le prix total : " +
                String.valueOf(SavedPagniesDb.getInstance(CommandeActivity.this).calculerSommeCommande()));
    }
}