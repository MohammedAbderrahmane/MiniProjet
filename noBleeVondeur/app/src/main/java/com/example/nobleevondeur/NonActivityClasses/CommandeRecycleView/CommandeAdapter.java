package com.example.nobleevondeur.NonActivityClasses.CommandeRecycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nobleevondeur.NonActivityClasses.DataBase;
import com.example.nobleevondeur.NonActivityClasses.LigneCommandeRecycleView.ItemLigneCommmande;
import com.example.nobleevondeur.NonActivityClasses.LigneCommandeRecycleView.LigneCommandeAdapter;
import com.example.nobleevondeur.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CommandeAdapter extends RecyclerView.Adapter<CommandeHolder>{

    Context context;
    List<ItemCommande> commandes;
    List<ItemLigneCommmande> ligneCommmandes = new ArrayList<>();

    public CommandeAdapter(Context context, List<ItemCommande> commandes) {
        this.context = context;
        this.commandes = commandes;
    }

    @NonNull
    @Override
    public CommandeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommandeHolder(LayoutInflater.from(context).inflate(R.layout.commande_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommandeHolder holder, int position) {
        ItemCommande commande = commandes.get(position);

        holder.nubmber.setText("N#" + (position+1));
        holder.prixTotal.setText("Le prix total :" + commande.getPrixTotal());
        holder.accepter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.refuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                supprimeCommande(commande);
            }
        });

        setUpLigneCommandesRecucleView(holder,position);

    }

    private void supprimeCommande(ItemCommande commande) {
        DataBase.getInstance(context)
                .getMagazinRef()
                .collection("Commandes_refusee")
                .add(
                        new ItemCommandeRefusee(
                                commande.getDate(),
                                commande.getMaladeRef(),
                                commande.getPrixTotal(),
                                commande.getCommandeRef().getPath()
                        )
                )
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference reference) {
                        commande.getCommandeRef()
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        List<DocumentSnapshot> documents = commande.getCommandeRef()
                                                .collection("Ligne_commande")
                                                .get().getResult().getDocuments();
                                        for (DocumentSnapshot document : documents) {
                                            document.getReference().delete();
                                        }
                                        Toast.makeText(context, "Commande a ete refusee", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return commandes.size();
    }

    void setUpLigneCommandesRecucleView(CommandeHolder holder, int position){
        LigneCommandeAdapter ligneCommandeAdapter = new LigneCommandeAdapter(context,ligneCommmandes);
        holder.lignies_commandes.setLayoutManager(new LinearLayoutManager(context));
        holder.lignies_commandes.setAdapter(ligneCommandeAdapter);
        commandes.get(position).commandeRef
                .collection("Ligne_commande")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            ligneCommmandes.add(
                                    new ItemLigneCommmande(
                                            document.getString("nom"),
                                            document.getString("prix"),
                                            document.getString("quentite")
                                    )
                            );
                        }
                        ligneCommandeAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });



    }
}
