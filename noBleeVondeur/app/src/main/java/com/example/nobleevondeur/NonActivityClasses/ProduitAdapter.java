package com.example.nobleevondeur.NonActivityClasses;

import android.content.Context;
import android.net.Uri;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ProduitAdapter extends RecyclerView.Adapter<ProduitHolder>{

    Context context;
    List<ItemProduit> produits;
    boolean imageSelected = false,nomChanged=false,prixChanged=false,photoChanged=false;
    Uri tmpImage;
    String imageUrl;

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
        ItemProduit produit = produits.get(position);
        holder.nom.setText(produit.getNom());
        holder.prix.setText(produit.getPrix());
        // TODO image
        holder.supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                supprimerProduit(holder,position);
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
                            holder.nom.getText().toString(),
                            holder.prix.getText().toString());
                }else
                    holder.modificationLayout.setVisibility(View.GONE);
            }
        });
    }
    void setUpModificationLayout(ProduitHolder holder,int position, String pNom, String pPrix){

        holder.modifierNom.setText(pNom);
        holder.modifierPrix.setText(pPrix);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                nomChanged = !holder.modifierNom.getText().toString().equals(pNom);
                prixChanged = !holder.modifierPrix.getText().toString().equals(pPrix);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        };
        holder.modifierNom.addTextChangedListener(textWatcher);
        holder.modifierPrix.addTextChangedListener(textWatcher);

        holder.modifierBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Image update
                modifierProduit(
                        pNom,
                        holder.modifierNom.getText().toString(),
                        holder.modifierPrix.getText().toString());

            }
        });

        holder.supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : confirmation dialog
                supprimerProduit(holder,position);
            }
        });

    }

    private void modifierProduit(String oldNom,String newNom,String newPrix) {
        if (nomChanged){
            Magazin.getInstance().getRef().collection("Produits")
                    .whereEqualTo("nom",oldNom)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments())
                                queryDocumentSnapshots.getDocuments().get(0).getReference().update("nom",newNom)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(context, "nom -> "+newNom, Toast.LENGTH_SHORT).show();
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
        if (prixChanged){
            Magazin.getInstance().getRef().collection("Produits")
                    .whereEqualTo("nom",oldNom)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments())
                                queryDocumentSnapshots.getDocuments().get(0).getReference().update("prix",newPrix)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(context, "prix -> "+newPrix, Toast.LENGTH_SHORT).show();
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
        // TODO : handle image changed
    }

    void supprimerProduit(ProduitHolder holder, int position){
        Magazin.getInstance().getRef().collection("Produits")
                .whereEqualTo("nom",produits.get(position).getNom())
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
