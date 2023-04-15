package com.example.nobleevondeur.NonActivityClasses;

import android.app.ProgressDialog;
import android.content.Context;
import com.bumptech.glide.Glide;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nobleevondeur.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProduitAdapter extends RecyclerView.Adapter<ProduitHolder>{

    Context context;
    List<ItemProduit> produits;
    boolean imageSelected = false,nomChanged=false,prixChanged=false,photoChanged=false,categorieChanged=false;
    ProgressDialog progressDialog;

    public ProduitAdapter(Context context, List<ItemProduit> produits) {
        this.context = context;
        this.produits = produits;
    }

    @NonNull
    @Override
    public ProduitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProduitHolder(LayoutInflater.from(context).inflate(R.layout.produit_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProduitHolder holder,int position) {

        try{
            ItemProduit produit = produits.get(position);

            holder.nom.setText(produit.getNom());
            holder.categorie.setText(produit.getCategorie());
            holder.prix.setText(produit.getPrix());
            Glide.with(context)
                .load(produit.getImageUrl())
                .into(holder.image);

            holder.supprimer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    supprimerProduit(produit.getNom(),position);
                }
            });
            // Modifaction partie
            holder.modifier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ( holder.modificationLayout.getVisibility() == View.GONE){
                        holder.modificationLayout.setVisibility(View.VISIBLE);
                        setUpModificationLayout(
                                holder,
                                position,
                                produit.getNom(),
                                produit.getCategorie(),
                                produit.getPrix());
                    }else{
                        holder.modificationLayout.setVisibility(View.GONE);
                    }
                }
            });
        }catch (Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }


    }

    void setUpModificationLayout(ProduitHolder holder,int position, String pNom,String pCategorie, String pPrix){
        holder.modifierNom.setText(pNom);
        holder.modifierCategorie.setText(pCategorie);
        holder.modifierPrix.setText(pPrix);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                nomChanged = !holder.modifierNom.getText().toString().equals(pNom);
                categorieChanged = !holder.modifierCategorie.getText().toString().equals(pCategorie);
                prixChanged = !holder.modifierPrix.getText().toString().equals(pPrix);

                holder.modifier.setEnabled(nomChanged || categorieChanged || prixChanged);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        };

        holder.modifierNom.addTextChangedListener(textWatcher);
        holder.modifierCategorie.addTextChangedListener(textWatcher);
        holder.modifierPrix.addTextChangedListener(textWatcher);

        // TODO : image changed

        holder.modifierBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Image update
                modifierProduit(
                        pNom,
                        holder.modifierNom.getText().toString(),
                        holder.modifierCategorie.getText().toString(),
                        holder.modifierPrix.getText().toString());

            }
        });

    }

    private void modifierProduit(String oldNom,String newNom,String newCategorie,String newPrix) {
        // TODO : update image too and delete old image
        progressDialog = ProgressDialog.show(context,"Changment en progress","attend la fin de modification");

        Map<String, Object> updates = new HashMap<>();
        updates.put("nom", newNom);
        updates.put("categorie", newCategorie);
        updates.put("prix", newPrix);

        DataBase.getInstance(context).getMagazinRef()
                .collection("Produits")
                .whereEqualTo("nom",oldNom)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        queryDocumentSnapshots.getDocuments().get(0).getReference()
                                .update(updates)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        notifyDataSetChanged();
                                        progressDialog.dismiss();
                                        Toast.makeText(context, "Le produit a ete modifié", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(context,e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void supprimerProduit(String nom,int position){
        progressDialog = ProgressDialog.show(context,"Supprimation en progress","attend la fin de supprimation");
        DataBase.getInstance(context).getMagazinRef()
                .collection("Produits")
                .whereEqualTo("nom",nom)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        queryDocumentSnapshots.getDocuments().get(0).getReference().delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        produits.remove(position);
                                        notifyDataSetChanged();
                                        // TODO : delete immage too
                                        progressDialog.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context,e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return produits.size();
    }
}
