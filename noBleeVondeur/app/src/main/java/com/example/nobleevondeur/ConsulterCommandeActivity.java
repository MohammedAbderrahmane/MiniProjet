package com.example.nobleevondeur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.nobleevondeur.NonActivityClasses.CommandeRecycleView.CommandeAdapter;
import com.example.nobleevondeur.NonActivityClasses.CommandeRecycleView.ItemCommande;
import com.example.nobleevondeur.NonActivityClasses.DataBase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ConsulterCommandeActivity extends AppCompatActivity {

    RecyclerView commandeRecycleView;
    View noCommande;
    List<ItemCommande> commandes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);

        noCommande = findViewById(R.id.consulter_commandes_no_commande);
        commandeRecycleView = findViewById(R.id.consulter_commandes_recycle_view);

        setUpCommandesRecycleView();




    }

    void setUpCommandesRecycleView (){
        CommandeAdapter commandeAdapter = new CommandeAdapter(ConsulterCommandeActivity.this,commandes);
        commandeRecycleView.setLayoutManager(new LinearLayoutManager(ConsulterCommandeActivity.this));
        commandeRecycleView.setAdapter(commandeAdapter);
        DataBase.getInstance(getApplicationContext()).getMagazinRef()
                .collection("Commandes")
                //.whereNotEqualTo("deleted",false)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            commandes.add(
                                    new ItemCommande(
                                            document.getTimestamp("date"),
                                            document.getString("malade"),
                                            document.getString("prixTotal"),
                                            document.getReference()
                                    )
                            );
                        }
                        if (commandes.isEmpty()){
                            noCommande.setVisibility(View.VISIBLE);
                            commandeRecycleView.setVisibility(View.GONE);
                        }else {
                            noCommande.setVisibility(View.GONE);
                            commandeRecycleView.setVisibility(View.VISIBLE);
                            commandeAdapter.notifyDataSetChanged();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}