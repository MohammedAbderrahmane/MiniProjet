package com.example.nobleevondeur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.nobleevondeur.NonActivityClasses.CommandeRecycleView.CommandeAdapter;
import com.example.nobleevondeur.NonActivityClasses.CommandeRecycleView.ItemCommande;
import com.example.nobleevondeur.NonActivityClasses.Magazin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ConsulterCommandeActivity extends AppCompatActivity {

    RecyclerView commandeRecycleView;
    List<ItemCommande> commandes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);

        setUpCommandesRecycleView();

    }

    void setUpCommandesRecycleView (){
        commandeRecycleView = findViewById(R.id.consulter_commandes_recycle_view);
        CommandeAdapter commandeAdapter = new CommandeAdapter(ConsulterCommandeActivity.this,commandes);
        commandeRecycleView.setLayoutManager(new LinearLayoutManager(ConsulterCommandeActivity.this));
        commandeRecycleView.setAdapter(commandeAdapter);
        Magazin.getInstance().getRef()
                .collection("Commandes")
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
                        commandeAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}