package com.example.noblee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.NonActivityClasses.Commande;
import com.example.noblee.NonActivityClasses.RecycleViewPagnie.ItemPagnie;
import com.example.noblee.NonActivityClasses.RecycleViewPagnie.PagnieAdapter;
import com.example.noblee.NonActivityClasses.SavedPagniesDb;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CommandeActivity extends AppCompatActivity {

    RecyclerView a;
    Button confirme,annuler;
    ImageButton ajouter;
    TextView decripption;
    PagnieAdapter pagnieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);

        SavedPagniesDb.getInstance(getApplicationContext()).setUpListPagnie();

        a = findViewById(R.id.commande_pagnies);
        confirme = findViewById(R.id.commande_confirme);
        annuler = findViewById(R.id.commande_annuler);
        ajouter = findViewById(R.id.commande_ajouter);
        decripption = findViewById(R.id.commande_description);


        pagnieAdapter = new PagnieAdapter(getApplicationContext());
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
                Toast.makeText(CommandeActivity.this, "pagnie est videe", Toast.LENGTH_SHORT).show();
                pagnieAdapter.notifyDataSetChanged();
            }
        });

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(),ConsulterProduitsActivity.class));

            }
        });


    }

    private void ajouterCommande() {
        // TODO : fix it
        CollectionReference commandeRef = FirebaseFirestore.getInstance()
                .collection("Magazins")
                .document()
                .collection("Commandes");
        commandeRef
                .add(new Commande(
                        // TODO : date ,prixTotal,client
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
}