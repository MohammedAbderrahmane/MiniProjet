package com.example.noblee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.NonActivityClasses.Commande;
import com.example.noblee.NonActivityClasses.EtatDialog.EtatCommandeDialog;
import com.example.noblee.NonActivityClasses.GestionDeMagazin;
import com.example.noblee.NonActivityClasses.RecycleViewPagnie.ItemPagnie;
import com.example.noblee.NonActivityClasses.RecycleViewPagnie.PagnieAdapter;
import com.example.noblee.NonActivityClasses.GestionDePagnies;
import com.example.noblee.NonActivityClasses.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class CommandeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AppCompatButton ajouter,confirme,annuler,consulterEtatCommandes;
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



        recyclerView = findViewById(R.id.commande_pagnies);
        confirme = findViewById(R.id.commande_confirme);
        annuler = findViewById(R.id.commande_annuler);
        ajouter = findViewById(R.id.commande_ajouter);
        decripption = findViewById(R.id.commande_description);
        prixTotal = findViewById(R.id.item_commande_prix_total);
        consulterEtatCommandes = findViewById(R.id.commande_consulter_etat);

        setUpRecycleView();
        updatePrixTotal();


        confirme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : confimation de commande
                if (GestionDePagnies.pagnies.isEmpty()) {
                    Toast.makeText(CommandeActivity.this, "Ajoutez au pagnie d'abord", Toast.LENGTH_SHORT).show();
                    return;
                }
                ajouterCommande();
            }
        });

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : confimation de annuler
                GestionDePagnies.viderPagnie();
                updatePrixTotal();
                Toast.makeText(CommandeActivity.this, "pagnie a été vidée", Toast.LENGTH_SHORT).show();
                pagnieAdapter.notifyDataSetChanged();
            }
        });

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), ConsulterActivity.class));

            }
        });

        consulterEtatCommandes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EtatCommandeDialog etatCommandeDialog = new EtatCommandeDialog();
                etatCommandeDialog.show(((AppCompatActivity) CommandeActivity.this).getSupportFragmentManager(), "etatCommandes");
            }
        });

    }
    private void setUpRecycleView(){
        pagnieAdapter = new PagnieAdapter(CommandeActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(pagnieAdapter);
    }
    private void ajouterCommande() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        CollectionReference commandeRef = FirebaseFirestore.getInstance()
                .document(GestionDeMagazin.getMagazinPath())
                .collection("Commandes");
        commandeRef
                .add(new Commande(
                        new Timestamp(new Date()),
                        User.getInstance().getReference().getPath(),
                        Integer.toString(GestionDePagnies.calculerSommeCommande())
                        )
                )
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        for (int i = 0; i< GestionDePagnies.pagnies.size(); i++){
                            documentReference.collection("LigneCommande")
                                    .add(new ItemPagnie(
                                            GestionDePagnies.pagnies.get(i).getNom(),
                                            GestionDePagnies.pagnies.get(i).getPrix(),
                                            GestionDePagnies.pagnies.get(i).getQuentite(),
                                            GestionDePagnies.pagnies.get(i).getImageUrl())
                                    );
                        }
                        progressDialog.dismiss();
                        Toast.makeText(CommandeActivity.this, "Votre commande a ete engistré", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void updatePrixTotal(){
        prixTotal.setText(
                "Le prix total : " +
                String.valueOf(GestionDePagnies.calculerSommeCommande()) +
                " DA");
    }
}